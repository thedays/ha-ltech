import json
import logging

from homeassistant.components.switch import SwitchEntity
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity_platform import AddEntitiesCallback

from .api import LtechApiError, LtechAuthError
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
                zone_name = _get_zone_name(device, zone_index)
                entities.append(LtechSwitch(coordinator, device, zone_index, zone_count, zone_name))
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

def _get_zone_name(device, zone_index):
    """Get zone name from paramext field."""
    paramext = device.get("paramext", "{}")
    if not paramext:
        return None

    try:
        if isinstance(paramext, str):
            param_data = json.loads(paramext)
        else:
            param_data = paramext

        zone_key = f"zone{zone_index}"
        zone_data = param_data.get(zone_key, {})
        if isinstance(zone_data, dict):
            zone_name = zone_data.get("name", "")
            if zone_name:
                return zone_name
    except (json.JSONDecodeError, TypeError) as e:
        _LOGGER.debug(f"Failed to parse paramext for zone name: {e}")

    return None


class LtechSwitch(LtechEntity, SwitchEntity):
    def __init__(self, coordinator, device, zone_index=None, zone_count=None, zone_name=None):
        super().__init__(coordinator, device)
        self._zone_index = zone_index
        self._zone_count = zone_count
        self._zone_name = zone_name

        if zone_index is not None and zone_count is not None and zone_count > 1:
            if zone_name:
                self._attr_name = f"{self.device_name} {zone_name}"
            else:
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

    def _get_device_state(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
        
        realtime_state = self.coordinator.get_device_state(self.device_id)
        if realtime_state:
            _LOGGER.debug(f"[SWITCH_STATE] Using realtime state for device_id={self.device_id}: {realtime_state}")
            return realtime_state
        
        device_state = self.device.get("deviceState", {})
        if isinstance(device_state, str):
            try:
                device_state = json.loads(device_state)
            except (json.JSONDecodeError, TypeError):
                device_state = {}
        
        if isinstance(device_state, dict) and device_state:
            _LOGGER.debug(f"[SWITCH_STATE] Using deviceState for device_id={self.device_id}: {device_state}")
            return device_state
        
        reportinstruct = self.device.get("reportinstruct", "")
        if reportinstruct and isinstance(reportinstruct, str) and len(reportinstruct) >= 14:
            try:
                ri_hex = reportinstruct.upper()
                status_byte_str = ri_hex[12:14]
                status_byte = int(status_byte_str, 16)
                
                if self._zone_index is not None and self._zone_count is not None and self._zone_count > 1:
                    is_on = bool(status_byte & (1 << (self._zone_index - 1)))
                    _LOGGER.debug(f"[SWITCH_STATE] Parsed reportinstruct for zone {self._zone_index}: status_byte=0x{status_byte_str}, is_on={is_on}")
                    return {"is_on": is_on}
                else:
                    is_on = (status_byte & 0x01) == 0x01
                    _LOGGER.debug(f"[SWITCH_STATE] Parsed reportinstruct: status_byte=0x{status_byte_str}, is_on={is_on}")
                    return {"is_on": is_on}
            except (ValueError, TypeError) as e:
                _LOGGER.debug(f"[SWITCH_STATE] Failed to parse reportinstruct: {e}")
        
        maccode = self.device.get("maccode", "")
        if maccode:
            try:
                maccode_data = json.loads(maccode)
                if isinstance(maccode_data, dict):
                    char_switch = maccode_data.get("CharSwitch")
                    if char_switch:
                        return {"CharSwitch": char_switch}
            except (json.JSONDecodeError, TypeError):
                pass
        
        _LOGGER.debug(f"[SWITCH_STATE] No reliable state available for device_id={self.device_id}, returning empty state (default off)")
        return {}
    
    @property
    def is_on(self):
        device_state = self._get_device_state()
        
        if not device_state:
            _LOGGER.debug(f"[SWITCH_STATE] No state available for device_id={self.device_id}, returning False (default off)")
            return False
        
        if "is_on" in device_state:
            _LOGGER.debug(f"[SWITCH_STATE] Using is_on from reportinstruct: {device_state['is_on']}")
            return device_state["is_on"]
        
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
            platform_device_id = self.device.get("platformdeviceid") or self.device.get("platformDeviceId")
            
            mesh_success = False
            if self.coordinator.mesh_enabled and self.coordinator.mesh_manager:
                if self._zone_index is not None and self._zone_count is not None and self._zone_count > 1:
                    control_data = self._build_zone_control_data(self._zone_index, True)
                    mesh_success = await self.coordinator.control_device_via_mesh(
                        self.device_id, "control", control_data
                    )
                else:
                    mesh_success = await self.coordinator.control_device_via_mesh(self.device_id, "on", True)
            
            if not mesh_success:
                if self._zone_index is not None and self._zone_count is not None and self._zone_count > 1:
                    await self.hass.async_add_executor_job(
                        self.coordinator.api.control_switch_zone,
                        self.device_id,
                        self._zone_index,
                        True,
                        platform_device_id,
                    )
                else:
                    await self.hass.async_add_executor_job(
                        self.coordinator.api.control_switch,
                        self.device_id,
                        True,
                        platform_device_id,
                    )

            self._is_on = True
            self.async_write_ha_state()

        except LtechAuthError as e:
            _LOGGER.error("Authentication failed when turning on switch, please check credentials: %s", e)
        except LtechApiError as e:
            _LOGGER.error("Failed to turn on switch: %s", e)

    async def async_turn_off(self, **kwargs):
        try:
            platform_device_id = self.device.get("platformdeviceid") or self.device.get("platformDeviceId")
            
            mesh_success = False
            if self.coordinator.mesh_enabled and self.coordinator.mesh_manager:
                if self._zone_index is not None and self._zone_count is not None and self._zone_count > 1:
                    control_data = self._build_zone_control_data(self._zone_index, False)
                    mesh_success = await self.coordinator.control_device_via_mesh(
                        self.device_id, "control", control_data
                    )
                else:
                    mesh_success = await self.coordinator.control_device_via_mesh(self.device_id, "on", False)
            
            if not mesh_success:
                if self._zone_index is not None and self._zone_count is not None and self._zone_count > 1:
                    await self.hass.async_add_executor_job(
                        self.coordinator.api.control_switch_zone,
                        self.device_id,
                        self._zone_index,
                        False,
                        platform_device_id,
                    )
                else:
                    await self.hass.async_add_executor_job(
                        self.coordinator.api.control_switch,
                        self.device_id,
                        False,
                        platform_device_id,
                    )

            self._is_on = False
            self.async_write_ha_state()

        except LtechAuthError as e:
            _LOGGER.error("Authentication failed when turning off switch, please check credentials: %s", e)
        except LtechApiError as e:
            _LOGGER.error("Failed to turn off switch: %s", e)

    def _parse_state_value(self, hex_string):
        if not isinstance(hex_string, str) or len(hex_string) < 8:
            return None

        try:
            hex_string = hex_string.upper()
            if hex_string.startswith("66BB") and hex_string.endswith("EB"):
                data = hex_string[4:-2]
                if len(data) >= 4:
                    status_byte = int(data[2:4], 16)
                    return 1 if (status_byte & 1) == 1 else 0
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
        For multi-zone switches, each zone's state is represented by a bit in status_byte.
        """
        if not isinstance(hex_string, str) or len(hex_string) < 8:
            return False

        try:
            hex_string = hex_string.upper()
            if hex_string.startswith("66BB") and hex_string.endswith("EB"):
                data = hex_string[4:-2]
                if len(data) >= 4:
                    status_byte = int(data[2:4], 16)
                    return bool(status_byte & (1 << (zone_index - 1)))
        except (ValueError, TypeError):
            return False

        return False

    def _build_zone_control_data(self, zone_index, on):
        """Build control data for multi-zone switch."""
        status_value = 1 if on else 0
        return f"66BB27C000002A002200090ED8010000000018001400180040010004015A00AD0204030605040018031C00{status_value:02X}EB"
