import hashlib
import json
import time
import requests
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad

APP_ID_DEFAULT = 119011416470103
SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
SESSION_DEFAULT = "cqq577bze26adn1wbt5uz0nwk7zlfs03"
REST_URL = "openapi/rest"
FUN_URL_LOGIN = "ysnetwork.base.com.user.app.login"
SERVER_URL = "https://apic.ltsys.com.cn:2443/"

def aes_encrypt_cbc(data, key):
    key_bytes = key.encode("utf-8")
    iv_bytes = key_bytes[:AES.block_size]
    cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
    padded_data = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    return encrypted.hex().upper()

def aes_encrypt_ecb(data, key):
    cipher = AES.new(key.encode("utf-8"), AES.MODE_ECB)
    padded_data = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    return encrypted.hex().upper()

def md5_sign(data_str):
    return hashlib.md5(data_str.encode("utf-8")).hexdigest().upper()

def build_request(method, data, app_id=APP_ID_DEFAULT, secret_key=SECRET_KEY_DEFAULT, session=SESSION_DEFAULT):
    timestamp = str(int(time.time() * 1000))
    
    if data is None:
        data_str = ""
    else:
        data_str = json.dumps(data, separators=(',', ':'))
    
    encrypted_data = aes_encrypt_cbc(data_str, secret_key)
    
    sign_str = (
        f"{secret_key}"
        f"{app_id}"
        f"{encrypted_data}"
        f"json"
        f"{method}"
        f"{session}"
        f"{timestamp}"
        f"2.0"
        f"{secret_key}"
    )
    sign = md5_sign(sign_str)
    
    return {
        "appid": app_id,
        "method": method,
        "data": encrypted_data,
        "format": "json",
        "session": session,
        "timestamp": timestamp,
        "v": "2.0",
        "sign": sign,
        "platform_version": "Android_3.3.5",
        "system_model": "33_Model",
    }

def send_request(payload):
    url = f"{SERVER_URL}{REST_URL}"
    print(f"URL: {url}")
    print(f"Payload: {json.dumps(payload, indent=2, ensure_ascii=False)}")
    
    try:
        response = requests.post(url, json=payload, verify=False, timeout=30)
        print(f"Response status: {response.status_code}")
        print(f"Response headers: {dict(response.headers)}")
        print(f"Response content: {response.text}")
        
        result = response.json()
        return result
    except Exception as e:
        print(f"Error: {e}")
        return None

def test_login(email, password):
    login_data = {
        "memberid": APP_ID_DEFAULT,
        "loginname": email,
        "pwd": password,
        "devicetype": "3",
        "devicesn": "",
    }
    
    print("=" * 60)
    print("Test Login")
    print("=" * 60)
    print(f"Login data (before encryption): {json.dumps(login_data, separators=(',', ':'))}")
    
    payload = build_request(FUN_URL_LOGIN, login_data)
    result = send_request(payload)
    
    if result:
        print(f"Result ret: {result.get('ret')}")
        print(f"Result msg: {result.get('msg')}")
        print(f"Result data: {result.get('data')}")
    
    return result

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) < 3:
        print("Usage: python test_api.py <email> <password>")
        sys.exit(1)
    
    email = sys.argv[1]
    password = sys.argv[2]
    
    result = test_login(email, password)