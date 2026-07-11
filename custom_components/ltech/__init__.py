import asyncio
import logging

from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant

from .api import LtechApiClient
from .const import CONF_ACCOUNT, CONF_PASSWORD, CONF_SERVER_URL, DOMAIN
from .coordinator import LtechDataUpdateCoordinator

_LOGGER = logging.getLogger(__name__)

PLATFORMS = ["light", "switch", "sensor"]


async def async_setup_entry(hass: HomeAssistant, entry: ConfigEntry) -> bool:
    api = LtechApiClient(
        server_url=entry.data[CONF_SERVER_URL],
        email=entry.data[CONF_ACCOUNT],
        password=entry.data[CONF_PASSWORD],
    )
    
    await hass.async_add_executor_job(api.login)
    
    coordinator = LtechDataUpdateCoordinator(hass, api)
    await coordinator.async_config_entry_first_refresh()
    
    hass.data.setdefault(DOMAIN, {})
    hass.data[DOMAIN][entry.entry_id] = coordinator
    
    if coordinator.places:
        first_place = coordinator.places[0]
        place_id = first_place.get("placeId") or first_place.get("placeid")
        await coordinator.start_mesh(place_id)
    
    await hass.config_entries.async_forward_entry_setups(entry, PLATFORMS)
    
    return True


async def async_unload_entry(hass: HomeAssistant, entry: ConfigEntry) -> bool:
    coordinator = hass.data[DOMAIN].get(entry.entry_id)
    if coordinator:
        await coordinator.stop_mesh()
        coordinator.stop_mqtt()
    
    unload_ok = all(
        await asyncio.gather(
            *[
                hass.config_entries.async_forward_entry_unload(entry, platform)
                for platform in PLATFORMS
            ]
        )
    )
    
    if unload_ok:
        hass.data[DOMAIN].pop(entry.entry_id)
    
    return unload_ok