import json
import requests
import os
import subprocess


def get_git_credentials():
    try:
        result = subprocess.run(
            ["git", "credential", "fill"],
            input=b"host=github.com\nprotocol=https\n",
            capture_output=True,
            text=True
        )
        for line in result.stdout.split("\n"):
            if line.startswith("password="):
                return line.split("=", 1)[1].strip()
    except Exception:
        pass
    return None


def load_env():
    env_path = ".env"
    if os.path.exists(env_path):
        with open(env_path, "r") as f:
            for line in f:
                line = line.strip()
                if line and not line.startswith("#"):
                    key, value = line.split("=", 1)
                    os.environ[key.strip()] = value.strip()


load_env()
GITHUB_TOKEN = os.environ.get("GITHUB_TOKEN")

if not GITHUB_TOKEN:
    GITHUB_TOKEN = get_git_credentials()
    if GITHUB_TOKEN:
        print("Using git credentials from keychain")

REPO_OWNER = "thedays"
REPO_NAME = "ha-ltech"
TAG_NAME = "v2.4.5"
RELEASE_TITLE = "v2.4.5 - Setup Version Logging & Diagnostics"
RELEASE_NOTES = """**Key Additions:**
- Added version logging at setup start: `[SETUP] Starting Ltech integration setup v2.4.5`
- Added entry data config logging for debugging setup issues
- Added JSON serialization of config entry for easier troubleshooting

**v2.4.4 Features (included):**
- Mesh setup completion callback for proper mesh_enabled state logging
- Enhanced mesh_manager.py: full BLE device/service/characteristic discovery logging
- Enhanced coordinator.py: detailed Mesh setup flow with step-by-step logs
- Enhanced light.py: complete control flow logging including API result and MQTT payload
- Enhanced switch.py: consistent control flow logging with light.py

**v2.4.3 Fixes (included):**
- Fix critical `_LOGger` typo to `_LOGGER` (4 instances across 2 files)
- Fix NameError when Mesh setup encountered errors

**v2.4.2 Features (included):**
- Fix platformdeviceid retrieval: use get_platform_device_id() method
- Auto-generate platformdeviceid from iotdevicename + iotproductkey
- Enhanced Bluetooth scanning and connection diagnostics

**Diagnostics Logs:**
- [SETUP] log: shows version, config, Mesh task status
- [MESH] log: shows full Mesh setup flow, BLE scan results
- [DEVICE_IOT] log: shows iotdevicename, iotproductkey, platformdeviceid
- [LIGHT_CONTROL] log: shows platform_device_id, API result, MQTT payload
- Error traceback logging for all failures

**Files Modified:**
- __init__.py: Added version/config logging at setup start
- coordinator.py: Enhanced Mesh setup flow logging
- light.py: Enhanced control flow logging
- switch.py: Enhanced control flow logging
- mesh_manager.py: Enhanced BLE scan, connect, service discovery
- manifest.json: Updated version to 2.4.5

**Version:** 2.4.5"""

ASSET_PATH = "ltech-hass-integration.zip"
ASSET_NAME = "ltech-hass-integration.zip"
ASSET_CONTENT_TYPE = "application/zip"


def create_release():
    if not GITHUB_TOKEN:
        print("Error: No GitHub token found. Please set GITHUB_TOKEN in .env or use git credentials.")
        return False

    headers = {
        "Authorization": f"token {GITHUB_TOKEN}",
        "Accept": "application/vnd.github.v3+json",
    }

    print(f"Creating release {TAG_NAME}...")

    release_data = {
        "tag_name": TAG_NAME,
        "target_commitish": "main",
        "name": RELEASE_TITLE,
        "body": RELEASE_NOTES,
        "draft": False,
        "prerelease": False,
    }

    url = f"https://api.github.com/repos/{REPO_OWNER}/{REPO_NAME}/releases"
    response = requests.post(url, headers=headers, json=release_data)

    if response.status_code != 201:
        print(f"Failed to create release: {response.status_code}")
        print(f"Response: {response.text}")
        return False

    release_info = response.json()
    print(f"Release created: {release_info['html_url']}")

    upload_url = release_info["upload_url"].replace("{?name,label}", "")

    print(f"Uploading asset {ASSET_PATH}...")
    with open(ASSET_PATH, "rb") as f:
        asset_data = f.read()

    asset_headers = headers.copy()
    asset_headers["Content-Type"] = ASSET_CONTENT_TYPE

    asset_url = f"{upload_url}?name={ASSET_NAME}"
    response = requests.post(asset_url, headers=asset_headers, data=asset_data)

    if response.status_code != 201:
        print(f"Failed to upload asset: {response.status_code}")
        print(f"Response: {response.text}")
        return False

    asset_info = response.json()
    print(f"Asset uploaded: {asset_info['browser_download_url']}")

    print("\n✅ Release created successfully!")
    return True


if __name__ == "__main__":
    create_release()