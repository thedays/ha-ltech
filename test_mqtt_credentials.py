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

def test_login(account, password):
    timestamp = str(int(time.time()))
    
    login_data = {
        "devicetype": "3",
        "memberid": str(APP_ID_DEFAULT),
        "loginname": account,
        "devicesn": "",
        "pwd": password,
    }
    data_str = json.dumps(login_data, separators=(',', ':'))
    encrypted_data = _aes_encrypt(data_str, SECRET_KEY_DEFAULT)
    
    sign_str = (
        f"{SECRET_KEY_DEFAULT}"
        f"{APP_ID_DEFAULT}"
        f"{encrypted_data}"
        f"json"
        f"{FUN_URL_LOGIN}"
        f"{SESSION_DEFAULT}"
        f"{timestamp}"
        f"2.0"
        f"{SECRET_KEY_DEFAULT}"
    )
    sign = _md5_sign(sign_str)
    
    payload = {
        "method": FUN_URL_LOGIN,
        "format": "json",
        "platform_version": "iOS_2.8.0",
        "data": encrypted_data,
        "system_model": "iOS 27.0_iPhone17,5",
        "v": "2.0",
        "session": SESSION_DEFAULT,
        "timestamp": timestamp,
        "appid": str(APP_ID_DEFAULT),
        "sign": sign,
    }
    
    url = f"{SERVER_URL}{REST_URL}"
    headers = {
        "Content-Type": "application/json",
        "User-Agent": "SmartHome/3 CFNetwork/3890.100.1 Darwin/27.0.0",
    }
    
    response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False, timeout=30)
    result = response.json()
    
    print(f"Login Response:")
    print(f"{json.dumps(result, indent=2, ensure_ascii=False)}")
    
    if result.get("ret") == 0 and "data" in result:
        data = result["data"]
        print(f"\n{'='*60}")
        print(f"MQTT相关字段:")
        print(f"{'='*60}")
        print(f"productkey: {data.get('productkey')}")
        print(f"deviceName: {data.get('deviceName')}")
        print(f"deviceSecret: {data.get('deviceSecret')}")
        print(f"productKey: {data.get('productKey')}")
        print(f"secretkey: {data.get('secretkey')}")
        print(f"devicesn: {data.get('devicesn')}")
        print(f"meshuuid: {data.get('meshuuid')}")
        print(f"applicationkey: {data.get('applicationkey')}")
        print(f"netkey: {data.get('netkey')}")
        
        print(f"\n{'='*60}")
        print(f"所有可用字段:")
        print(f"{'='*60}")
        for key in data.keys():
            print(f"  {key}: {data[key]}")

if __name__ == "__main__":
    if len(sys.argv) >= 3:
        test_login(sys.argv[1], sys.argv[2])
    else:
        print("Usage: python test_mqtt_credentials.py <account> <password>")