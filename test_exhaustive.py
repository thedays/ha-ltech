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

def aes_encrypt(data, key, mode='ECB'):
    key_bytes = key.encode("utf-8")
    if mode == 'ECB':
        cipher = AES.new(key_bytes, AES.MODE_ECB)
    else:
        iv_bytes = key_bytes[:AES.block_size]
        cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
    padded = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded)
    return encrypted.hex().upper()

def send_request(request_data):
    try:
        response = requests.post(f"{SERVER_URL}{REST_URL}", data=request_data, headers={"Content-Type": "application/json"}, verify=False, timeout=30)
        return response.text
    except Exception as e:
        return f"Error: {e}"

def test_scenario(name, login_data, encrypt_mode, request_order):
    print(f"\n{'='*80}")
    print(f"Scenario: {name}")
    print(f"{'='*80}")
    
    login_json = json.dumps(login_data, separators=(',', ':'))
    print(f"Login JSON: {login_json}")
    
    encrypted_data = aes_encrypt(login_json, SECRET_KEY_DEFAULT, encrypt_mode)
    print(f"Encrypted ({encrypt_mode}): {encrypted_data[:50]}...")
    
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
    
    request_object = OrderedDict(request_order)
    request_object['appid'] = APP_ID_DEFAULT
    request_object['data'] = encrypted_data
    request_object['method'] = FUN_URL_LOGIN
    request_object['session'] = SESSION_DEFAULT
    request_object['sign'] = sign
    request_object['timestamp'] = timestamp
    request_object['format'] = format_val
    request_object['v'] = v_val
    request_object['platform_version'] = "Android_3.3.5"
    request_object['system_model'] = "33_HA"
    
    request_json = json.dumps(request_object, separators=(',', ':'))
    print(f"Request JSON: {request_json[:200]}...")
    
    response = send_request(request_json)
    print(f"Response: {response}")
    
    return response

login_data_base = {
    "devicesn": "",
    "devicetype": "3",
    "loginname": "15019860713",
    "memberid": APP_ID_DEFAULT,
    "pwd": "Kaggie00",
}

login_data_ordered_java = OrderedDict([
    ("devicesn", ""),
    ("devicetype", "3"),
    ("loginname", "15019860713"),
    ("memberid", APP_ID_DEFAULT),
    ("pwd", "Kaggie00"),
])

request_order_java = [
    ("appid", None),
    ("data", None),
    ("method", None),
    ("platform_version", None),
    ("session", None),
    ("sign", None),
    ("system_model", None),
    ("timestamp", None),
    ("format", None),
    ("v", None),
]

test_scenario("ECB + Java field order + Java request order", login_data_ordered_java, 'ECB', request_order_java)

test_scenario("CBC + Java field order + Java request order", login_data_ordered_java, 'CBC', request_order_java)

login_data_ordered_reversed = OrderedDict([
    ("pwd", "Kaggie00"),
    ("memberid", APP_ID_DEFAULT),
    ("loginname", "15019860713"),
    ("devicetype", "3"),
    ("devicesn", ""),
])
test_scenario("ECB + reversed field order", login_data_ordered_reversed, 'ECB', request_order_java)

test_scenario("CBC + reversed field order", login_data_ordered_reversed, 'CBC', request_order_java)

login_data_memberid_str = OrderedDict([
    ("devicesn", ""),
    ("devicetype", "3"),
    ("loginname", "15019860713"),
    ("memberid", str(APP_ID_DEFAULT)),
    ("pwd", "Kaggie00"),
])
test_scenario("ECB + memberid as string", login_data_memberid_str, 'ECB', request_order_java)

test_scenario("CBC + memberid as string", login_data_memberid_str, 'CBC', request_order_java)

print("\n" + "="*80)
print("Test: Check if appid in sign is string or long")
print("="*80)

encrypted_data = aes_encrypt(json.dumps(login_data_ordered_java, separators=(',', ':')), SECRET_KEY_DEFAULT, 'ECB')
timestamp = str(int(time.time() * 1000))

sign_str1 = (
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

sign_str2 = (
    f"{SECRET_KEY_DEFAULT}"
    f"{str(APP_ID_DEFAULT)}"
    f"{encrypted_data}"
    f"json"
    f"{FUN_URL_LOGIN}"
    f"{SESSION_DEFAULT}"
    f"{timestamp}"
    f"2.0"
    f"{SECRET_KEY_DEFAULT}"
)

sign1 = md5_sign(sign_str1)
sign2 = md5_sign(sign_str2)

print(f"AppID as long: {APP_ID_DEFAULT}")
print(f"AppID as string: {str(APP_ID_DEFAULT)}")
print(f"Sign with long: {sign1}")
print(f"Sign with string: {sign2}")
print(f"Signs equal: {sign1 == sign2}")