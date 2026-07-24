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
FUN_URL_LOGIN = "ysnetwork.base.com.user.app.login"
SERVER_URL = "https://apic.ltsys.com.cn:2443/"
REST_URL = "openapi/rest"

login_data = {
    "devicesn": "",
    "devicetype": "3",
    "loginname": "15019860713",
    "memberid": APP_ID_DEFAULT,
    "pwd": "Kaggie00",
}

login_json = json.dumps(login_data, separators=(',', ':'))
print(f"Login JSON: {login_json}")

key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
iv_bytes = key_bytes[:AES.block_size]

cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
padded = pad(login_json.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted_data = cipher.encrypt(padded).hex().upper()

print(f"\nCBC encrypted data: {encrypted_data}")

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

sign = hashlib.md5(sign_str.encode("utf-8")).hexdigest().upper()
print(f"\nSign: {sign}")

request_object = {
    "appid": APP_ID_DEFAULT,
    "data": encrypted_data,
    "method": FUN_URL_LOGIN,
    "platform_version": "Android_3.3.5",
    "session": SESSION_DEFAULT,
    "sign": sign,
    "system_model": "33_HA",
    "timestamp": timestamp,
    "format": format_val,
    "v": v_val,
}

request_json = json.dumps(request_object, separators=(',', ':'))
print(f"\nRequest JSON: {request_json}")

response = requests.post(f"{SERVER_URL}{REST_URL}", data=request_json, headers={"Content-Type": "application/json"}, verify=False, timeout=30)
print(f"\nResponse:")
print(f"Status: {response.status_code}")
print(f"Content: {response.text}")