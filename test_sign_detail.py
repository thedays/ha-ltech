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
cipher = AES.new(key_bytes, AES.MODE_ECB)
padded = pad(login_json.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted_data = cipher.encrypt(padded).hex().upper()

timestamp = str(int(time.time() * 1000))
format_val = "json"
v_val = "2.0"

print("\n" + "=" * 80)
print("Java signature order from RequestObject.java:")
print("sb.append(secretKey);")
print("sb.append(this.appid);")
print("sb.append(this.data);")
print("sb.append(this.format);")
print("sb.append(method);")
print("sb.append(session);")
print("if (!TextUtils.isEmpty(this.target_appid)) { sb.append(this.target_appid); }")
print("sb.append(this.timestamp);")
print("sb.append(this.v);")
print("sb.append(secretKey);")
print("=" * 80)

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

print(f"\nSign string components:")
print(f"1. secretKey: {SECRET_KEY_DEFAULT}")
print(f"2. appid: {APP_ID_DEFAULT}")
print(f"3. data: {encrypted_data[:30]}...")
print(f"4. format: {format_val}")
print(f"5. method: {FUN_URL_LOGIN}")
print(f"6. session: {SESSION_DEFAULT}")
print(f"7. timestamp: {timestamp}")
print(f"8. v: {v_val}")
print(f"9. secretKey: {SECRET_KEY_DEFAULT}")

print(f"\nFull sign string length: {len(sign_str)}")

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

print(f"\nRequestObject fields (Java order):")
print("appid, data, method, platform_version, session, sign, system_model, target_appid, timestamp, format, v")

print(f"\nSending request with explicit JSON string...")
request_json = json.dumps(request_object, separators=(',', ':'))
print(f"Request JSON: {request_json}")

response = requests.post(f"{SERVER_URL}{REST_URL}", data=request_json, headers={"Content-Type": "application/json"}, verify=False, timeout=30)
print(f"\nResponse:")
print(f"Status: {response.status_code}")
print(f"Content: {response.text}")