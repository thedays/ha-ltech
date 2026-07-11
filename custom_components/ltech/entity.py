from homeassistant.helpers.device_registry import DeviceInfo
from homeassistant.helpers.update_coordinator import CoordinatorEntity

from .const import DOMAIN
from .coordinator import LtechDataUpdateCoordinator


class LtechEntity(CoordinatorEntity[LtechDataUpdateCoordinator]):
    def __init__(self, coordinator, device):
        super().__init__(coordinator)
        self.device = device
        self.device_id = device.get("deviceId") or device.get("deviceid", "")
        self.product_id = device.get("productId", "") or device.get("productid", "")
        self.device_name = device.get("deviceName", "") or device.get("devicename", "")
        self.room_name = device.get("roomName", "") or device.get("roomname", "")
        self.floor_name = device.get("floorName", "") or device.get("floorname", "")

    @property
    def device_info(self) -> DeviceInfo:
        return DeviceInfo(
            identifiers={(DOMAIN, self.device_id)},
            name=self.device_name,
            model=self.product_id,
            manufacturer="Ltech",
            sw_version=self.device.get("firmwareversion"),
            hw_version=self.device.get("hardwareId"),
            suggested_area=self.room_name,
        )

    @property
    def name(self):
        return self.device_name

    @property
    def unique_id(self):
        return f"{DOMAIN}_{self.device_id}"

    @property
    def available(self):
        return self.device.get("onlineFlag", 0) != 2

    @property
    def should_poll(self):
        return False

    @property
    def extra_state_attributes(self):
        return {
            "product_id": self.product_id,
            "room_name": self.room_name,
            "floor_name": self.floor_name,
            "online_flag": self.device.get("onlineFlag"),
        }