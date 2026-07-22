import asyncio
import logging
import warnings

import urllib3

from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant

warnings.filterwarnings("ignore", category=urllib3.exceptions.InsecureRequestWarning)

from .api import LtechApiClient
from .const import CONF_ACCOUNT, CONF_PASSWORD, CONF_SERVER_URL, DOMAIN
from .coordinator import LtechDataUpdateCoordinator

_LOGGER = logging.getLogger(__name__)

PLATFORMS = ["light", "switch", "sensor"]


async def async_setup_entry(hass: HomeAssistant, entry: ConfigEntry) -> bool:
    _LOGGER.info(f"[SETUP] Starting Ltech integration setup")
    
    api = LtechApiClient(
        server_url=entry.data[CONF_SERVER_URL],
        email=entry.data[CONF_ACCOUNT],
        password=entry.data[CONF_PASSWORD],
    )
    
    _LOGGER.info(f"[SETUP] Logging in with account: {entry.data[CONF_ACCOUNT]}")
    await hass.async_add_executor_job(api.login)
    _LOGGER.info(f"[SETUP] Login successful, user_id={api.user_id}")
    
    coordinator = LtechDataUpdateCoordinator(hass, api)
    _LOGGER.info(f"[SETUP] Refreshing coordinator data...")
    await coordinator.async_config_entry_first_refresh()
    
    hass.data.setdefault(DOMAIN, {})
    hass.data[DOMAIN][entry.entry_id] = coordinator
    
    _LOGGER.info(f"[SETUP] Devices loaded: {len(coordinator.devices)}")
    _LOGGER.info(f"[SETUP] Places loaded: {len(coordinator.places) if isinstance(coordinator.places, list) else 'dict'}")
    
    places_list = []
    if isinstance(coordinator.places, dict) and "rows" in coordinator.places:
        places_list = coordinator.places["rows"]
    elif isinstance(coordinator.places, list):
        places_list = coordinator.places
    
    if places_list:
        first_place = places_list[0]
        place_id = first_place.get("placeId") or first_place.get("placeid")
        _LOGGER.info(f"[SETUP] Starting mesh for place_id={place_id}")
        hass.async_create_task(coordinator.start_mesh(place_id))
    
    _LOGGER.info(f"[SETUP] Forwarding setup to platforms: {PLATFORMS}")
    await hass.config_entries.async_forward_entry_setups(entry, PLATFORMS)
    
    _LOGGER.info(f"[SETUP] Ltech integration setup completed")
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