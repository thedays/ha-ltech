import logging

from homeassistant.components.light import (
    ATTR_BRIGHTNESS,
    ATTR_COLOR_TEMP,
    ColorMode,
    LightEntity,
)
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity_platform import AddEntitiesCallback

from .api import LtechApiError
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
    
    entities = []
    for device in lights:
        entities.append(LtechLight(coordinator, device))
    
    async_add_entities(entities)


class LtechLight(LtechEntity, LightEntity):
    def __init__(self, coordinator, device):
        super().__init__(coordinator, device)
        self._brightness = None
        self._color_temp = None
        self._is_on = False

    @property
    def color_mode(self):
        product_id = self.product_id
        
        if "COLOR" in product_id:
            return ColorMode.HS
        
        if "CT" in product_id or "PRO" in product_id:
            return ColorMode.COLOR_TEMP
        
        if "DIM" in product_id:
            return ColorMode.BRIGHTNESS
        
        return ColorMode.ONOFF

    @property
    def supported_color_modes(self):
        product_id = self.product_id
        modes = []
        
        if "COLOR" in product_id:
            modes.append(ColorMode.HS)
        
        if "CT" in product_id or "PRO" in product_id:
            modes.append(ColorMode.COLOR_TEMP)
        
        if "DIM" in product_id or "COLOR" in product_id:
            modes.append(ColorMode.BRIGHTNESS)
        
        modes.append(ColorMode.ONOFF)
        
        return set(modes)

    @property
    def brightness(self):
        return self._brightness

    @property
    def color_temp(self):
        return self._color_temp

    @property
    def min_mireds(self):
        return 153

    @property
    def max_mireds(self):
        return 500

    @property
    def is_on(self):
        return self._is_on

    async def async_turn_on(self, **kwargs):
        brightness = kwargs.get(ATTR_BRIGHTNESS)
        color_temp = kwargs.get(ATTR_COLOR_TEMP)
        
        try:
            await self.hass.async_add_executor_job(
                self.coordinator.api.control_light,
                self.device_id,
                True,
                brightness,
                color_temp,
            )
            
            self._is_on = True
            if brightness is not None:
                self._brightness = brightness
            if color_temp is not None:
                self._color_temp = color_temp
            
            self.async_write_ha_state()
        
        except LtechApiError as e:
            _LOGGER.error("Failed to turn on light: %s", e)

    async def async_turn_off(self, **kwargs):
        try:
            await self.hass.async_add_executor_job(
                self.coordinator.api.control_light,
                self.device_id,
                False,
            )
            
            self._is_on = False
            self.async_write_ha_state()
        
        except LtechApiError as e:
            _LOGGER.error("Failed to turn off light: %s", e)

    async def async_update(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
            
            device_state = device.get("deviceState", {})
            if isinstance(device_state, dict):
                state_value = device_state.get("CharSwitch")
                if state_value is not None:
                    self._is_on = state_value != "0"
                
                brightness_value = device_state.get("CharBrightness")
                if brightness_value is not None:
                    try:
                        self._brightness = int(brightness_value, 16)
                    except (ValueError, TypeError):
                        pass
                
                temp_value = device_state.get("CharTemp")
                if temp_value is not None:
                    try:
                        kelvin = int(temp_value, 16)
                        if kelvin > 0:
                            self._color_temp = 1000000 // kelvin
                    except (ValueError, TypeError):
                        pass