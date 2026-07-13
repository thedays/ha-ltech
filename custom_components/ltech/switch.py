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
    
    _LOGGER.info(f"Found {len(switches)} switch devices: {[d.get('productId') or d.get('productid', 'N/A') for d in switches]}")
    _LOGGER.info(f"All devices count: {len(coordinator.devices)}")
    
    entities = []
    for device in switches:
        entities.append(LtechSwitch(coordinator, device))
    
    _LOGGER.info(f"Adding {len(entities)} switch entities")
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

    def _parse_state_value(self, hex_string):
        if not isinstance(hex_string, str) or len(hex_string) < 8:
            return None
        
        try:
            hex_string = hex_string.upper()
            if hex_string.startswith("66BB") and hex_string.endswith("EB"):
                data = hex_string[4:-2]
                if len(data) >= 8:
                    value_hex = data[-2:]
                    return int(value_hex, 16)
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