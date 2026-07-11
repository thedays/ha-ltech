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
        self.places = []
        self._reauth_attempted = False
        self.mqtt_client = None
        self.mesh_manager = None
        self.mesh_enabled = False

    async def _async_update_data(self):
        try:
            if not self.places:
                self.places = await self.hass.async_add_executor_job(self.api.get_place_list)
            
            if self.places:
                first_place = self.places[0]
                place_id = first_place.get("placeId") or first_place.get("placeid")
                self.api.select_place(place_id)
                
                device_list = await self.hass.async_add_executor_job(
                    self.api.get_device_list, place_id
                )
                
                if isinstance(device_list, dict) and "rows" in device_list:
                    self.devices = {
                        device["deviceId"]: device
                        for device in device_list["rows"]
                    }
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

    def get_devices_by_type(self, product_ids):
        return [
            device for device in self.devices.values()
            if device.get("productId") in product_ids
        ]

    def start_mqtt(self):
        if self.mqtt_client:
            self.mqtt_client.disconnect()

        self.mqtt_client = LtechMqttClient(self.api, self._on_mqtt_message)
        connected = self.mqtt_client.connect()
        
        if connected:
            _LOGGER.info("MQTT client started successfully")
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
            
            try:
                data = json.loads(payload)
            except json.JSONDecodeError:
                data = json.loads(f"{{{payload}}}")
            
            if isinstance(data, dict):
                device_name = data.get("devicename")
                product_key = data.get("productkey")
                payload_data = data.get("payload")
                
                if device_name or product_key:
                    _LOGGER.debug(f"MQTT update received: productkey={product_key}, devicename={device_name}, payload={payload_data}")
                    self.hass.async_create_task(self.async_refresh())
                else:
                    _LOGGER.debug(f"MQTT message received (unknown format): {payload[:100]}...")
                    self.hass.async_create_task(self.async_refresh())
                    
        except json.JSONDecodeError:
            _LOGGER.debug(f"MQTT payload (raw): {payload[:200]}")
            self.hass.async_create_task(self.async_refresh())
        except Exception as e:
            _LOGGER.error(f"Error processing MQTT message: {e}")

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
                        _LOGGER.warning("Mesh keys not found in place info")
            else:
                _LOGGER.warning("Failed to get place info for Mesh")
                
        except Exception as e:
            _LOGGER.error(f"Failed to start Mesh: {e}")

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