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

def test_login(email, password):
    print("=" * 70)
    print("TEST: Full Login Flow")
    print("=" * 70)
    
    login_data = {
        "devicesn": "",
        "devicetype": "3",
        "loginname": email,
        "memberid": APP_ID_DEFAULT,
        "pwd": password,
    }
    
    login_json = json.dumps(login_data, separators=(',', ':'))
    print(f"\n1. LoginRequest JSON (before encryption):")
    print(f"   {login_json}")
    print(f"   Length: {len(login_json)}")
    
    encrypted_data = aes_encrypt_cbc(login_json, SECRET_KEY_DEFAULT)
    print(f"\n2. Encrypted data:")
    print(f"   {encrypted_data}")
    print(f"   Length: {len(encrypted_data)}")
    
    timestamp = str(int(time.time() * 1000))
    print(f"\n3. Timestamp: {timestamp}")
    
    format_val = "json"
    v_val = "2.0"
    
    sign_str = (
        f"{SECRET_KEY_DEFAULT}"
        f"{APP_ID_DEFAULT}"
        f"{encrypted_data}"
        f"{format_val}"
        f"{FUN_URL_LOGIN}"
        f"{SESSION_DEFAULT}"
        f"{timestamp}"
        f"{v_val}"
        f"{SECRET_KEY_DEFAULT}"
    )
    print(f"\n4. Sign string (raw):")
    print(f"   {sign_str}")
    print(f"   Length: {len(sign_str)}")
    
    sign = md5_sign(sign_str)
    print(f"\n5. Sign: {sign}")
    
    platform_version = "Android_3.3.5"
    system_model = "33_HA"
    
    request_object = {
        "appid": APP_ID_DEFAULT,
        "data": encrypted_data,
        "format": format_val,
        "method": FUN_URL_LOGIN,
        "platform_version": platform_version,
        "session": SESSION_DEFAULT,
        "sign": sign,
        "system_model": system_model,
        "timestamp": timestamp,
        "v": v_val,
    }
    
    request_json = json.dumps(request_object, separators=(',', ':'))
    print(f"\n6. Final request JSON:")
    print(f"   {request_json}")
    
    print(f"\n7. Sending request to {SERVER_URL}{REST_URL}...")
    response = requests.post(f"{SERVER_URL}{REST_URL}", json=request_object, verify=False, timeout=30)
    
    print(f"\n8. Response:")
    print(f"   Status: {response.status_code}")
    print(f"   Content: {response.text}")
    
    try:
        result = response.json()
        print(f"\n9. Parsed result:")
        print(f"   ret: {result.get('ret')}")
        print(f"   msg: {result.get('msg')}")
        print(f"   data: {result.get('data')}")
    except:
        print("\n9. Response is not JSON")

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) >= 3:
        email = sys.argv[1]
        password = sys.argv[2]
        test_login(email, password)
    else:
        print("Usage: python debug_full.py <email> <password>")