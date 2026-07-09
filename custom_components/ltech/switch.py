import logging

from homeassistant.components.switch import SwitchEntity
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity_platform import AddEntitiesCallback

from .api import LtechApiError
from .const import DOMAIN, SWITCH_PRODUCT_IDS
from .coordinator import LtechDataUpdateCoordinator
from .entity import LtechEntity

_LOGGER = logging.getLogger(__name__)


async def async_setup_entry(
    hass: HomeAssistant,
    config_entry: ConfigEntry,
    async_add_entities: AddEntitiesCallback,
) -> None:
    coordinator = hass.data[DOMAIN][config_entry.entry_id]
    switches = coordinator.get_devices_by_type(SWITCH_PRODUCT_IDS)
    
    entities = []
    for device in switches:
        entities.append(LtechSwitch(coordinator, device))
    
    async_add_entities(entities)


class LtechSwitch(LtechEntity, SwitchEntity):
    def __init__(self, coordinator, device):
        super().__init__(coordinator, device)
        self._is_on = False

    @property
    def is_on(self):
        return self._is_on

    async def async_turn_on(self, **kwargs):
        try:
            await self.hass.async_add_executor_job(
                self.coordinator.api.control_switch,
                self.device_id,
                True,
            )
            
            self._is_on = True
            self.async_write_ha_state()
        
        except LtechApiError as e:
            _LOGGER.error("Failed to turn on switch: %s", e)

    async def async_turn_off(self, **kwargs):
        try:
            await self.hass.async_add_executor_job(
                self.coordinator.api.control_switch,
                self.device_id,
                False,
            )
            
            self._is_on = False
            self.async_write_ha_state()
        
        except LtechApiError as e:
            _LOGGER.error("Failed to turn off switch: %s", e)

    async def async_update(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
            
            device_state = device.get("deviceState", {})
            if isinstance(device_state, dict):
                state_value = device_state.get("CharSwitch")
                if state_value is not None:
                    self._is_on = state_value != "0"