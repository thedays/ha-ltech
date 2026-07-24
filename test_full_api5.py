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
FUN_URL_PLACE_LIST = "ysnetwork.base.com.area.place.user.list"
FUN_URL_DEVICE_LIST = "ysnetwork.base.area.device.list"
SERVER_URL = "https://apic.ltsys.com.cn:2443/"

class LtechApiClient:
    def __init__(self):
        self.session = SESSION_DEFAULT
        self.app_id = APP_ID_DEFAULT
        self.secret_key = SECRET_KEY_DEFAULT
        self.user_id = None
        self.place_id = None

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
        except requests.exceptions.ReadTimeout:
            print(f"Request timeout after {timeout} seconds")
            return None
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
            print(f"✅ Login SUCCESS")
            print(f"  - session: {self.session}")
            print(f"  - secretkey: {self.secret_key}")
            print(f"  - userid: {self.user_id}")
        
        return result

    def get_place_list(self):
        data = {"userId": self.user_id}
        return self._send_request(FUN_URL_PLACE_LIST, data)

    def get_device_list(self, place_id):
        data = {"placeId": place_id}
        return self._send_request(FUN_URL_DEVICE_LIST, data, timeout=120)

def test_api(account, password):
    client = LtechApiClient()
    
    print(f"\n{'='*80}")
    print(f"Step 1: Login")
    print(f"{'='*80}")
    login_result = client.login(account, password)
    
    if not login_result:
        return
    
    print(f"\n{'='*80}")
    print(f"Step 2: Get Place List")
    print(f"{'='*80}")
    place_list = client.get_place_list()
    if place_list:
        print(f"✅ Place List (full):")
        print(f"{json.dumps(place_list, indent=2, ensure_ascii=False)}")
    
    if isinstance(place_list, dict) and 'rows' in place_list and place_list['rows']:
        first_place = place_list['rows'][0]
        place_id = first_place.get('placeid') or first_place.get('placeId') or first_place.get('id')
        print(f"\n{'='*80}")
        print(f"Step 3: Get Device List (place_id={place_id})")
        print(f"{'='*80}")
        device_list = client.get_device_list(place_id)
        if device_list:
            print(f"✅ Device List (full):")
            print(f"{json.dumps(device_list, indent=2, ensure_ascii=False)}")

if __name__ == "__main__":
    if len(sys.argv) >= 3:
        test_api(sys.argv[1], sys.argv[2])
    else:
        print("Usage: python test_full_api5.py <account> <password>")