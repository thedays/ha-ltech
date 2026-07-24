import hashlib
import json
import time
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

def aes_encrypt_cbc(data, key):
    key_bytes = key.encode("utf-8")
    iv_bytes = key_bytes[:AES.block_size]
    cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
    padded_data = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    return encrypted.hex().upper()

def md5_sign(data_str):
    return hashlib.md5(data_str.encode("utf-8")).hexdigest().upper()

def build_request_java_style(method, data, app_id=APP_ID_DEFAULT, secret_key=SECRET_KEY_DEFAULT, session=SESSION_DEFAULT):
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
    
    platform_version = "Android_3.3.5"
    system_model = "33_HA"
    
    request_obj = {
        "appid": app_id,
        "data": encrypted_data,
        "method": method,
        "platform_version": platform_version,
        "session": session,
        "sign": sign,
        "system_model": system_model,
        "timestamp": timestamp,
        "format": "json",
        "v": "2.0",
    }
    
    return request_obj, data_str, encrypted_data, sign_str, sign

def send_request(payload):
    url = f"{SERVER_URL}{REST_URL}"
    print(f"\n=== Sending Request ===")
    print(f"URL: {url}")
    print(f"Payload (raw):")
    print(json.dumps(payload, indent=2, ensure_ascii=False))
    
    try:
        response = requests.post(url, json=payload, verify=False, timeout=30)
        print(f"\n=== Response ===")
        print(f"Status: {response.status_code}")
        print(f"Headers:")
        for key, value in response.headers.items():
            print(f"  {key}: {value}")
        print(f"\nContent:")
        print(response.text)
        
        try:
            result = response.json()
            print(f"\n=== Parsed Result ===")
            print(f"ret: {result.get('ret')}")
            print(f"msg: {result.get('msg')}")
            print(f"data: {result.get('data')}")
            return result
        except:
            print(f"\nResponse is not JSON")
            return {"raw": response.text}
    except Exception as e:
        print(f"\nError: {e}")
        return None

def test_login(email, password):
    login_data = {
        "memberid": APP_ID_DEFAULT,
        "loginname": email,
        "pwd": password,
        "devicetype": "3",
        "devicesn": "",
    }
    
    print("=" * 70)
    print("TEST: Login")
    print("=" * 70)
    print(f"\n1. Login data (before encryption):")
    login_data_json = json.dumps(login_data, separators=(',', ':'))
    print(login_data_json)
    print(f"   Length: {len(login_data_json)}")
    
    print(f"\n2. Building request...")
    payload, data_str, encrypted_data, sign_str, sign = build_request_java_style(FUN_URL_LOGIN, login_data)
    
    print(f"\n3. Encrypted data:")
    print(f"   {encrypted_data}")
    print(f"   Length: {len(encrypted_data)}")
    
    print(f"\n4. Sign string:")
    print(f"   {sign_str}")
    print(f"   Length: {len(sign_str)}")
    
    print(f"\n5. Sign:")
    print(f"   {sign}")
    print(f"   Length: {len(sign)}")
    
    print(f"\n6. Sending request...")
    result = send_request(payload)
    
    return result

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) >= 3:
        email = sys.argv[1]
        password = sys.argv[2]
        test_login(email, password)
    else:
        print("Usage: python debug_api2.py <email> <password>")