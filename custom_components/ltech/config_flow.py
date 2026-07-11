import logging
import warnings

import urllib3
import voluptuous as vol

from homeassistant import config_entries
from homeassistant.core import HomeAssistant
from homeassistant.data_entry_flow import FlowResult

warnings.filterwarnings("ignore", category=urllib3.exceptions.InsecureRequestWarning)

from .api import LtechApiClient, LtechApiError, LtechAuthError
from .const import (
    CONF_ACCOUNT,
    CONF_PASSWORD,
    CONF_SERVER_URL,
    DEFAULT_SERVER_URL,
    DOMAIN,
)

_LOGGER = logging.getLogger(__name__)

STEP_USER_DATA_SCHEMA = vol.Schema(
    {
        vol.Required(CONF_ACCOUNT): str,
        vol.Required(CONF_PASSWORD): str,
        vol.Optional(CONF_SERVER_URL, default=DEFAULT_SERVER_URL): str,
    }
)


async def validate_input(hass: HomeAssistant, data: dict) -> dict:
    api = LtechApiClient(
        server_url=data[CONF_SERVER_URL],
        email=data[CONF_ACCOUNT],
        password=data[CONF_PASSWORD],
    )
    
    try:
        result = await hass.async_add_executor_job(api.login)
        
        if result and isinstance(result, dict):
            return {
                "title": f"Ltech ({data[CONF_ACCOUNT]})",
                "session": result.get("session"),
                "user_id": result.get("userid"),
            }
        
        raise LtechAuthError("Login failed")
    
    except (LtechApiError, LtechAuthError) as e:
        _LOGGER.error("Authentication failed: %s", e)
        raise


class LtechConfigFlow(config_entries.ConfigFlow, domain=DOMAIN):
    VERSION = 1
    CONNECTION_CLASS = config_entries.CONN_CLASS_CLOUD_POLL

    async def async_step_user(
        self, user_input: dict | None = None
    ) -> FlowResult:
        errors: dict[str, str] = {}
        
        if user_input is not None:
            try:
                info = await validate_input(self.hass, user_input)
                
                await self.async_set_unique_id(user_input[CONF_ACCOUNT])
                for entry in self._async_current_entries():
                    if entry.unique_id == user_input[CONF_ACCOUNT]:
                        return self.async_abort(reason="already_configured")
                
                return self.async_create_entry(
                    title=info["title"],
                    data=user_input,
                )
            
            except LtechAuthError:
                errors["base"] = "invalid_auth"
            except LtechApiError:
                errors["base"] = "cannot_connect"
            except Exception as e:
                _LOGGER.exception("Unexpected exception: %s", e)
                errors["base"] = "unknown"
        
        return self.async_show_form(
            step_id="user",
            data_schema=STEP_USER_DATA_SCHEMA,
            errors=errors,
        )
    
    async def async_step_import(self, import_config: dict) -> FlowResult:
        return await self.async_step_user(import_config)