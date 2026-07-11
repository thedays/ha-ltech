import json
import logging
import sys
import time

logging.basicConfig(level=logging.DEBUG)

try:
    from linkkit import linkkit
    LINKKIT_AVAILABLE = True
except ImportError:
    print("❌ aliyun-iot-linkkit not installed. Install with: pip install aliyun-iot-linkkit")
    LINKKIT_AVAILABLE = False

import requests
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
import hashlib
import base64

BASE_URL = "https://apic.ltsys.com.cn:2443/openapi/rest"
SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
SESSION_DEFAULT = "cqq577bze26adn1wbt5uz0nwk7zlfs03"
APP_ID_DEFAULT = 119011416470103
MQTT_BROKER_CN = "iot-060a5shm.mqtt.iothub.aliyuncs.com"

FUN_URL_LOGIN = "ysnetwork.base.com.user.app.login"
FUN_URL_BIND_USER = "ysnetwork.base.com.deviceparam.binduser"


def aes_encrypt(data, key):
    key_bytes = key.encode("utf-8")
    iv_bytes = key_bytes[:AES.block_size]
    cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
    padded_data = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    b64 = base64.b64encode(encrypted).decode("utf-8")
    return b64.replace('+', '-').replace('/', '_').rstrip('=')


def md5_sign(data_str):
    return hashlib.md5(data_str.encode("utf-8")).hexdigest().lower()


def build_request(method, data, app_id, session, secret_key):
    timestamp = str(int(time.time()))
    
    if data is None:
        data = ""
    else:
        data = json.dumps(data, separators=(',', ':'))
    
    encrypted_data = aes_encrypt(data, secret_key)
    
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
        "appid": str(app_id),
        "data": encrypted_data,
        "method": method,
        "platform_version": "iOS_2.8.0",
        "session": session,
        "sign": sign,
        "system_model": "iOS 27.0_iPhone17,5",
        "timestamp": timestamp,
        "format": "json",
        "v": "2.0",
    }


def send_request(url, method, data, app_id, session, secret_key):
    payload = build_request(method, data, app_id, session, secret_key)
    
    try:
        response = requests.post(url, json=payload, verify=False, timeout=10)
        response.raise_for_status()
        result = response.json()
        return result
    except Exception as e:
        print(f"Request failed: {e}")
        return None


def login(email, password):
    print("\n" + "="*60)
    print("Step 1: Login to Ltech API")
    print("="*60)
    
    login_data = {
        "devicetype": "3",
        "memberid": str(APP_ID_DEFAULT),
        "loginname": email,
        "devicesn": "",
        "pwd": password,
    }
    
    result = send_request(BASE_URL, FUN_URL_LOGIN, login_data, APP_ID_DEFAULT, SESSION_DEFAULT, SECRET_KEY_DEFAULT)
    
    if result and result.get("ret") == 0:
        data = result.get("data", {})
        print(f"✅ Login successful!")
        print(f"   User ID: {data.get('userid')}")
        print(f"   Session: {data.get('session')[:20]}...")
        print(f"   Secret Key: {data.get('secretkey')}")
        return {
            "session": data.get("session"),
            "secret_key": data.get("secretkey"),
            "user_id": data.get("userid")
        }
    else:
        print(f"❌ Login failed: {result}")
        return None


def bind_user(session, secret_key):
    print("\n" + "="*60)
    print("Step 2: Bind User for MQTT credentials")
    print("="*60)
    
    result = send_request(BASE_URL, FUN_URL_BIND_USER, {}, APP_ID_DEFAULT, session, secret_key)
    
    if result and result.get("ret") == 0:
        data = result.get("data", {})
        if isinstance(data, dict) and 'param' in data:
            try:
                param = json.loads(data['param'])
                print(f"✅ Bind user result: {json.dumps(param, indent=2)}")
                print(f"   Product Key: {param.get('productKey')}")
                print(f"   Device Name: {param.get('deviceName')}")
                print(f"   Device Secret: {param.get('deviceSecret')}")
                return param
            except json.JSONDecodeError:
                pass
        print(f"✅ Bind user result: {json.dumps(data, indent=2)}")
        return data
    else:
        print(f"❌ Bind user failed: {result}")
        return None


def on_connect(session_flag, rc, userdata):
    if rc == 0:
        print("✅ LinkKit connected successfully!")
    else:
        print(f"❌ LinkKit connection failed with code {rc}")


def on_disconnect(session_flag, rc, userdata):
    print(f"🔌 LinkKit disconnected with code {rc}")


def on_message(session_flag, topic, payload, qos, userdata):
    try:
        message = payload.decode("utf-8")
        print(f"\n📩 Message received on topic {topic}:")
        print(f"   {message}")
    except Exception as e:
        print(f"❌ Failed to process message: {e}")


def test_linkkit(product_key, device_name, device_secret):
    print("\n" + "="*60)
    print("Step 3: Connect with LinkKit SDK")
    print("="*60)
    print(f"   Product Key: {product_key}")
    print(f"   Device Name: {device_name}")
    print(f"   Device Secret: {device_secret[:8]}...")
    print(f"   Endpoint: {MQTT_BROKER_CN}:8883")
    
    lk = linkkit.LinkKit(
        host_name="cn-shanghai",
        product_key=product_key,
        device_name=device_name,
        device_secret=device_secret
    )
    
    lk.config_mqtt(
        endpoint=MQTT_BROKER_CN,
        port=8883,
        transport="TCP",
        secure="TLS",
        keep_alive=60
    )
    
    lk.on_connect = on_connect
    lk.on_disconnect = on_disconnect
    lk.on_message = on_message
    
    print("\n🔌 Starting LinkKit connection...")
    lk.connect_async()
    
    print("\n⏳ Waiting for connection result (5 seconds)...")
    for i in range(5):
        print(f"   Waiting {i+1}/5...")
        time.sleep(1)
    
    print("\n✅ Test completed! LinkKit SDK is working.")
    lk.disconnect()


if __name__ == "__main__":
    if len(sys.argv) >= 3:
        email = sys.argv[1]
        password = sys.argv[2]
    else:
        email = input("Enter email/phone: ")
        password = input("Enter password: ")
    
    if not LINKKIT_AVAILABLE:
        sys.exit(1)
    
    login_result = login(email, password)
    if not login_result:
        sys.exit(1)
    
    bind_result = bind_user(login_result["session"], login_result["secret_key"])
    if not bind_result:
        sys.exit(1)
    
    test_linkkit(
        bind_result["productKey"],
        bind_result["deviceName"],
        bind_result["deviceSecret"]
    )
