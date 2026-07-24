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

def aes_encrypt_ecb(data, key):
    key_bytes = key.encode("utf-8")
    cipher = AES.new(key_bytes, AES.MODE_ECB)
    padded_data = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    return encrypted.hex().upper()

def md5_sign(data_str):
    return hashlib.md5(data_str.encode("utf-8")).hexdigest().upper()

def test_all_encryptions():
    print("=" * 70)
    print("TEST: All Encryption Methods")
    print("=" * 70)
    
    login_data = {
        "memberid": APP_ID_DEFAULT,
        "loginname": "test@test.com",
        "pwd": "test123",
        "devicetype": "3",
        "devicesn": "",
    }
    
    data_str = json.dumps(login_data, separators=(',', ':'))
    print(f"\nOriginal data: {data_str}")
    print(f"Data length: {len(data_str)}")
    
    print("\n1. ECB Mode:")
    ecb_encrypted = aes_encrypt_ecb(data_str, SECRET_KEY_DEFAULT)
    print(f"   Encrypted: {ecb_encrypted}")
    print(f"   Length: {len(ecb_encrypted)}")
    
    print("\n2. CBC Mode (key as IV):")
    cbc_encrypted = aes_encrypt_cbc(data_str, SECRET_KEY_DEFAULT)
    print(f"   Encrypted: {cbc_encrypted}")
    print(f"   Length: {len(cbc_encrypted)}")
    
    print("\n3. Sign comparison:")
    timestamp = "1234567890123"
    
    ecb_sign_str = (
        f"{SECRET_KEY_DEFAULT}"
        f"{APP_ID_DEFAULT}"
        f"{ecb_encrypted}"
        f"json"
        f"{FUN_URL_LOGIN}"
        f"{SESSION_DEFAULT}"
        f"{timestamp}"
        f"2.0"
        f"{SECRET_KEY_DEFAULT}"
    )
    ecb_sign = md5_sign(ecb_sign_str)
    
    cbc_sign_str = (
        f"{SECRET_KEY_DEFAULT}"
        f"{APP_ID_DEFAULT}"
        f"{cbc_encrypted}"
        f"json"
        f"{FUN_URL_LOGIN}"
        f"{SESSION_DEFAULT}"
        f"{timestamp}"
        f"2.0"
        f"{SECRET_KEY_DEFAULT}"
    )
    cbc_sign = md5_sign(cbc_sign_str)
    
    print(f"ECB sign: {ecb_sign}")
    print(f"CBC sign: {cbc_sign}")
    print(f"Signs equal: {ecb_sign == cbc_sign}")

def test_login_request(email, password):
    print("\n" + "=" * 70)
    print("TEST: Login Request")
    print("=" * 70)
    
    login_data = {
        "memberid": APP_ID_DEFAULT,
        "loginname": email,
        "pwd": password,
        "devicetype": "3",
        "devicesn": "",
    }
    
    data_str = json.dumps(login_data, separators=(',', ':'))
    print(f"\n1. Login data (JSON): {data_str}")
    
    timestamp = str(int(time.time() * 1000))
    print(f"2. Timestamp: {timestamp}")
    
    encrypted_data = aes_encrypt_cbc(data_str, SECRET_KEY_DEFAULT)
    print(f"3. Encrypted data: {encrypted_data}")
    
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
    print(f"4. Sign string: {sign_str}")
    print(f"   Sign string length: {len(sign_str)}")
    
    sign = md5_sign(sign_str)
    print(f"5. Sign: {sign}")
    
    payload = {
        "appid": APP_ID_DEFAULT,
        "data": encrypted_data,
        "format": "json",
        "method": FUN_URL_LOGIN,
        "platform_version": "Android_3.3.5",
        "session": SESSION_DEFAULT,
        "sign": sign,
        "system_model": "33_HA",
        "timestamp": timestamp,
        "v": "2.0",
    }
    
    print(f"\n6. Sending request...")
    url = f"{SERVER_URL}{REST_URL}"
    response = requests.post(url, json=payload, verify=False, timeout=30)
    print(f"\nResponse status: {response.status_code}")
    print(f"Response content: {response.text}")
    
    try:
        result = response.json()
        print(f"\nResult ret: {result.get('ret')}")
        print(f"Result msg: {result.get('msg')}")
    except:
        print("\nResponse is not JSON")

if __name__ == "__main__":
    import sys
    
    test_all_encryptions()
    
    if len(sys.argv) >= 3:
        email = sys.argv[1]
        password = sys.argv[2]
        test_login_request(email, password)