import asyncio
import logging
import warnings

import urllib3

from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers import device_registry as dr, entity_registry as er

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
    if isinstance(coordinator.places, dict):
        places_data = coordinator.places.get("data", coordinator.places)
        if "rows" in places_data:
            places_list = places_data["rows"]
        elif isinstance(places_data, list):
            places_list = places_data
    elif isinstance(coordinator.places, list):
        places_list = coordinator.places
    
    _LOGGER.info(f"[SETUP] Places list count: {len(places_list)}")
    
    if places_list:
        first_place = places_list[0]
        place_id = first_place.get("placeId") or first_place.get("placeid")
        _LOGGER.info(f"[SETUP] Starting mesh for place_id={place_id}")
        _LOGGER.info("[SETUP] Mesh start_mesh called (async, may take a few seconds)")
        mesh_task = hass.async_create_task(coordinator.start_mesh(place_id))
        
        def _mesh_done_callback(task):
            try:
                result = task.result()
                _LOGGER.info(f"[SETUP] Mesh setup completed: mesh_enabled={result}")
            except Exception as e:
                _LOGGER.error(f"[SETUP] Mesh setup failed: {e}")
                import traceback
                _LOGGER.error(f"[SETUP] Mesh traceback: {traceback.format_exc()}")
        
        mesh_task.add_done_callback(_mesh_done_callback)
        _LOGGER.info("[SETUP] Mesh task created, waiting for completion...")
    else:
        _LOGGER.warning("[SETUP] No places found, skipping Mesh setup")
        _LOGGER.warning("[SETUP] Please ensure your account has at least one place/area configured")
    
    _LOGGER.info(f"[SETUP] Starting MQTT for realtime updates")
    mqtt_started = await hass.async_add_executor_job(coordinator.start_mqtt)
    _LOGGER.info(f"[SETUP] MQTT started: {mqtt_started}")
    _LOGGER.info(f"[SETUP] Mesh state at startup: mesh_enabled={coordinator.mesh_enabled} (may change after async setup completes)")
    
    _LOGGER.info(f"[SETUP] Forwarding setup to platforms: {PLATFORMS}")
    await hass.config_entries.async_forward_entry_setups(entry, PLATFORMS)
    
    await _async_cleanup_old_entities(hass, entry, coordinator)
    
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


async def async_remove_config_entry_device(hass: HomeAssistant, config_entry: ConfigEntry, device_entry: dr.DeviceEntry) -> bool:
    return True


async def _async_cleanup_old_entities(hass: HomeAssistant, entry: ConfigEntry, coordinator):
    ent_reg = er.async_get(hass)
    
    current_device_ids = set(coordinator.devices.keys())
    
    old_entities = []
    for entity_entry in er.async_entries_for_config_entry(ent_reg, entry.entry_id):
        entity_device_id = entity_entry.unique_id.replace(f"{DOMAIN}_", "").replace("_zone_1", "").replace("_zone_2", "").replace("_zone_3", "").replace("_zone_4", "")
        if entity_device_id not in current_device_ids:
            old_entities.append(entity_entry)
    
    if old_entities:
        _LOGGER.info(f"[CLEANUP] Found {len(old_entities)} old entities to remove")
        for entity_entry in old_entities:
            _LOGGER.info(f"[CLEANUP] Removing old entity: {entity_entry.entity_id}")
            ent_reg.async_remove(entity_entry.entity_id)