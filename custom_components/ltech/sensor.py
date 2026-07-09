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
    
    async_add_entities(entities)


class LtechSensor(LtechEntity, SensorEntity):
    def __init__(self, coordinator, device):
        super().__init__(coordinator, device)
        self._state = None

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
                    try:
                        self._state = float(temp_value) / 10.0
                    except (ValueError, TypeError):
                        pass


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
                    try:
                        self._state = int(humi_value)
                    except (ValueError, TypeError):
                        pass


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
                    self._state = motion_value == "1"


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
                    self._state = "open" if door_value == "1" else "closed"


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
                    try:
                        self._state = int(battery_value)
                    except (ValueError, TypeError):
                        pass