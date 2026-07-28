import json
import logging

from homeassistant.components.light import (
    ATTR_BRIGHTNESS,
    ATTR_COLOR_TEMP_KELVIN,
    ColorMode,
    LightEntity,
)
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity_platform import AddEntitiesCallback

from .api import LtechApiError, LtechAuthError
from .const import DOMAIN, LIGHT_PRODUCT_IDS
from .coordinator import LtechDataUpdateCoordinator
from .entity import LtechEntity

_LOGGER = logging.getLogger(__name__)


async def async_setup_entry(
    hass: HomeAssistant,
    config_entry: ConfigEntry,
    async_add_entities: AddEntitiesCallback,
) -> None:
    coordinator = hass.data[DOMAIN][config_entry.entry_id]
    lights = coordinator.get_devices_by_type(LIGHT_PRODUCT_IDS)
    
    _LOGGER.info(f"Found {len(lights)} light devices: {[d.get('productId') or d.get('productid', 'N/A') for d in lights]}")
    _LOGGER.info(f"All devices count: {len(coordinator.devices)}")
    _LOGGER.info(f"All devices productIds: {[d.get('productId') or d.get('productid', 'N/A') for d in coordinator.devices.values()]}")
    
    entities = []
    for device in lights:
        entities.append(LtechLight(coordinator, device))
    
    _LOGGER.info(f"Adding {len(entities)} light entities")
    async_add_entities(entities)


class LtechLight(LtechEntity, LightEntity):
    def __init__(self, coordinator, device):
        super().__init__(coordinator, device)

    def _get_device_state(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
        
        realtime_state = self.coordinator.get_device_state(self.device_id)
        if realtime_state:
            _LOGGER.debug(f"[LIGHT_STATE] Using realtime state for device_id={self.device_id}: {realtime_state}")
            return realtime_state
        
        device_state = self.device.get("deviceState", {})
        if isinstance(device_state, str):
            try:
                import json
                device_state = json.loads(device_state)
            except (json.JSONDecodeError, TypeError):
                device_state = {}
        
        if isinstance(device_state, dict) and device_state:
            _LOGGER.debug(f"[LIGHT_STATE] Using deviceState for device_id={self.device_id}: {device_state}")
            return device_state
        
        _LOGGER.debug(f"[LIGHT_STATE] No reliable state available for device_id={self.device_id}, returning empty state (default off)")
        return {}

    @property
    def color_mode(self):
        product_name = self.device.get("productname", "")
        param = self.device.get("param", "{}")
        
        if "RGB" in product_name:
            return ColorMode.HS
        
        if "色温" in product_name:
            return ColorMode.COLOR_TEMP
        
        if "调光" in product_name:
            return ColorMode.BRIGHTNESS
        
        return ColorMode.ONOFF

    @property
    def supported_color_modes(self):
        product_name = self.device.get("productname", "")
        
        if "RGB" in product_name:
            return {ColorMode.HS, ColorMode.COLOR_TEMP}
        
        if "色温" in product_name:
            return {ColorMode.COLOR_TEMP}
        
        if "调光" in product_name:
            return {ColorMode.BRIGHTNESS}
        
        return {ColorMode.ONOFF}

    @property
    def brightness(self):
        device_state = self._get_device_state()
        brightness_value = device_state.get("CharBrightness")
        if brightness_value is not None:
            parsed = self._parse_state_value(brightness_value)
            if parsed is not None:
                return int((parsed / 100) * 255)
        return None

    @property
    def color_temp(self):
        device_state = self._get_device_state()
        temp_value = device_state.get("CharTemp")
        if temp_value is not None:
            parsed = self._parse_state_value(temp_value)
            if parsed is not None and parsed > 0:
                return 1000000 // parsed
        return None

    @property
    def min_mireds(self):
        return 153

    @property
    def max_mireds(self):
        return 500

    @property
    def is_on(self):
        device_state = self._get_device_state()
        _LOGGER.debug(f"[LIGHT_STATE] device_id={self.device_id}, device_name={self.device_name}, device_state={device_state}")
        
        if "is_on" in device_state:
            _LOGGER.debug(f"[LIGHT_STATE] Using is_on from state: {device_state['is_on']}")
            return device_state["is_on"]
        
        for field in ["CharSwitch", "CharBrightness", "CharTemp"]:
            state_value = device_state.get(field)
            if state_value is not None and isinstance(state_value, str) and state_value.upper().startswith("66BB"):
                parsed = self._parse_state_value(state_value)
                if parsed is not None:
                    if field == "CharSwitch":
                        result = parsed == 1
                    else:
                        result = parsed > 0
                    _LOGGER.debug(f"[LIGHT_STATE] Parsed {field}={state_value[:30]}, parsed={parsed}, is_on={result}")
                    return result
        
        _LOGGER.debug(f"[LIGHT_STATE] No state fields found, defaulting to off")
        return False

    async def async_turn_on(self, **kwargs):
        brightness = kwargs.get(ATTR_BRIGHTNESS)
        color_temp_kelvin = kwargs.get(ATTR_COLOR_TEMP_KELVIN)
        
        try:
            platform_device_id = self.coordinator.get_platform_device_id(self.device_id)
            
            _LOGGER.info(f"[LIGHT_CONTROL] Turning ON light {self.device_id} ({self.device_name}), platform_device_id={platform_device_id}, brightness={brightness}, color_temp={color_temp_kelvin}")
            _LOGGER.info(f"[LIGHT_CONTROL] mesh_enabled={self.coordinator.mesh_enabled}, mqtt_connected={self.coordinator.mqtt_client.is_connected() if self.coordinator.mqtt_client else False}")
            
            mesh_success = False
            if self.coordinator.mesh_enabled and self.coordinator.mesh_manager:
                _LOGGER.info(f"[LIGHT_CONTROL] Attempting Mesh control for {self.device_id}")
                mesh_success = await self.coordinator.control_device_via_mesh(self.device_id, "on", True)
                _LOGGER.info(f"[LIGHT_CONTROL] Mesh on result: {mesh_success}")
                if brightness is not None:
                    bright_result = await self.coordinator.control_device_via_mesh(self.device_id, "brightness", brightness)
                    _LOGGER.info(f"[LIGHT_CONTROL] Mesh brightness result: {bright_result}")
                if color_temp_kelvin is not None:
                    color_temp_mired = 1000000 // color_temp_kelvin
                    temp_result = await self.coordinator.control_device_via_mesh(self.device_id, "color_temp", color_temp_mired)
                    _LOGGER.info(f"[LIGHT_CONTROL] Mesh color_temp result: {temp_result}")
            else:
                _LOGGER.info(f"[LIGHT_CONTROL] Mesh not enabled, will use cloud fallback")
            
            if not mesh_success:
                _LOGGER.info(f"[LIGHT_CONTROL] Attempting API control for {self.device_id}")
                try:
                    api_result = await self.hass.async_add_executor_job(
                        self.coordinator.api.control_light,
                        self.device_id,
                        True,
                        brightness,
                        color_temp_kelvin,
                        platform_device_id,
                    )
                    _LOGGER.info(f"[LIGHT_CONTROL] API control result: {api_result}")
                except Exception as api_error:
                    _LOGGER.warning(f"[MQTT_CONTROL] API control failed, continuing with MQTT: {api_error}")
                    import traceback
                    _LOGGER.error(f"[MQTT_CONTROL] API error traceback: {traceback.format_exc()}")
                
                mqtt_success = False
                _LOGGER.info(f"[LIGHT_CONTROL] mqtt_client={self.coordinator.mqtt_client is not None}, is_connected={self.coordinator.mqtt_client.is_connected() if self.coordinator.mqtt_client else 'N/A'}")
                if self.coordinator.mqtt_client and self.coordinator.mqtt_client.is_connected():
                    mqtt_data = {
                        "deviceid": int(self.device_id),
                        "CharSwitch": "66BB0000000001EB"
                    }
                    if brightness is not None:
                        brightness_hex = f"{int((brightness / 255) * 100):02X}"
                        mqtt_data["CharBrightness"] = f"66BB00000001{brightness_hex}EB"
                    if color_temp_kelvin is not None:
                        color_temp_mired = 1000000 // color_temp_kelvin
                        temp_hex = f"{color_temp_mired:04X}"
                        mqtt_data["CharTemp"] = f"66BB00000002{temp_hex}EB"
                    if platform_device_id:
                        mqtt_data["platformdeviceid"] = platform_device_id
                    
                    mqtt_payload = json.dumps(mqtt_data)
                    _LOGGER.info(f"[LIGHT_CONTROL] Sending MQTT control: {mqtt_payload}")
                    mqtt_success = await self.hass.async_add_executor_job(
                        self.coordinator.control_device_via_mqtt,
                        self.device_id,
                        mqtt_payload
                    )
                    _LOGGER.info(f"[MQTT_CONTROL] Light {self.device_id} MQTT control: {mqtt_success}, payload: {mqtt_payload}")
                else:
                    _LOGGER.warning(f"[MQTT_CONTROL] MQTT client not connected, cannot send control command")
            
            _LOGGER.info(f"[LIGHT_CONTROL] Updating state for {self.device_id}: is_on=True")
            self.coordinator.device_states[self.device_id] = {"is_on": True}
            if brightness is not None:
                self.coordinator.device_states[self.device_id]["CharBrightness"] = hex(int((brightness / 255) * 100))[2:].upper().zfill(2)
            self.schedule_update_ha_state()
            
            _LOGGER.info(f"[LIGHT_CONTROL] Refreshing state for {self.device_id}")
            await self.coordinator.async_refresh()
            _LOGGER.info(f"[LIGHT_CONTROL] Turn ON completed for {self.device_id}")
        
        except LtechAuthError as e:
            _LOGGER.error("Authentication failed when turning on light, please check credentials: %s", e)
        except LtechApiError as e:
            _LOGGER.error("Failed to turn on light: %s", e)
        except Exception as e:
            _LOGGER.error(f"Unexpected error turning on light: {e}")
            import traceback
            _LOGGER.error(f"Traceback: {traceback.format_exc()}")

    async def async_turn_off(self, **kwargs):
        try:
            platform_device_id = self.coordinator.get_platform_device_id(self.device_id)
            
            _LOGGER.info(f"[LIGHT_CONTROL] Turning OFF light {self.device_id} ({self.device_name}), platform_device_id={platform_device_id}")
            _LOGGER.info(f"[LIGHT_CONTROL] mesh_enabled={self.coordinator.mesh_enabled}, mqtt_connected={self.coordinator.mqtt_client.is_connected() if self.coordinator.mqtt_client else False}")
            
            mesh_success = False
            if self.coordinator.mesh_enabled and self.coordinator.mesh_manager:
                _LOGGER.info(f"[LIGHT_CONTROL] Attempting Mesh control for {self.device_id}")
                mesh_success = await self.coordinator.control_device_via_mesh(self.device_id, "on", False)
                _LOGGER.info(f"[LIGHT_CONTROL] Mesh off result: {mesh_success}")
            else:
                _LOGGER.info(f"[LIGHT_CONTROL] Mesh not enabled, will use cloud fallback")
            
            if not mesh_success:
                _LOGGER.info(f"[LIGHT_CONTROL] Attempting API control for {self.device_id}")
                try:
                    api_result = await self.hass.async_add_executor_job(
                        self.coordinator.api.control_light,
                        self.device_id,
                        False,
                        None,
                        None,
                        platform_device_id,
                    )
                    _LOGGER.info(f"[LIGHT_CONTROL] API control result: {api_result}")
                except Exception as api_error:
                    _LOGGER.warning(f"[MQTT_CONTROL] API control failed, continuing with MQTT: {api_error}")
                    import traceback
                    _LOGGER.error(f"[MQTT_CONTROL] API error traceback: {traceback.format_exc()}")
                
                mqtt_success = False
                _LOGGER.info(f"[LIGHT_CONTROL] mqtt_client={self.coordinator.mqtt_client is not None}, is_connected={self.coordinator.mqtt_client.is_connected() if self.coordinator.mqtt_client else 'N/A'}")
                if self.coordinator.mqtt_client and self.coordinator.mqtt_client.is_connected():
                    mqtt_data = {
                        "deviceid": int(self.device_id),
                        "CharSwitch": "66BB0000000000EB"
                    }
                    if platform_device_id:
                        mqtt_data["platformdeviceid"] = platform_device_id
                    
                    mqtt_payload = json.dumps(mqtt_data)
                    _LOGGER.info(f"[LIGHT_CONTROL] Sending MQTT control: {mqtt_payload}")
                    mqtt_success = await self.hass.async_add_executor_job(
                        self.coordinator.control_device_via_mqtt,
                        self.device_id,
                        mqtt_payload
                    )
                    _LOGGER.info(f"[MQTT_CONTROL] Light {self.device_id} MQTT control off: {mqtt_success}, payload: {mqtt_payload}")
                else:
                    _LOGGER.warning(f"[MQTT_CONTROL] MQTT client not connected, cannot send control command")
            
            _LOGGER.info(f"[LIGHT_CONTROL] Updating state for {self.device_id}: is_on=False")
            self.coordinator.device_states[self.device_id] = {"is_on": False}
            self.schedule_update_ha_state()
            
            _LOGGER.info(f"[LIGHT_CONTROL] Refreshing state for {self.device_id}")
            await self.coordinator.async_refresh()
            _LOGGER.info(f"[LIGHT_CONTROL] Turn OFF completed for {self.device_id}")
        
        except LtechAuthError as e:
            _LOGGER.error("Authentication failed when turning off light, please check credentials: %s", e)
        except LtechApiError as e:
            _LOGGER.error("Failed to turn off light: %s", e)
        except Exception as e:
            _LOGGER.error(f"Unexpected error turning off light: {e}")
            import traceback
            _LOGGER.error(f"Traceback: {traceback.format_exc()}")

    def _parse_state_value(self, hex_string):
        if not isinstance(hex_string, str) or len(hex_string) < 8:
            return None
        
        try:
            hex_string = hex_string.upper()
            if hex_string.startswith("66BB") and hex_string.endswith("EB"):
                data = hex_string[4:-2]
                if len(data) >= 10:
                    cmd_subtype = int(data[6:8], 16)
                    
                    if cmd_subtype == 0x02:
                        if len(data) >= 12:
                            return int(data[8:12], 16)
                    else:
                        return int(data[8:10], 16)
            return int(hex_string, 16)
        except (ValueError, TypeError):
            return None

    async def async_update(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
            
            device_state = device.get("deviceState", {})
            if isinstance(device_state, dict):
                state_value = device_state.get("CharSwitch")
                if state_value is not None:
                    parsed = self._parse_state_value(state_value)
                    self._is_on = parsed == 1 if parsed is not None else False
                
                brightness_value = device_state.get("CharBrightness")
                if brightness_value is not None:
                    parsed = self._parse_state_value(brightness_value)
                    if parsed is not None:
                        self._brightness = int((parsed / 100) * 255)
                
                temp_value = device_state.get("CharTemp")
                if temp_value is not None:
                    parsed = self._parse_state_value(temp_value)
                    if parsed is not None and parsed > 0:
                        self._color_temp = 1000000 // parsed