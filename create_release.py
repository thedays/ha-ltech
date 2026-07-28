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
TAG_NAME = "v2.4.0"
RELEASE_TITLE = "v2.4.0 - Bluetooth Mesh Control & Communication Fix"
RELEASE_NOTES = """**Major Features:**
- Added Bluetooth Mesh (BLE) direct control support via Bleak library
- Implemented Mesh network encryption/decryption (AES-CCM, K1/K2/K3/K4 key derivation)
- Added Mesh gateway auto-connect and device discovery

**Bug Fixes:**
- Fix Mesh upper transport decryption: removed incorrect AAD usage (use No AAD per Mesh spec)
- Fix encrypt_upper_transport and decrypt_upper_transport to use assoc_len=0
- Fix Ltech control data parsing: corrected cmd_subtype indexing (params[5] instead of params[4])
- Fix light.py and switch.py state value parsing for 66BB...EB format
- Fix multi-zone switch state parsing using cmd_subtype as zone index
- Fix MQTT message payload parsing for hex control data (cmd_subtype and value fields)
- Fix mesh_manager.py fallback decryption logic (AppKey/DeviceKey try order)
- Fix API response handling - return full result dict instead of data only
- Fix login/bind_user/get_place_info to handle result.data correctly
- Fix JSON control character error in API response parsing
- Fix SSL connection errors with forced TLSv1.2 and cipher configuration
- Fix light control: pass action parameter to request_device_control

**Files Modified:**
- mesh_crypto.py: Complete rewrite with proper Mesh crypto (k1/k3/k4, encrypt/decrypt, vendor model)
- mesh_manager.py: Added BLE connection, SAR handling, vendor model message parsing
- api.py: Fixed response handling, SSL config, login/bind_user methods
- coordinator.py: Fixed MQTT hex payload parsing, device state management
- light.py: Fixed _parse_state_value for 66BB...EB format, Mesh control path
- switch.py: Fixed _parse_state_value and _parse_zone_state for multi-zone switches
- manifest.json: Updated version to 2.4.0

**Requirements:**
- bleak>=0.21.0 (new)
- pycryptodome>=3.18.0
- requests>=2.31.0
- aliyun-iot-linkkit>=1.2.13

**Version:** 2.4.0"""

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