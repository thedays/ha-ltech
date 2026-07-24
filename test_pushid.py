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

def md5_sign(data_str):
    return hashlib.md5(data_str.encode("utf-8")).hexdigest().upper()

def aes_encrypt_ecb(data, key):
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

push_ids_to_test = [
    "",
    "test_device_id",
    "1234567890abcdef",
    "alicloud_push_device_id",
    "ha_integration_test",
]

for push_id in push_ids_to_test:
    print(f"\n{'='*80}")
    print(f"Testing with devicesn: '{push_id}'")
    print(f"{'='*80}")
    
    login_data = OrderedDict([
        ("devicesn", push_id),
        ("devicetype", "3"),
        ("loginname", "15019860713"),
        ("memberid", APP_ID_DEFAULT),
        ("pwd", "Kaggie00"),
    ])
    
    login_json = json.dumps(login_data, separators=(',', ':'))
    print(f"Login JSON: {login_json}")
    
    encrypted_data = aes_encrypt_ecb(login_json, SECRET_KEY_DEFAULT)
    print(f"Encrypted: {encrypted_data[:50]}...")
    
    timestamp = str(int(time.time() * 1000))
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
    
    sign = md5_sign(sign_str)
    print(f"Sign: {sign}")
    
    request_object = OrderedDict([
        ("appid", APP_ID_DEFAULT),
        ("data", encrypted_data),
        ("method", FUN_URL_LOGIN),
        ("platform_version", "Android_3.3.5"),
        ("session", SESSION_DEFAULT),
        ("sign", sign),
        ("system_model", "33_HA"),
        ("timestamp", timestamp),
        ("format", format_val),
        ("v", v_val),
    ])
    
    request_json = json.dumps(request_object, separators=(',', ':'))
    response = send_request(request_json)
    print(f"Response: {response}")

print("\n" + "="*80)
print("Testing with Android device type (devicetype='2')")
print("="*80)

login_data_android = OrderedDict([
    ("devicesn", ""),
    ("devicetype", "2"),
    ("loginname", "15019860713"),
    ("memberid", APP_ID_DEFAULT),
    ("pwd", "Kaggie00"),
])

login_json = json.dumps(login_data_android, separators=(',', ':'))
print(f"Login JSON: {login_json}")

encrypted_data = aes_encrypt_ecb(login_json, SECRET_KEY_DEFAULT)
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

sign = md5_sign(sign_str)

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
print(f"Response: {response}")