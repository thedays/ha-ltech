import logging

from homeassistant.components.sensor import (
    SensorDeviceClass,
    SensorEntity,
    SensorStateClass,
)
from homeassistant.config_entries import ConfigEntry
from homeassistant.const import PERCENTAGE, TEMP_CELSIUS
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity_platform import AddEntitiesCallback

from .const import DOMAIN, SENSOR_PRODUCT_IDS
from .coordinator import LtechDataUpdateCoordinator
from .entity import LtechEntity

_LOGGER = logging.getLogger(__name__)


async def async_setup_entry(
    hass: HomeAssistant,
    config_entry: ConfigEntry,
    async_add_entities: AddEntitiesCallback,
) -> None:
    coordinator = hass.data[DOMAIN][config_entry.entry_id]
    sensors = coordinator.get_devices_by_type(SENSOR_PRODUCT_IDS)
    
    entities = []
    for device in sensors:
        product_id = device.get("productId", "")
        
        if "TEMP" in product_id:
            entities.append(LtechTemperatureSensor(coordinator, device))
        elif "HUMI" in product_id:
            entities.append(LtechHumiditySensor(coordinator, device))
        elif "PIR" in product_id:
            entities.append(LtechMotionSensor(coordinator, device))
        elif "DOOR" in product_id:
            entities.append(LtechDoorSensor(coordinator, device))
        elif "BATTERY" in product_id:
            entities.append(LtechBatterySensor(coordinator, device))
    
    entities.append(LtechMeshStatusSensor(coordinator))
    
    async_add_entities(entities)


class LtechSensor(LtechEntity, SensorEntity):
    def __init__(self, coordinator, device):
        super().__init__(coordinator, device)
        self._state = None

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

    @property
    def state(self):
        return self._state

    @property
    def state_class(self):
        return SensorStateClass.MEASUREMENT


class LtechTemperatureSensor(LtechSensor):
    @property
    def device_class(self):
        return SensorDeviceClass.TEMPERATURE

    @property
    def unit_of_measurement(self):
        return TEMP_CELSIUS

    async def async_update(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
            
            device_state = device.get("deviceState", {})
            if isinstance(device_state, dict):
                temp_value = device_state.get("CharTemp")
                if temp_value is not None:
                    parsed = self._parse_state_value(temp_value)
                    if parsed is not None:
                        self._state = float(parsed) / 10.0


class LtechHumiditySensor(LtechSensor):
    @property
    def device_class(self):
        return SensorDeviceClass.HUMIDITY

    @property
    def unit_of_measurement(self):
        return PERCENTAGE

    async def async_update(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
            
            device_state = device.get("deviceState", {})
            if isinstance(device_state, dict):
                humi_value = device_state.get("CharHumidity")
                if humi_value is not None:
                    parsed = self._parse_state_value(humi_value)
                    if parsed is not None:
                        self._state = int(parsed)


class LtechMotionSensor(LtechSensor):
    @property
    def device_class(self):
        return SensorDeviceClass.MOTION

    async def async_update(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
            
            device_state = device.get("deviceState", {})
            if isinstance(device_state, dict):
                motion_value = device_state.get("CharSwitch")
                if motion_value is not None:
                    parsed = self._parse_state_value(motion_value)
                    self._state = parsed == 1 if parsed is not None else False


class LtechDoorSensor(LtechSensor):
    @property
    def device_class(self):
        return SensorDeviceClass.DOOR

    async def async_update(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
            
            device_state = device.get("deviceState", {})
            if isinstance(device_state, dict):
                door_value = device_state.get("CharSwitch")
                if door_value is not None:
                    parsed = self._parse_state_value(door_value)
                    self._state = "open" if parsed == 1 else "closed"


class LtechBatterySensor(LtechSensor):
    @property
    def device_class(self):
        return SensorDeviceClass.BATTERY

    @property
    def unit_of_measurement(self):
        return PERCENTAGE

    @property
    def state_class(self):
        return SensorStateClass.MEASUREMENT

    async def async_update(self):
        device = self.coordinator.get_device(self.device_id)
        if device:
            self.device = device
            
            device_state = device.get("deviceState", {})
            if isinstance(device_state, dict):
                battery_value = device_state.get("CharBattery")
                if battery_value is not None:
                    parsed = self._parse_state_value(battery_value)
                    if parsed is not None:
                        self._state = int(parsed)


class LtechMeshStatusSensor(SensorEntity):
    def __init__(self, coordinator):
        self.coordinator = coordinator
        self._attr_name = "Ltech Mesh Status"
        self._attr_unique_id = "ltech_mesh_status"
        self._attr_device_class = SensorDeviceClass.CONNECTIVITY
        self._attr_state = "disconnected"

    @property
    def state(self):
        if self.coordinator.mesh_enabled and self.coordinator.mesh_manager:
            if self.coordinator.mesh_manager.connected:
                return "connected"
        return "disconnected"

    @property
    def icon(self):
        if self.state == "connected":
            return "mdi:bluetooth-connected"
        return "mdi:bluetooth-off"

    @property
    def extra_state_attributes(self):
        attrs = {}
        if self.coordinator.mesh_manager:
            attrs["mesh_uuid"] = self.coordinator.mesh_manager.mesh_uuid[:8] + "..." if self.coordinator.mesh_manager.mesh_uuid else None
            attrs["net_key"] = self.coordinator.mesh_manager.net_key[:8] + "..." if self.coordinator.mesh_manager.net_key else None
            attrs["app_key"] = self.coordinator.mesh_manager.app_key[:8] + "..." if self.coordinator.mesh_manager.app_key else None
        return attrs