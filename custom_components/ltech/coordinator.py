import logging
from datetime import timedelta

from homeassistant.core import HomeAssistant
from homeassistant.helpers.update_coordinator import DataUpdateCoordinator, UpdateFailed

from .api import LtechApiClient, LtechApiError, LtechAuthError
from .const import DOMAIN

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

    async def _async_update_data(self):
        try:
            if not self.places:
                self.places = await self.hass.async_add_executor_job(self.api.get_place_list)
            
            if self.places:
                first_place = self.places[0]
                place_id = first_place.get("placeId")
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