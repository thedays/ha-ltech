import json
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

    _LOGGER.info(f"Found {len(switches)} switch devices")

    entities = []
    for device in switches:
        zone_count = _get_zone_count(device)
        device_name = device.get("deviceName", "") or device.get("devicename", "")

        if zone_count > 1:
            _LOGGER.info(f"Device '{device_name}' has {zone_count} zones, creating {zone_count} switch entities")
            for zone_index in range(1, zone_count + 1):
                entities.append(LtechSwitch(coordinator, device, zone_index, zone_count))
        else:
            entities.append(LtechSwitch(coordinator, device))

    _LOGGER.info(f"Adding {len(entities)} switch entities")
    async_add_entities(entities)


def _get_zone_count(device):
    """Parse zone count from paramext field."""
    paramext = device.get("paramext", "{}")
    if not paramext:
        return 1

    try:
        if isinstance(paramext, str):
            param_data = json.loads(paramext)
        else:
            param_data = paramext

        zone_number = param_data.get("zoneNumber", 1)
        if isinstance(zone_number, int) and zone_number > 0:
            return zone_number
    except (json.JSONDecodeError, TypeError) as e:
        _LOGGER.debug(f"Failed to parse paramext: {e}")

    return 1


class LtechSwitch(LtechEntity, SwitchEntity):
    def __init__(self, coordinator, device, zone_index=None, zone_count=None):
        super().__init__(coordinator, device)
        self._zone_index = zone_index
        self._zone_count = zone_count

        if zone_index is not None and zone_count is not None and zone_count > 1:
            self._attr_name = f"{self.device_name} Zone {zone_index}"
            self._attr_unique_id = f"{DOMAIN}_{self.device_id}_zone_{zone_index}"
        else:
            self._attr_name = self.device_name
            self._attr_unique_id = f"{DOMAIN}_{self.device_id}"

    @property
    def name(self):
        return self._attr_name

    @property
    def unique_id(self):
        return self._attr_unique_id

    @property
    def is_on(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
        
        device_state = self.device.get("deviceState", {})
        if isinstance(device_state, str):
            try:
                device_state = json.loads(device_state)
            except (json.JSONDecodeError, TypeError):
                device_state = {}
        
        if not device_state:
            maccode = self.device.get("maccode", "{}")
            if isinstance(maccode, str):
                try:
                    maccode_data = json.loads(maccode)
                    device_state.update(maccode_data)
                except (json.JSONDecodeError, TypeError):
                    pass
        
        if self._zone_index is not None and self._zone_count is not None and self._zone_count > 1:
            zone_key = f"zone{self._zone_index}"
            zone_state = device_state.get(zone_key, {})
            if isinstance(zone_state, dict):
                state_value = zone_state.get("CharSwitch")
                if state_value is not None:
                    try:
                        value_hex = state_value[-2:]
                        return int(value_hex, 16) == 1
                    except (ValueError, TypeError):
                        return False
            return False
        
        state_value = device_state.get("CharSwitch")
        if state_value is not None:
            try:
                value_hex = state_value[-2:]
                return int(value_hex, 16) == 1
            except (ValueError, TypeError):
                return False
        return False

    async def async_turn_on(self, **kwargs):
        try:
            if self._zone_index is not None and self._zone_count is not None and self._zone_count > 1:
                await self.hass.async_add_executor_job(
                    self.coordinator.api.control_switch_zone,
                    self.device_id,
                    self._zone_index,
                    True,
                )
            else:
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
            if self._zone_index is not None and self._zone_count is not None and self._zone_count > 1:
                await self.hass.async_add_executor_job(
                    self.coordinator.api.control_switch_zone,
                    self.device_id,
                    self._zone_index,
                    False,
                )
            else:
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
                    if self._zone_index is not None and self._zone_count is not None and self._zone_count > 1:
                        self._is_on = self._parse_zone_state(state_value, self._zone_index)
                    else:
                        parsed = self._parse_state_value(state_value)
                        self._is_on = parsed == 1 if parsed is not None else False

    def _parse_zone_state(self, hex_string, zone_index):
        """Parse zone state from CharSwitch hex string.

        The CharSwitch format is: 66BB + data + EB
        For multi-zone switches, each zone's state is represented by a bit.
        """
        if not isinstance(hex_string, str) or len(hex_string) < 8:
            return False

        try:
            hex_string = hex_string.upper()
            if hex_string.startswith("66BB") and hex_string.endswith("EB"):
                data = hex_string[4:-2]
                if len(data) >= 4:
                    state_value = int(data[2:4], 16)
                    return bool(state_value & (1 << (zone_index - 1)))
        except (ValueError, TypeError):
            pass

        return False
