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

    async def _async_update_data(self):
        try:
            if not self.places:
                self.places = await self.hass.async_add_executor_job(self.api.get_place_list)
            
            places_list = []
            if isinstance(self.places, dict) and "rows" in self.places:
                places_list = self.places["rows"]
            elif isinstance(self.places, list):
                places_list = self.places
            
            if places_list:
                first_place = places_list[0]
                place_id = first_place.get("placeId") or first_place.get("placeid")
                self.api.select_place(place_id)
                
                device_list = await self.hass.async_add_executor_job(
                    self.api.get_device_list, place_id
                )
                
                await self.hass.async_add_executor_job(
                    self.api.sync_device_status, place_id
                )
                
                if isinstance(device_list, dict) and "rows" in device_list:
                    self.devices = {}
                    device_name_counts = {}
                    for device in device_list["rows"]:
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

    def start_mqtt(self):
        if self.mqtt_client:
            self.mqtt_client.disconnect()

        self.mqtt_client = LtechMqttClient(self.api, self._on_mqtt_message)
        connected = self.mqtt_client.connect()
        
        if connected:
            _LOGGER.info("MQTT client started successfully")
            places_list = []
            if isinstance(self.places, dict) and "rows" in self.places:
                places_list = self.places["rows"]
            elif isinstance(self.places, list):
                places_list = self.places
            
            if places_list:
                first_place = places_list[0]
                place_id = first_place.get("placeId") or first_place.get("placeid")
                _LOGGER.info(f"[MQTT_SYNC] Triggering device status sync after MQTT connect, place_id={place_id}")
                self.api.sync_device_status(place_id)
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
                data = json.loads(f"{{{payload}}}")
            
            if isinstance(data, dict):
                message_type = data.get("messagetype")
                device_id = data.get("deviceid")
                report_instruct = data.get("reportinstruct")
                
                _LOGGER.info(f"[MQTT_RECV] messagetype={message_type}, deviceid={device_id}, reportinstruct={report_instruct}")
                
                if message_type == 29 and device_id and report_instruct:
                    device_id_str = str(device_id)
                    try:
                        if isinstance(report_instruct, str):
                            state_data = json.loads(report_instruct)
                        else:
                            state_data = report_instruct
                        
                        if isinstance(state_data, dict):
                            self.device_states[device_id_str] = state_data
                            _LOGGER.info(f"[MQTT_UPDATE] device_id={device_id_str}, state={state_data}")
                            self.hass.async_create_task(self.async_refresh())
                    except (json.JSONDecodeError, TypeError) as e:
                        _LOGGER.warning(f"[MQTT_ERROR] Failed to parse reportinstruct: {e}")
                        self.hass.async_create_task(self.async_refresh())
                elif message_type == 2 and device_id:
                    device_id_str = str(device_id)
                    state = data.get("state")
                    if device_id_str in self.devices and state is not None:
                        self.devices[device_id_str]["onlineflag"] = int(state)
                        _LOGGER.info(f"[MQTT_ONLINE] device_id={device_id_str}, online={state}")
                        self.hass.async_create_task(self.async_refresh())
                    else:
                        self.hass.async_create_task(self.async_refresh())
                else:
                    _LOGGER.info(f"[MQTT_OTHER] message_type={message_type}, deviceid={device_id}")
                    self.hass.async_create_task(self.async_refresh())
                    
        except json.JSONDecodeError:
            _LOGGER.warning(f"[MQTT_ERROR] Failed to parse JSON: {payload[:200]}")
            self.hass.async_create_task(self.async_refresh())
        except Exception as e:
            _LOGGER.error(f"[MQTT_ERROR] Error processing message: {e}")

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
                    
                    if net_key and mesh_uuid:
                        _LOGGER.info(f"Mesh keys found: netkey={net_key[:8]}..., appkey={app_key[:8]}..., uuid={mesh_uuid[:8]}...")
                        
                        self.mesh_manager = LtechMeshManager()
                        self.mesh_manager.set_keys(net_key, app_key, mesh_uuid)
                        self.mesh_manager.set_message_callback(self._on_mesh_message)
                        
                        await self.mesh_manager.connect()
                        
                        if self.mesh_manager.connected:
                            self.mesh_enabled = True
                            _LOGGER.info("Bluetooth Mesh connected successfully")
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

    def _on_mesh_message(self, message):
        try:
            _LOGGER.debug(f"Mesh message received: {message}")
            self.hass.async_create_task(self.async_refresh())
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