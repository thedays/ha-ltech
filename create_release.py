import requests
import json
import os
import sys

GITHUB_TOKEN = os.environ.get('GITHUB_TOKEN')
REPO_OWNER = 'thedays'
REPO_NAME = 'ha-ltech'
TAG_NAME = 'v2.3.59'
RELEASE_TITLE = 'v2.3.59'
RELEASE_NOTES = '''Fix device status not updating

- Added _update_device_states_from_sync() method to parse and store device states from sync_device_status API response
- Updated _async_update_data() to call sync_device_status and update device_states dictionary
- Device states are now properly updated during each refresh cycle
- Light and Switch entities now get real-time states from device_states dictionary
- Updated version to 2.3.59'''
ZIP_FILE = 'ltech-hass-integration.zip'


def get_github_token():
    if GITHUB_TOKEN:
        return GITHUB_TOKEN
    
    if len(sys.argv) > 1:
        return sys.argv[1]
    
    print("⚠️  GITHUB_TOKEN environment variable not set")
    print("Trying to get token from git config...")
    
    try:
        import subprocess
        result = subprocess.run(
            ['git', 'config', '--get', 'github.token'],
            capture_output=True,
            text=True
        )
        token = result.stdout.strip()
        if token:
            print("✅ Found token from git config")
            return token
    except Exception as e:
        pass
    
    print("❌ No GitHub token found. Usage:")
    print("   python3 create_release.py <your_github_token>")
    print("   or set GITHUB_TOKEN environment variable")
    sys.exit(1)


def create_release(token):
    print(f"📦 Creating release for tag: {TAG_NAME}")
    
    url = f'https://api.github.com/repos/{REPO_OWNER}/{REPO_NAME}/releases'
    headers = {
        'Authorization': f'token {token}',
        'Accept': 'application/vnd.github.v3+json'
    }
    
    data = {
        'tag_name': TAG_NAME,
        'target_commitish': 'main',
        'name': RELEASE_TITLE,
        'body': RELEASE_NOTES,
        'draft': False,
        'prerelease': False
    }
    
    response = requests.post(url, headers=headers, data=json.dumps(data))
    
    if response.status_code == 201:
        print(f"✅ Release created successfully")
        return response.json()
    elif response.status_code == 422:
        print(f"⚠️  Release already exists, fetching existing release")
        return get_existing_release(token)
    else:
        print(f"❌ Failed to create release: {response.status_code}")
        print(f"   Error: {response.text}")
        return None


def get_existing_release(token):
    url = f'https://api.github.com/repos/{REPO_OWNER}/{REPO_NAME}/releases/tags/{TAG_NAME}'
    headers = {
        'Authorization': f'token {token}',
        'Accept': 'application/vnd.github.v3+json'
    }
    
    response = requests.get(url, headers=headers)
    
    if response.status_code == 200:
        print(f"✅ Found existing release")
        return response.json()
    else:
        print(f"❌ Failed to get existing release: {response.status_code}")
        return None


def upload_asset(token, release_id):
    print(f"📤 Uploading asset: {ZIP_FILE}")
    
    url = f'https://uploads.github.com/repos/{REPO_OWNER}/{REPO_NAME}/releases/{release_id}/assets?name={os.path.basename(ZIP_FILE)}'
    headers = {
        'Authorization': f'token {token}',
        'Content-Type': 'application/zip',
        'Accept': 'application/vnd.github.v3+json'
    }
    
    with open(ZIP_FILE, 'rb') as f:
        response = requests.post(url, headers=headers, data=f)
    
    if response.status_code == 201:
        print(f"✅ Asset uploaded successfully")
        return response.json()
    elif response.status_code == 422:
        print(f"⚠️  Asset already exists, skipping upload")
        return None
    else:
        print(f"❌ Failed to upload asset: {response.status_code}")
        print(f"   Error: {response.text}")
        return None


def main():
    token = get_github_token()
    
    release = create_release(token)
    if not release:
        print("❌ Failed to create/find release")
        sys.exit(1)
    
    release_id = release['id']
    release_url = release['html_url']
    print(f"🔗 Release URL: {release_url}")
    
    asset = upload_asset(token, release_id)
    
    if asset:
        asset_url = asset['browser_download_url']
        print(f"📥 Asset URL: {asset_url}")
    
    print("\n🎉 Release completed successfully!")


if __name__ == '__main__':
    main()