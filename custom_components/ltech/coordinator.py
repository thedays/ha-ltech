import json
import logging
from datetime import timedelta

from homeassistant.core import HomeAssistant
from homeassistant.helpers.update_coordinator import DataUpdateCoordinator, UpdateFailed

from .api import LtechApiClient, LtechApiError, LtechAuthError
from .const import DOMAIN
from .mesh_manager import LtechMeshManager
from .mqtt_client import LtechMqttClient

_LOGGER = logging.getLogger(__name__)

SCAN_INTERVAL = timedelta(seconds=30)


class LtechDataUpdateCoordinator(DataUpdateCoordinator):
    def __init__(self, hass: HomeAssistant, api: LtechApiClient):
        super().__init__(
            hass,
            _LOGGER,
            name=DOMAIN,
            update_interval=SCAN_INTERVAL,
        )
        self.api = api
        self.devices = {}
        self.device_states = {}
        self.places = []
        self._reauth_attempted = False
        self.mqtt_client = None
        self.mesh_manager = None
        self.mesh_enabled = False
        self._mqtt_last_disconnect_time = 0

    async def _async_update_data(self):
        try:
            if not self.places:
                try:
                    self.places = await self.hass.async_add_executor_job(self.api.get_place_list)
                except LtechApiError as e:
                    _LOGGER.error(f"[UPDATE_DATA] Failed to get place list: {e}")
                    if self.devices:
                        _LOGGER.info(f"[UPDATE_DATA] Keeping {len(self.devices)} existing devices")
                        return self.devices
                    raise
            
            places_list = []
            if isinstance(self.places, dict):
                places_data = self.places.get("data", self.places)
                if "rows" in places_data:
                    places_list = places_data["rows"]
                elif isinstance(places_data, list):
                    places_list = places_data
            elif isinstance(self.places, list):
                places_list = self.places
            
            if places_list:
                first_place = places_list[0]
                place_id = first_place.get("placeId") or first_place.get("placeid")
                self.api.select_place(place_id)
                
                try:
                    device_list = await self.hass.async_add_executor_job(
                        self.api.get_device_list, place_id
                    )
                except LtechApiError as e:
                    _LOGGER.error(f"[UPDATE_DATA] Failed to get device list: {e}")
                    if self.devices:
                        _LOGGER.info(f"[UPDATE_DATA] Keeping {len(self.devices)} existing devices")
                        return self.devices
                    raise
                
                try:
                    sync_result = await self.hass.async_add_executor_job(
                        self.api.sync_device_status, place_id
                    )
                    self._update_device_states_from_sync(sync_result)
                except LtechApiError as e:
                    _LOGGER.error(f"[UPDATE_DATA] Failed to sync device status: {e}")
                
                device_data = device_list.get("data", device_list) if isinstance(device_list, dict) else {}
                if isinstance(device_data, dict) and "rows" in device_data:
                    self.devices = {}
                    device_name_counts = {}
                    for device in device_data["rows"]:
                        device_id = device.get("deviceId") or device.get("deviceid")
                        device_id = str(device_id) if device_id else None
                        product_id = device.get("productId") or device.get("productid", "")
                        device_name = device.get("deviceName") or device.get("devicename", "")
                        
                        if device_id:
                            if device_id in self.devices:
                                _LOGGER.warning(f"[DUPLICATE] Device with id={device_id} already exists, skipping")
                            else:
                                device_state = device.get("deviceState", "NOT_FOUND")
                                _LOGGER.debug(f"[DEVICE_STATE] device_id={device_id}, device_name={device_name}, deviceState={device_state[:100] if isinstance(device_state, str) else type(device_state)}")
                                self.devices[device_id] = device
                                self._parse_sync_device_data(device)
                                if device_name in device_name_counts:
                                    device_name_counts[device_name] += 1
                                else:
                                    device_name_counts[device_name] = 1
                    
                    for name, count in device_name_counts.items():
                        if count > 1:
                            _LOGGER.info(f"[DUPLICATE_NAME] Device name '{name}' appears {count} times")
                    
                    _LOGGER.info(f"Total devices loaded: {len(self.devices)}")
                    return self.devices
            
            return self.devices
        
        except LtechAuthError:
            if self._reauth_attempted:
                raise UpdateFailed("Session expired and re-authentication failed")
            
            _LOGGER.warning("Session expired, re-authenticating...")
            self._reauth_attempted = True
            try:
                await self.hass.async_add_executor_job(self.api.login)
                return await self._async_update_data()
            except LtechApiError as e:
                raise UpdateFailed(f"Failed to re-authenticate: {e}") from e
            finally:
                self._reauth_attempted = False
        except LtechApiError as e:
            _LOGGER.error(f"[UPDATE_DATA] Error updating data: {e}")
            if self.devices:
                _LOGGER.info(f"[UPDATE_DATA] Keeping existing {len(self.devices)} devices")
                return self.devices
            raise UpdateFailed(f"Error updating data: {e}") from e

    def get_device(self, device_id):
        return self.devices.get(device_id)

    def get_devices_by_type(self, product_types):
        devices = []
        for device in self.devices.values():
            device_id = device.get("deviceId") or device.get("deviceid")
            product_type = device.get("producttype")
            producttypename = device.get("producttypename", "")
            productname = device.get("productname", "")
            devicename = device.get("devicename", "")
            aipuducttype = device.get("aipuducttype", "")
            
            _LOGGER.debug(f"[CLASSIFY_DEBUG] Device '{devicename}' (id={device_id}): producttype={product_type}, producttypename={producttypename}, productname={productname}, aipuducttype={aipuducttype}")
            
            is_light = (producttypename == "智能照明" or 
                       "灯" in producttypename or
                       "灯" in devicename or
                       "灯" in productname or
                       product_type == "2")
            
            is_switch = (producttypename == "智能开关" or 
                        "开关" in producttypename or
                        product_type == "8")
            
            if is_light and any("LIGHT" in pt for pt in product_types):
                _LOGGER.info(f"[CLASSIFY] Device '{devicename}' (id={device_id}) classified as LIGHT")
                devices.append(device)
                continue
            
            if is_switch and any("SWITCH" in pt for pt in product_types):
                _LOGGER.info(f"[CLASSIFY] Device '{devicename}' (id={device_id}) classified as SWITCH")
                devices.append(device)
                continue
            
            if any("SENSOR" in pt for pt in product_types):
                if "sensor" in producttypename.lower() or "传感器" in producttypename:
                    _LOGGER.info(f"[CLASSIFY] Device '{devicename}' (id={device_id}) classified as SENSOR")
                    devices.append(device)
        
        _LOGGER.info(f"[CLASSIFY] Found {len(devices)} devices for types {product_types}")
        return devices

    def get_device_state(self, device_id):
        device_id_str = str(device_id)
        if device_id_str in self.device_states:
            return self.device_states[device_id_str]
        return None

    def _update_device_states_from_sync(self, sync_result):
        """Update device states from sync_device_status API response."""
        if not sync_result:
            return
        
        _LOGGER.info(f"[SYNC_UPDATE] Raw sync_result type={type(sync_result)}, keys={list(sync_result.keys()) if isinstance(sync_result, dict) else 'N/A'}")
        if isinstance(sync_result, dict):
            _LOGGER.info(f"[SYNC_UPDATE] sync_result data preview: {str(sync_result)[:500]}")
        
        try:
            if isinstance(sync_result, dict):
                data = sync_result.get("data", sync_result)
                rows = data.get("rows", [])
                if isinstance(rows, list):
                    for device_data in rows:
                        self._parse_sync_device_data(device_data)
                else:
                    self._parse_sync_device_data(sync_result)
            elif isinstance(sync_result, list):
                for device_data in sync_result:
                    if isinstance(device_data, dict):
                        self._parse_sync_device_data(device_data)
            
            _LOGGER.info(f"[SYNC_UPDATE] Updated {len(self.device_states)} device states from sync")
        except Exception as e:
            _LOGGER.error(f"[SYNC_UPDATE] Failed to update device states: {e}")
    
    def _parse_sync_device_data(self, device_data):
        """Parse a single device data from sync response."""
        device_id = device_data.get("deviceId") or device_data.get("deviceid")
        if not device_id:
            return
        
        device_id_str = str(device_id)
        device_state = {}
        
        device_state_str = device_data.get("deviceState")
        if device_state_str:
            if isinstance(device_state_str, str):
                try:
                    device_state = json.loads(device_state_str)
                except (json.JSONDecodeError, TypeError):
                    device_state = {}
            elif isinstance(device_state_str, dict):
                device_state = device_state_str
        
        if not device_state:
            reportinstruct = device_data.get("reportinstruct", "")
            if reportinstruct:
                try:
                    report_data = json.loads(reportinstruct)
                    if isinstance(report_data, dict):
                        for key, value in report_data.items():
                            if key.startswith("Char"):
                                device_state[key] = value
                except (json.JSONDecodeError, TypeError):
                    pass
            
            if not device_state and isinstance(reportinstruct, str) and len(reportinstruct) >= 14:
                ri_hex = reportinstruct.upper()
                try:
                    status_byte_str = ri_hex[12:14]
                    status_byte = int(status_byte_str, 16)
                    is_on = status_byte == 0x01
                    device_state["is_on"] = is_on
                    _LOGGER.debug(f"[REPORTINSTRUCT_PARSE] device_id={device_id_str}, reportinstruct={ri_hex}, status_byte=0x{status_byte_str}, is_on={is_on}")
                except ValueError:
                    pass
        
        if not device_state:
            maccode = device_data.get("maccode", "")
            if maccode:
                try:
                    maccode_data = json.loads(maccode)
                    if isinstance(maccode_data, dict):
                        for key, value in maccode_data.items():
                            if key.startswith("Char"):
                                device_state[key] = value
                except (json.JSONDecodeError, TypeError):
                    pass
        
        if device_state:
            if device_id_str in self.device_states:
                old_state = self.device_states[device_id_str]
                if old_state != device_state:
                    _LOGGER.info(f"[SYNC_UPDATE] device_id={device_id_str} state changed: {old_state} -> {device_state}")
            self.device_states[device_id_str] = device_state
            _LOGGER.debug(f"[SYNC_UPDATE] device_id={device_id_str}, state={device_state}")

    def _on_mqtt_disconnect(self):
        import time
        self._mqtt_last_disconnect_time = time.time()
        _LOGGER.info(f"[MQTT_DISCONNECT] Recorded disconnect time: {self._mqtt_last_disconnect_time}")
    
    def start_mqtt(self):
        if self.mqtt_client:
            self.mqtt_client.disconnect()

        try:
            _LOGGER.info("[MQTT_START] Calling bind_user to get MQTT credentials")
            bind_result = self.api.bind_user()
            if bind_result and self.api.product_key and self.api.device_name and self.api.device_secret:
                _LOGGER.info(f"[MQTT_START] MQTT credentials obtained: product_key={self.api.product_key}, device_name={self.api.device_name}")
            else:
                _LOGGER.error("[MQTT_START] Failed to get MQTT credentials!")
                return False
        except Exception as e:
            _LOGGER.error(f"[MQTT_START] Failed to call bind_user: {e}")
            return False

        self.mqtt_client = LtechMqttClient(self.api, self._on_mqtt_message, self.hass, self._on_mqtt_disconnect)
        connected = self.mqtt_client.connect()
        
        if connected:
            _LOGGER.info("MQTT client started successfully")
        else:
            _LOGGER.error("MQTT client failed to connect")
        
        if connected:
            places_list = []
            if isinstance(self.places, dict):
                places_data = self.places.get("data", self.places)
                if "rows" in places_data:
                    places_list = places_data["rows"]
                elif isinstance(places_data, list):
                    places_list = places_data
            elif isinstance(self.places, list):
                places_list = self.places
            
            if places_list:
                first_place = places_list[0]
                place_id = first_place.get("placeId") or first_place.get("placeid")
                _LOGGER.info(f"[MQTT_SYNC] Triggering device status sync after MQTT connect, place_id={place_id}")
                try:
                    sync_result = self.api.sync_device_status(place_id)
                    self._update_device_states_from_sync(sync_result)
                    _LOGGER.info(f"[MQTT_SYNC] Sync completed, {len(self.device_states)} device states updated")
                except Exception as e:
                    _LOGGER.error(f"[MQTT_SYNC] Failed to sync device status: {e}")
        else:
            _LOGGER.warning("MQTT client failed to connect, falling back to polling")
        
        return connected

    def stop_mqtt(self):
        if self.mqtt_client:
            self.mqtt_client.disconnect()
            self.mqtt_client = None

    def _on_mqtt_message(self, payload):
        try:
            if isinstance(payload, bytes):
                payload = payload.decode("utf-8")
            
            _LOGGER.info(f"[MQTT_RECV] Raw payload: {payload[:500]}")
            
            try:
                data = json.loads(payload)
            except json.JSONDecodeError:
                try:
                    data = json.loads(f"{{{payload}}}")
                except json.JSONDecodeError:
                    _LOGGER.warning(f"[MQTT_ERROR] Failed to parse JSON: {payload[:200]}")
                    return
            
            if not isinstance(data, dict):
                return
            
            mqtt_devicename = data.get("devicename")
            mqtt_payload = data.get("payload")
            mqtt_productkey = data.get("productkey")
            
            _LOGGER.info(f"[MQTT_RECV] devicename={mqtt_devicename}, productkey={mqtt_productkey}, payload={str(mqtt_payload)[:200]}")
            
            if mqtt_devicename and mqtt_payload:
                device_id = self._find_device_id_by_iot_name(mqtt_devicename, mqtt_productkey)
                
                if device_id:
                    device_id_str = str(device_id)
                    state_data = self._parse_mqtt_payload(mqtt_payload)
                    
                    if state_data:
                        old_state = self.device_states.get(device_id_str)
                        state_changed = old_state != state_data
                        
                        force_refresh = False
                        if self._mqtt_last_disconnect_time > 0:
                            import time
                            if time.time() - self._mqtt_last_disconnect_time < 10:
                                force_refresh = True
                                _LOGGER.info(f"[MQTT_UPDATE] Force refresh after reconnection for device {device_id_str}")
                        
                        self.device_states[device_id_str] = state_data
                        
                        if state_changed or force_refresh:
                            _LOGGER.info(f"[MQTT_UPDATE] device_id={device_id_str} state changed: {old_state} -> {state_data}")
                            self._schedule_refresh()
                        else:
                            _LOGGER.debug(f"[MQTT_UPDATE] device_id={device_id_str} state unchanged, skipping refresh")
                    else:
                        _LOGGER.warning(f"[MQTT_ERROR] Failed to parse payload for device {device_id_str}: {mqtt_payload[:200]}")
                else:
                    _LOGGER.warning(f"[MQTT_ERROR] No device found for devicename={mqtt_devicename}, productkey={mqtt_productkey}")
            else:
                _LOGGER.info(f"[MQTT_OTHER] Unknown message format: {data}")
                    
        except Exception as e:
            _LOGGER.error(f"[MQTT_ERROR] Error processing message: {e}")

    def _find_device_id_by_iot_name(self, iot_device_name, iot_product_key=None):
        """Find device ID by IoT device name and product key."""
        for device_id, device in self.devices.items():
            platform_device_id = device.get("platformdeviceid") or device.get("platformDeviceId")
            
            if platform_device_id:
                if iot_product_key:
                    expected_platform_id = f"{iot_product_key}_{iot_device_name}"
                    if platform_device_id == expected_platform_id:
                        _LOGGER.info(f"[MQTT_MATCH] Found device {device_id} by platformdeviceid={platform_device_id}")
                        return device_id
                
                if iot_device_name in platform_device_id:
                    _LOGGER.info(f"[MQTT_MATCH] Found device {device_id} by platformdeviceid containing {iot_device_name}")
                    return device_id
        
        for device_id, device in self.devices.items():
            device_iot_name = device.get("iotdevicename") or device.get("iotDeviceName")
            device_iot_key = device.get("iotproductkey") or device.get("iotProductKey")
            
            if device_iot_name and device_iot_name == iot_device_name:
                if iot_product_key is None or (device_iot_key and device_iot_key == iot_product_key):
                    _LOGGER.debug(f"[MQTT_MATCH] Found device {device_id} by iotdevicename={iot_device_name}")
                    return device_id
        
        for device_id, device in self.devices.items():
            device_name = device.get("devicename") or device.get("deviceName")
            if device_name and device_name == iot_device_name:
                _LOGGER.debug(f"[MQTT_MATCH] Found device {device_id} by devicename={iot_device_name}")
                return device_id
        
        return None

    def _parse_mqtt_payload(self, payload):
        """Parse MQTT payload to device state data."""
        try:
            if isinstance(payload, dict):
                return payload
            
            if isinstance(payload, str):
                try:
                    return json.loads(payload)
                except json.JSONDecodeError:
                    pass
                
                if payload.startswith("66BB") or payload.startswith("66bb"):
                    return self._parse_hex_payload(payload)
                
                _LOGGER.debug(f"[MQTT_PARSE] Unrecognized payload format: {payload[:100]}")
            
            return None
        except Exception as e:
            _LOGGER.error(f"[MQTT_PARSE] Error parsing payload: {e}")
            return None

    def _parse_hex_payload(self, hex_str):
        """Parse hex payload (66BB...EB format) to device state.
        
        Format: 66BB + reserved(0000) + cmd_subtype + value + EB
        - cmd_subtype=00: switch, value is 0 or 1
        - cmd_subtype=01: brightness, value is 0-255
        - cmd_subtype=02: color temperature, value is 2 bytes
        """
        try:
            hex_str = hex_str.upper()
            if not (hex_str.startswith("66BB") and hex_str.endswith("EB")):
                return None
            
            data = hex_str[4:-2]
            if len(data) < 10:
                return None
            
            state = {"CharSwitch": hex_str}
            
            cmd_subtype = int(data[6:8], 16)
            
            if cmd_subtype == 0x02:
                if len(data) >= 12:
                    color_temp_value = int(data[8:12], 16)
                else:
                    return None
                
                if color_temp_value > 0:
                    state["CharTemp"] = f"66BB00000002{color_temp_value:04X}EB"
                state["is_on"] = True
            elif cmd_subtype == 0x01:
                brightness_value = int(data[8:10], 16)
                if brightness_value > 0:
                    state["CharBrightness"] = f"66BB00000001{brightness_value:02X}EB"
                state["is_on"] = brightness_value > 0
            else:
                value = int(data[8:10], 16)
                state["is_on"] = value == 1
            
            _LOGGER.debug(f"[MQTT_PARSE] Parsed hex payload: {hex_str[:50]} -> {state}")
            return state
        except Exception as e:
            _LOGGER.error(f"[MQTT_PARSE] Error parsing hex payload: {e}")
            return None

    def _schedule_refresh(self):
        try:
            self.hass.loop.call_soon_threadsafe(
                lambda: self.hass.async_create_task(self.async_refresh())
            )
        except Exception as e:
            _LOGGER.error(f"[MQTT_ERROR] Failed to schedule refresh: {e}")

    async def start_mesh(self, place_id):
        if self.mesh_manager:
            await self.mesh_manager.disconnect()

        try:
            place_info = await self.hass.async_add_executor_job(self.api.get_place_info, place_id)
            
            if place_info and isinstance(place_info, dict):
                info = place_info.get("info", {})
                if isinstance(info, dict):
                    net_key = info.get("netkey")
                    app_key = info.get("applicationkey")
                    mesh_uuid = info.get("meshuuid")
                    iv_index = info.get("ivindex", 0)
                    
                    if net_key and mesh_uuid:
                        _LOGGER.info(f"Mesh keys found: netkey={net_key[:8]}..., appkey={app_key[:8]}..., uuid={mesh_uuid[:8]}..., iv_index={iv_index}")
                        
                        self.mesh_manager = LtechMeshManager()
                        self.mesh_manager.set_keys(net_key, app_key, mesh_uuid)
                        self.mesh_manager.set_iv_index(iv_index)
                        self.mesh_manager.set_message_callback(self._on_mesh_message)
                        
                        await self.mesh_manager.connect()
                        
                        if self.mesh_manager.connected:
                            self.mesh_enabled = True
                            _LOGGER.info("Bluetooth Mesh connected successfully")
                            
                            device_addresses = {}
                            for device_id, device in self.devices.items():
                                mesh_addr = device.get("meshaddr") or device.get("meshAddress")
                                if mesh_addr:
                                    try:
                                        addr_int = int(mesh_addr)
                                        device_addresses[device_id] = addr_int
                                        _LOGGER.debug(f"[MESH_ADDR] device_id={device_id}, meshaddr={addr_int}")
                                    except ValueError:
                                        _LOGGER.debug(f"[MESH_ADDR] Invalid meshaddr for device {device_id}: {mesh_addr}")
                            
                            self.mesh_manager.set_device_addresses(device_addresses)
                            _LOGGER.info(f"[MESH] Set {len(device_addresses)} device addresses")
                            
                            device_keys_map = {}
                            for device_id, device in self.devices.items():
                                device_key = None
                                
                                param_str = device.get("param", "")
                                if param_str:
                                    try:
                                        param_data = json.loads(param_str) if isinstance(param_str, str) else param_str
                                        if isinstance(param_data, dict):
                                            device_key = param_data.get("deviceKey") or param_data.get("devicekey")
                                    except (json.JSONDecodeError, TypeError):
                                        pass
                                
                                if not device_key:
                                    device_key = device.get("deviceKey") or device.get("devicekey")
                                
                                if device_key:
                                    device_keys_map[str(device_id)] = device_key
                                    _LOGGER.debug(f"[MESH_KEY] device_id={device_id}, device_key={device_key[:8]}...")
                            
                            if device_keys_map:
                                self.mesh_manager.set_device_keys(device_keys_map)
                                _LOGGER.info(f"[MESH] Set {len(device_keys_map)} device keys")
                        else:
                            _LOGGER.warning("Bluetooth Mesh connection failed, falling back to cloud API")
                    else:
                        _LOGGER.info("Mesh keys not found, skipping Mesh setup")
                else:
                    _LOGGER.info("Place info is not a dict, skipping Mesh setup")
            else:
                _LOGGER.info("Place info API returned empty, skipping Mesh setup")
                
        except Exception as e:
            _LOGGER.info(f"Mesh setup skipped due to API error: {e}")

        return self.mesh_enabled

    async def stop_mesh(self):
        if self.mesh_manager:
            await self.mesh_manager.disconnect()
            self.mesh_manager = None
            self.mesh_enabled = False
            _LOGGER.info("Bluetooth Mesh disconnected")

    async def control_device_via_mesh(self, device_id, control_type, value):
        if not self.mesh_enabled or not self.mesh_manager:
            _LOGGER.debug(f"[MESH] Mesh not enabled, falling back to cloud API for device {device_id}")
            return False

        try:
            if control_type == "on":
                result = await self.mesh_manager.set_device_on(device_id, value)
            elif control_type == "brightness":
                result = await self.mesh_manager.set_device_brightness(device_id, value)
            elif control_type == "color_temp":
                result = await self.mesh_manager.set_device_color_temp(device_id, value)
            elif control_type == "control":
                result = await self.mesh_manager.send_device_control(device_id, value)
            else:
                _LOGGER.warning(f"[MESH] Unknown control type: {control_type}")
                result = False

            if result:
                _LOGGER.info(f"[MESH] Successfully controlled device {device_id} via Mesh: {control_type}={value}")
            else:
                _LOGGER.warning(f"[MESH] Failed to control device {device_id} via Mesh")

            return result
        except Exception as e:
            _LOGGER.error(f"[MESH] Error controlling device {device_id}: {e}")
            return False

    def control_device_via_mqtt(self, device_id, control_data):
        if not self.mqtt_client or not self.mqtt_client.is_connected():
            _LOGGER.debug(f"[MQTT_CONTROL] MQTT not connected, falling back to cloud API for device {device_id}")
            return False

        try:
            _LOGGER.info(f"[MQTT_CONTROL] Publishing control data for device {device_id}: {str(control_data)[:200]}")
            result = self.mqtt_client.publish(control_data)
            
            if result:
                _LOGGER.info(f"[MQTT_CONTROL] Successfully published control data for device {device_id}")
            else:
                _LOGGER.warning(f"[MQTT_CONTROL] Failed to publish control data for device {device_id}")
            
            return result
        except Exception as e:
            _LOGGER.error(f"[MQTT_CONTROL] Error publishing control data for device {device_id}: {e}")
            return False

    def _on_mesh_message(self, message):
        try:
            _LOGGER.info(f"[MESH_MSG] Decrypted message: {message}")
            
            device_id = None
            state_update = {}
            
            dst_addr = message.get("dst")
            if dst_addr is not None and self.mesh_manager:
                device_id = self.mesh_manager.get_device_by_address(dst_addr)
            
            if not device_id:
                src_addr = message.get("src")
                if src_addr is not None and self.mesh_manager:
                    device_id = self.mesh_manager.get_device_by_address(src_addr)
            
            msg_type = message.get("type", "")
            
            if msg_type == "leite_vendor_model":
                sub_type = message.get("sub_type", "")
                
                if sub_type in ("device_control", "device_status"):
                    on_status = message.get("on")
                    if on_status is not None:
                        state_update["is_on"] = on_status
                        status_val = 1 if on_status else 0
                        state_update["CharSwitch"] = f"66BB000000000{status_val:02X}EB"
                    
                    brightness = message.get("brightness")
                    if brightness is not None:
                        state_update["CharBrightness"] = f"66BB00000001{brightness:02X}EB"
                    
                    temp_mired = message.get("color_temp_mired")
                    if temp_mired is not None:
                        state_update["CharTemp"] = f"66BB00000002{temp_mired:04X}EB"
                
                elif sub_type == "brightness":
                    brightness = message.get("brightness", 0)
                    state_update["CharBrightness"] = f"66BB00000001{brightness:02X}EB"
                    
                elif sub_type == "color_temp":
                    temp_mired = message.get("color_temp_mired", 0)
                    state_update["CharTemp"] = f"66BB00000002{temp_mired:04X}EB"
                    
            elif msg_type == "vendor_model":
                opcode = message.get("opcode", 0)
                params_hex = message.get("parameters", "")
                _LOGGER.debug(f"[MESH_MSG] Unknown vendor model: opcode=0x{opcode:04X}, params={params_hex}")
                        
            elif msg_type == "generic_onoff_status":
                on_val = message.get("on", False)
                state_update["is_on"] = on_val
                state_update["CharSwitch"] = f"66BB000000000{1 if on_val else 0:02X}EB"
                
            elif msg_type == "generic_level_status":
                level = message.get("level", 0)
                state_update["CharBrightness"] = f"66BB00000001{level:02X}EB"
            
            if device_id and state_update:
                device_id_str = str(device_id)
                old_state = self.device_states.get(device_id_str, {})
                self.device_states[device_id_str] = {**old_state, **state_update}
                _LOGGER.info(f"[MESH_UPDATE] device_id={device_id_str} updated state: {state_update}")
                self._schedule_refresh()
            elif device_id:
                _LOGGER.debug(f"[MESH_MSG] Message for device {device_id} but no state parsed, refreshing")
                self._schedule_refresh()
            else:
                _LOGGER.debug(f"[MESH_MSG] Cannot map address to device, refreshing")
                self._schedule_refresh()
                
        except Exception as e:
            _LOGGER.error(f"Error processing Mesh message: {e}")

    async def send_mesh_command(self, device_address, command, **kwargs):
        if not self.mesh_manager or not self.mesh_manager.connected:
            return False

        try:
            if command == "onoff":
                return await self.mesh_manager.send_generic_onoff(device_address, kwargs.get("on", True))
            elif command == "level":
                return await self.mesh_manager.send_generic_level(device_address, kwargs.get("level", 0))
            elif command == "vendor":
                return await self.mesh_manager.send_vendor_model(
                    device_address,
                    kwargs.get("opcode", 0),
                    kwargs.get("parameters", b""),
                    kwargs.get("app_key_index", 0)
                )
        except Exception as e:
            _LOGGER.error(f"Failed to send Mesh command: {e}")

        return False