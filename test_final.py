import hashlib
import json
import time
import requests
from collections import OrderedDict
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
import urllib3

urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

APP_ID_DEFAULT = 119011416470103
SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
SESSION_DEFAULT = "cqq577bze26adn1wbt5uz0nwk7zlfs03"
FUN_URL_LOGIN = "ysnetwork.base.com.user.app.login"
SERVER_URL = "https://apic.ltsys.com.cn:2443/"
REST_URL = "openapi/rest"

def md5_sign_lower(data_str):
    return hashlib.md5(data_str.encode("utf-8")).hexdigest().lower()

def aes_encrypt_ecb_hex(data, key):
    key_bytes = key.encode("utf-8")
    cipher = AES.new(key_bytes, AES.MODE_ECB)
    padded = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded)
    return encrypted.hex().upper()

def send_request(request_data):
    try:
        response = requests.post(f"{SERVER_URL}{REST_URL}", data=request_data, headers={"Content-Type": "application/json"}, verify=False, timeout=30)
        return response.text
    except Exception as e:
        return f"Error: {e}"

login_data = OrderedDict([
    ("devicesn", ""),
    ("devicetype", "3"),
    ("loginname", "15019860713"),
    ("memberid", APP_ID_DEFAULT),
    ("pwd", "Kaggie00"),
])

login_json = json.dumps(login_data, separators=(',', ':'))
print(f"Login JSON: {login_json}")

encrypted_data = aes_encrypt_ecb_hex(login_json, SECRET_KEY_DEFAULT)
timestamp = str(int(time.time() * 1000))

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

sign = md5_sign_lower(sign_str)

print(f"\nSign string: {sign_str}")
print(f"Sign: {sign}")
print(f"Encrypted data: {encrypted_data}")

print("\n" + "="*80)
print("Test 1: Full RequestObject with correct order")
print("="*80)

request_object = OrderedDict([
    ("appid", APP_ID_DEFAULT),
    ("data", encrypted_data),
    ("method", FUN_URL_LOGIN),
    ("platform_version", "Android_3.3.5"),
    ("session", SESSION_DEFAULT),
    ("sign", sign),
    ("system_model", "33_HA"),
    ("timestamp", timestamp),
    ("format", "json"),
    ("v", "2.0"),
])

request_json = json.dumps(request_object, separators=(',', ':'))
response = send_request(request_json)
print(f"Request JSON: {request_json}")
print(f"Response: {response}")

print("\n" + "="*80)
print("Test 2: With actual Android device model")
print("="*80)

request_object2 = OrderedDict([
    ("appid", APP_ID_DEFAULT),
    ("data", encrypted_data),
    ("method", FUN_URL_LOGIN),
    ("platform_version", "Android_3.3.5"),
    ("session", SESSION_DEFAULT),
    ("sign", sign),
    ("system_model", "33_Pixel 8"),
    ("timestamp", timestamp),
    ("format", "json"),
    ("v", "2.0"),
])

request_json2 = json.dumps(request_object2, separators=(',', ':'))
response2 = send_request(request_json2)
print(f"Response: {response2}")

print("\n" + "="*80)
print("Test 3: Try without platform_version and system_model")
print("="*80)

request_object3 = OrderedDict([
    ("appid", APP_ID_DEFAULT),
    ("data", encrypted_data),
    ("method", FUN_URL_LOGIN),
    ("session", SESSION_DEFAULT),
    ("sign", sign),
    ("timestamp", timestamp),
    ("format", "json"),
    ("v", "2.0"),
])

request_json3 = json.dumps(request_object3, separators=(',', ':'))
response3 = send_request(request_json3)
print(f"Response: {response3}")

print("\n" + "="*80)
print("Test 4: Try different session value")
print("="*80)

session_test = "test_session_123"

sign_str4 = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{encrypted_data}"
    f"json"
    f"{FUN_URL_LOGIN}"
    f"{session_test}"
    f"{timestamp}"
    f"2.0"
    f"{SECRET_KEY_DEFAULT}"
)

sign4 = md5_sign_lower(sign_str4)

request_object4 = OrderedDict([
    ("appid", APP_ID_DEFAULT),
    ("data", encrypted_data),
    ("method", FUN_URL_LOGIN),
    ("platform_version", "Android_3.3.5"),
    ("session", session_test),
    ("sign", sign4),
    ("system_model", "33_HA"),
    ("timestamp", timestamp),
    ("format", "json"),
    ("v", "2.0"),
])

request_json4 = json.dumps(request_object4, separators=(',', ':'))
response4 = send_request(request_json4)
print(f"Session: {session_test}")
print(f"Sign: {sign4}")
print(f"Response: {response4}")