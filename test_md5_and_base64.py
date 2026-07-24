import hashlib
import json
import time
import requests
import base64
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

def md5_sign_upper(data_str):
    return hashlib.md5(data_str.encode("utf-8")).hexdigest().upper()

def aes_encrypt_ecb_hex(data, key):
    key_bytes = key.encode("utf-8")
    cipher = AES.new(key_bytes, AES.MODE_ECB)
    padded = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded)
    return encrypted.hex().upper()

def aes_encrypt_ecb_base64(data, key):
    key_bytes = key.encode("utf-8")
    cipher = AES.new(key_bytes, AES.MODE_ECB)
    padded = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded)
    return base64.b64encode(encrypted).decode("utf-8")

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

print("\n" + "="*80)
print("Test 1: ECB + Hex + MD5 lowercase")
print("="*80)

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
print(f"Sign: {sign}")
print(f"Response: {response}")

print("\n" + "="*80)
print("Test 2: ECB + Base64 + MD5 uppercase")
print("="*80)

encrypted_data_b64 = aes_encrypt_ecb_base64(login_json, SECRET_KEY_DEFAULT)
timestamp = str(int(time.time() * 1000))

sign_str_b64 = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{encrypted_data_b64}"
    f"json"
    f"{FUN_URL_LOGIN}"
    f"{SESSION_DEFAULT}"
    f"{timestamp}"
    f"2.0"
    f"{SECRET_KEY_DEFAULT}"
)

sign_b64 = md5_sign_upper(sign_str_b64)

request_object_b64 = OrderedDict([
    ("appid", APP_ID_DEFAULT),
    ("data", encrypted_data_b64),
    ("method", FUN_URL_LOGIN),
    ("platform_version", "Android_3.3.5"),
    ("session", SESSION_DEFAULT),
    ("sign", sign_b64),
    ("system_model", "33_HA"),
    ("timestamp", timestamp),
    ("format", "json"),
    ("v", "2.0"),
])

request_json_b64 = json.dumps(request_object_b64, separators=(',', ':'))
response_b64 = send_request(request_json_b64)
print(f"Encrypted (Base64): {encrypted_data_b64[:50]}...")
print(f"Sign: {sign_b64}")
print(f"Response: {response_b64}")

print("\n" + "="*80)
print("Test 3: ECB + Base64 + MD5 lowercase")
print("="*80)

sign_b64_lower = md5_sign_lower(sign_str_b64)

request_object_b64_lower = OrderedDict([
    ("appid", APP_ID_DEFAULT),
    ("data", encrypted_data_b64),
    ("method", FUN_URL_LOGIN),
    ("platform_version", "Android_3.3.5"),
    ("session", SESSION_DEFAULT),
    ("sign", sign_b64_lower),
    ("system_model", "33_HA"),
    ("timestamp", timestamp),
    ("format", "json"),
    ("v", "2.0"),
])

request_json_b64_lower = json.dumps(request_object_b64_lower, separators=(',', ':'))
response_b64_lower = send_request(request_json_b64_lower)
print(f"Sign: {sign_b64_lower}")
print(f"Response: {response_b64_lower}")

print("\n" + "="*80)
print("Test 4: CBC + Base64")
print("="*80)

key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
iv_bytes = key_bytes[:AES.block_size]
cipher_cbc = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
padded_cbc = pad(login_json.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted_cbc = cipher_cbc.encrypt(padded_cbc)
encrypted_cbc_b64 = base64.b64encode(encrypted_cbc).decode("utf-8")
timestamp = str(int(time.time() * 1000))

sign_str_cbc_b64 = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{encrypted_cbc_b64}"
    f"json"
    f"{FUN_URL_LOGIN}"
    f"{SESSION_DEFAULT}"
    f"{timestamp}"
    f"2.0"
    f"{SECRET_KEY_DEFAULT}"
)

sign_cbc_b64 = md5_sign_upper(sign_str_cbc_b64)

request_object_cbc_b64 = OrderedDict([
    ("appid", APP_ID_DEFAULT),
    ("data", encrypted_cbc_b64),
    ("method", FUN_URL_LOGIN),
    ("platform_version", "Android_3.3.5"),
    ("session", SESSION_DEFAULT),
    ("sign", sign_cbc_b64),
    ("system_model", "33_HA"),
    ("timestamp", timestamp),
    ("format", "json"),
    ("v", "2.0"),
])

request_json_cbc_b64 = json.dumps(request_object_cbc_b64, separators=(',', ':'))
response_cbc_b64 = send_request(request_json_cbc_b64)
print(f"Encrypted (CBC+Base64): {encrypted_cbc_b64[:50]}...")
print(f"Sign: {sign_cbc_b64}")
print(f"Response: {response_cbc_b64}")