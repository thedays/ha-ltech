import base64
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
    iv_bytes = key_bytes[:AES.block_size]
    cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
    padded_data = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    b64 = base64.b64encode(encrypted).decode("utf-8")
    return b64.replace('+', '-').replace('/', '_').rstrip('=')

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
        "method": method,
        "format": "json",
        "platform_version": "iOS_2.8.0",
        "data": encrypted_data,
        "system_model": "iOS 27.0_iPhone17,5",
        "v": "2.0",
        "session": session,
        "timestamp": timestamp,
        "appid": str(APP_ID_DEFAULT),
        "sign": sign,
    }
    
    url = f"{server_url}{REST_URL}"
    
    headers = {
        "Content-Type": "application/json",
        "User-Agent": "SmartHome/3 CFNetwork/3890.100.1 Darwin/27.0.0",
    }
    
    print(f"Request payload: {json.dumps(payload, separators=(',', ':'))}")
    response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False, timeout=30)
    print(f"Response: {response.text}")
    
    return response.json()

def test_login(account, password):
    print(f"\n{'='*80}")
    print(f"Testing login with account: {account}")
    print(f"{'='*80}")
    
    login_data = {
        "devicetype": "3",
        "memberid": str(APP_ID_DEFAULT),
        "loginname": account,
        "devicesn": "",
        "pwd": password,
    }
    
    result = _send_request(SERVER_URL, SESSION_DEFAULT, FUN_URL_LOGIN, login_data)
    
    if result.get("ret") == 0:
        print("✅ Login SUCCESS!")
        print(f"Session: {result.get('session')}")
        print(f"User ID: {result.get('userid')}")
        return result.get("session"), result.get("userid")
    else:
        print(f"❌ Login FAILED: ret={result.get('ret')}, msg={result.get('message')}")
        return None, None

if __name__ == "__main__":
    if len(sys.argv) >= 3:
        account = sys.argv[1]
        password = sys.argv[2]
        test_login(account, password)
    else:
        print("Usage: python test_cbc_final.py <account> <password>")