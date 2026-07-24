import hashlib
import json
import time
import sys

import requests
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
import urllib3

urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

APP_ID_DEFAULT = 119011416470103
SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
SESSION_DEFAULT = "cqq577bze26adn1wbt5uz0nwk7zlfs03"
REST_URL = "openapi/rest"
FUN_URL_LOGIN = "ysnetwork.base.com.user.app.login"
SERVER_URL = "https://apic.ltsys.com.cn:2443/"

def _aes_encrypt(data, key):
    key_bytes = key.encode("utf-8")
    cipher = AES.new(key_bytes, AES.MODE_ECB)
    padded_data = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    return encrypted.hex().upper()

def _md5_sign(data_str):
    return hashlib.md5(data_str.encode("utf-8")).hexdigest().lower()

def _send_request(server_url, session, method, data=None):
    timestamp = str(int(time.time() * 1000))
    
    if data is None:
        data_str = ""
    else:
        data_str = json.dumps(data, separators=(',', ':'))
    
    encrypted_data = _aes_encrypt(data_str, SECRET_KEY_DEFAULT)
    
    sign_str = (
        f"{SECRET_KEY_DEFAULT}"
        f"{APP_ID_DEFAULT}"
        f"{encrypted_data}"
        f"json"
        f"{method}"
        f"{session}"
        f"{timestamp}"
        f"2.0"
        f"{SECRET_KEY_DEFAULT}"
    )
    sign = _md5_sign(sign_str)
    
    payload = {
        "appid": APP_ID_DEFAULT,
        "data": encrypted_data,
        "method": method,
        "platform_version": "Android_3.3.5",
        "session": session,
        "sign": sign,
        "system_model": "33_HA",
        "timestamp": timestamp,
        "format": "json",
        "v": "2.0",
    }
    
    url = f"{server_url}{REST_URL}"
    response = requests.post(url, json=payload, verify=False, timeout=30)
    result = response.json()
    
    print(f"Request payload: {json.dumps(payload, separators=(',', ':'))}")
    print(f"Response: {response.text}")
    
    return result

def test_login(account, password):
    print(f"\n{'='*80}")
    print(f"Testing login with account: {account}")
    print(f"{'='*80}")
    
    login_data_android = {
        "devicesn": "",
        "devicetype": "2",
        "loginname": account,
        "memberid": APP_ID_DEFAULT,
        "pwd": password,
    }
    
    print("\nTest 1: Android devicetype='2'")
    result = _send_request(SERVER_URL, SESSION_DEFAULT, FUN_URL_LOGIN, login_data_android)
    
    if result.get("ret") == 0:
        print("✅ Login SUCCESS!")
        return
    
    login_data_ios = {
        "devicesn": "",
        "devicetype": "3",
        "loginname": account,
        "memberid": APP_ID_DEFAULT,
        "pwd": password,
    }
    
    print("\nTest 2: iOS devicetype='3'")
    result = _send_request(SERVER_URL, SESSION_DEFAULT, FUN_URL_LOGIN, login_data_ios)
    
    if result.get("ret") == 0:
        print("✅ Login SUCCESS!")
        return

if __name__ == "__main__":
    if len(sys.argv) >= 3:
        account = sys.argv[1]
        password = sys.argv[2]
        test_login(account, password)
    else:
        print("Usage: python test_android_type.py <account> <password>")