import base64
import hashlib
import json
import time
import sys

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
FUN_URL_BIND_USER = "ysnetwork.base.com.deviceparam.binduser"
SERVER_URL = "https://apic.ltsys.com.cn:2443/"

class LtechApiClient:
    def __init__(self):
        self.session = SESSION_DEFAULT
        self.app_id = APP_ID_DEFAULT
        self.secret_key = SECRET_KEY_DEFAULT
        self.user_id = None
        self.product_key = None
        self.device_name = None
        self.device_secret = None

    def _aes_encrypt(self, data, key):
        key_bytes = key.encode("utf-8")
        iv_bytes = key_bytes[:AES.block_size]
        cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
        padded_data = pad(data.encode("utf-8"), AES.block_size, style='pkcs7')
        encrypted = cipher.encrypt(padded_data)
        b64 = base64.b64encode(encrypted).decode("utf-8")
        return b64.replace('+', '-').replace('/', '_').rstrip('=')

    def _md5_sign(self, data_str):
        return hashlib.md5(data_str.encode("utf-8")).hexdigest().lower()

    def _send_request(self, method, data=None, timeout=60):
        timestamp = str(int(time.time()))
        
        if data is None:
            data_str = ""
        else:
            data_str = json.dumps(data, separators=(',', ':'))
        
        encrypted_data = self._aes_encrypt(data_str, self.secret_key)
        
        sign_str = (
            f"{self.secret_key}"
            f"{self.app_id}"
            f"{encrypted_data}"
            f"json"
            f"{method}"
            f"{self.session}"
            f"{timestamp}"
            f"2.0"
            f"{self.secret_key}"
        )
        sign = self._md5_sign(sign_str)
        
        payload = {
            "method": method,
            "format": "json",
            "platform_version": "iOS_2.8.0",
            "data": encrypted_data,
            "system_model": "iOS 27.0_iPhone17,5",
            "v": "2.0",
            "session": self.session,
            "timestamp": timestamp,
            "appid": str(self.app_id),
            "sign": sign,
        }
        
        url = f"{SERVER_URL}{REST_URL}"
        headers = {
            "Content-Type": "application/json",
            "User-Agent": "SmartHome/3 CFNetwork/3890.100.1 Darwin/27.0.0",
        }
        
        print(f"Request: method={method}, data={data_str[:100]}...")
        try:
            response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False, timeout=timeout)
            result = response.json()
            
            if result.get("ret") != 0:
                print(f"API error: ret={result.get('ret')}, msg={result.get('message')}")
                return None
            
            return result.get("data", result.get("message"))
        except Exception as e:
            print(f"Request failed: {e}")
            return None

    def login(self, account, password):
        login_data = {
            "devicetype": "3",
            "memberid": str(self.app_id),
            "loginname": account,
            "devicesn": "",
            "pwd": password,
        }
        
        result = self._send_request(FUN_URL_LOGIN, login_data)
        
        if result:
            self.session = result.get("session", self.session)
            self.secret_key = result.get("secretkey", self.secret_key)
            self.user_id = result.get("userid")
        
        return result

    def bind_user(self):
        return self._send_request(FUN_URL_BIND_USER, {})

def test_api(account, password):
    client = LtechApiClient()
    
    print(f"\n{'='*80}")
    print(f"Step 1: Login")
    print(f"{'='*80}")
    login_result = client.login(account, password)
    
    if not login_result:
        return
    
    print(f"✅ Login SUCCESS")
    print(f"  - session: {client.session}")
    print(f"  - secretkey: {client.secret_key}")
    print(f"  - userid: {client.user_id}")
    
    print(f"\n{'='*80}")
    print(f"Step 2: Bind User (获取MQTT凭证)")
    print(f"{'='*80}")
    bind_result = client.bind_user()
    if bind_result is not None:
        print(f"✅ Bind User Response:")
        print(f"{json.dumps(bind_result, indent=2, ensure_ascii=False)}")
        
        if isinstance(bind_result, dict):
            print(f"\n{'='*60}")
            print(f"MQTT凭证:")
            print(f"{'='*60}")
            print(f"productKey: {bind_result.get('productKey')}")
            print(f"deviceName: {bind_result.get('deviceName')}")
            print(f"deviceSecret: {bind_result.get('deviceSecret')}")
            print(f"iotId: {bind_result.get('iotId')}")
            
            client.product_key = bind_result.get('productKey')
            client.device_name = bind_result.get('deviceName')
            client.device_secret = bind_result.get('deviceSecret')
            
            if client.product_key and client.device_name and client.device_secret:
                print(f"\n{'='*60}")
                print(f"阿里云IoT MQTT连接信息:")
                print(f"{'='*60}")
                print(f"Broker: {client.product_key}.iot-as-mqtt.cn-shanghai.aliyuncs.com")
                print(f"Port: 1883 (TCP) / 8883 (SSL)")
                print(f"Client ID: {client.product_key}@@@{client.device_name}")
                print(f"Username: {client.device_name}&{client.product_key}")
                print(f"Password: 需要根据deviceSecret生成")

if __name__ == "__main__":
    if len(sys.argv) >= 3:
        test_api(sys.argv[1], sys.argv[2])
    else:
        print("Usage: python test_mqtt_bind_user.py <account> <password>")