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
TAG_NAME = "v2.4.1"
RELEASE_TITLE = "v2.4.1 - MQTT State Sync Fix & Enhanced Logging"
RELEASE_NOTES = """**Bug Fixes:**
- Fix MQTT state synchronization: light turns on but state shows off
- Fix _parse_mqtt_payload to add is_on field for JSON payloads
- Fix light.py is_on property to infer state from Brightness/Temp fields
- Fix switch.py is_on property for consistency
- Fix _enrich_state_with_is_on to properly handle CharSwitch, CharBrightness, CharTemp
- Enhanced Mesh control logging for debugging
- Enhanced MQTT message parsing logging

**Files Modified:**
- coordinator.py: Added _enrich_state_with_is_on method, fixed _parse_mqtt_payload
- light.py: Fixed is_on property to infer state from multiple fields, enhanced logging
- switch.py: Fixed is_on property for consistency, enhanced logging
- mesh_manager.py: Enhanced Mesh send/receive/decryption logging
- manifest.json: Updated version to 2.4.1

**Version:** 2.4.1"""

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