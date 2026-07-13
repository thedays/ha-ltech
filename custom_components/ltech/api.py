import base64
import hashlib
import hmac
import json
import time
import warnings

import requests
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad

warnings.filterwarnings("ignore", message="Unverified HTTPS request")

from .const import (
    APP_ID_DEFAULT,
    FUN_URL_BIND_USER,
    FUN_URL_DEVICE_CONTROL,
    FUN_URL_DEVICE_LIST,
    FUN_URL_DEVICE_ONLINE_STATUS,
    FUN_URL_DEVICE_REQUEST_CONTROL,
    FUN_URL_DEVICE_SUBSCRIBE,
    FUN_URL_DEVICE_SYNC_STATUS,
    FUN_URL_DEVICE_UNSUBSCRIBE,
    FUN_URL_LOGIN,
    FUN_URL_PLACE_INFO,
    FUN_URL_PLACE_LIST,
    MQTT_BROKER_CN,
    REST_URL,
    SECRET_KEY_DEFAULT,
    SESSION_DEFAULT,
)


class LtechApiError(Exception):
    pass


class LtechAuthError(Exception):
    pass


class LtechApiClient:
    def __init__(self, server_url, email=None, password=None):
        self.server_url = server_url
        self.email = email
        self.password = password
        self.session = SESSION_DEFAULT
        self.app_id = APP_ID_DEFAULT
        self.secret_key = SECRET_KEY_DEFAULT
        self.user_id = None
        self.place_id = None
        self.device_name = None
        self.device_secret = None
        self.product_key = None
        self.mesh_net_key = None
        self.mesh_app_key = None
        self.mesh_uuid = None
        self.mqtt_broker = MQTT_BROKER_CN

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

    def _build_request(self, method, data=None):
        timestamp = str(int(time.time()))
        
        if data is None:
            data = ""
        else:
            data = json.dumps(data, separators=(',', ':'))
        
        encrypted_data = self._aes_encrypt(data, self.secret_key)
        
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
        
        return {
            "appid": str(self.app_id),
            "data": encrypted_data,
            "method": method,
            "platform_version": "iOS_2.8.0",
            "session": self.session,
            "sign": sign,
            "system_model": "iOS 27.0_iPhone17,5",
            "timestamp": timestamp,
            "format": "json",
            "v": "2.0",
        }

    def _send_request(self, method, data=None):
        url = f"{self.server_url}{REST_URL}"
        payload = self._build_request(method, data)
        
        headers = {
            "Content-Type": "application/json",
            "User-Agent": "SmartHome/3 CFNetwork/3890.100.1 Darwin/27.0.0",
        }
        
        try:
            import logging
            _logger = logging.getLogger(__name__)
            _logger.info(f"API Request: method={method}, url={url}, data={data}, session={self.session[:20]}...")
            
            response = requests.post(url, data=json.dumps(payload), headers=headers, verify=False, timeout=60)
            response.raise_for_status()
            result = response.json()
            
            _logger.info(f"API Response: ret={result.get('ret')}, msg={result.get('msg', '')}, data={str(result.get('data'))[:200]}")
            
            if result.get("ret") == 10:
                raise LtechAuthError("Session expired, need to re-login")
            
            if result.get("ret") != 0:
                raise LtechApiError(f"API error: {result.get('msg', 'Unknown error')} (ret={result.get('ret')})")
            
            return result.get("data", result.get("message"))
        
        except requests.exceptions.RequestException as e:
            raise LtechApiError(f"Request failed: {str(e)}") from e

    def login(self):
        login_data = {
            "devicetype": "3",
            "memberid": str(APP_ID_DEFAULT),
            "loginname": self.email,
            "devicesn": "",
            "pwd": self.password,
        }
        
        result = self._send_request(FUN_URL_LOGIN, login_data)
        
        if isinstance(result, dict):
            self.session = result.get("session", self.session)
            self.user_id = result.get("userid")
        
        return result

    def get_place_list(self):
        data = {"userId": self.user_id}
        return self._send_request(FUN_URL_PLACE_LIST, data)

    def get_place_info(self, place_id):
        data = {"placeId": place_id}
        result = self._send_request(FUN_URL_PLACE_INFO, data)
        
        if isinstance(result, dict):
            info = result.get("info", {})
            if isinstance(info, dict):
                self.mesh_net_key = info.get("netkey")
                self.mesh_app_key = info.get("applicationkey")
                self.mesh_uuid = info.get("meshuuid")
        
        return result

    def select_place(self, place_id):
        self.place_id = place_id

    def get_device_list(self, place_id=None):
        if place_id is None:
            place_id = self.place_id
        
        data = {"placeId": place_id}
        return self._send_request(FUN_URL_DEVICE_LIST, data)

    def request_device_control(self, device_ids):
        data = {"deviceIds": device_ids}
        return self._send_request(FUN_URL_DEVICE_REQUEST_CONTROL, data)

    def get_device_online_status(self, device_ids):
        data = {"deviceIds": device_ids}
        return self._send_request(FUN_URL_DEVICE_ONLINE_STATUS, data)

    def control_device(self, device_id, action):
        self.request_device_control([device_id])
        
        data = {
            "deviceId": device_id,
            "action": action,
        }
        return self._send_request(FUN_URL_DEVICE_CONTROL, data)

    def control_light(self, device_id, on, brightness=None, color_temp=None):
        action = {}
        
        if on:
            action["CharSwitch"] = "66BB0000000001EB"
        else:
            action["CharSwitch"] = "66BB0000000000EB"
        
        if brightness is not None:
            brightness_hex = f"{brightness:02X}"
            action["CharBrightness"] = f"66BB00000001{brightness_hex}EB"
        
        if color_temp is not None:
            temp_hex = f"{color_temp:04X}"
            action["CharTemp"] = f"66BB00000002{temp_hex}EB"
        
        return self.control_device(device_id, action)

    def control_switch(self, device_id, on):
        action = {}
        
        if on:
            action["CharSwitch"] = "66BB0000000001EB"
        else:
            action["CharSwitch"] = "66BB0000000000EB"
        
        return self.control_device(device_id, action)

    def subscribe_device(self):
        return self._send_request(FUN_URL_DEVICE_SUBSCRIBE, {})

    def unsubscribe_device(self):
        return self._send_request(FUN_URL_DEVICE_UNSUBSCRIBE, {})

    def get_device_sync_status(self, place_id):
        data = {"placeId": place_id}
        return self._send_request(FUN_URL_DEVICE_SYNC_STATUS, data)

    def bind_user(self):
        result = self._send_request(FUN_URL_BIND_USER, {})
        if result and isinstance(result, dict) and 'param' in result:
            try:
                param = json.loads(result['param'])
                self.product_key = param.get('productKey')
                self.device_name = param.get('deviceName')
                self.device_secret = param.get('deviceSecret')
                return param
            except json.JSONDecodeError:
                pass
        return result

    def generate_mqtt_password(self, timestamp=None):
        if not self.product_key or not self.device_name or not self.device_secret:
            return None
        
        if timestamp is None:
            timestamp = str(int(time.time() * 1000))
        
        client_id = f"{self.product_key}@@@{self.device_name}"
        sign_content = f"clientId{client_id}deviceName{self.device_name}productKey{self.product_key}timestamp{timestamp}"
        
        password = hmac.new(
            self.device_secret.encode('utf-8'),
            sign_content.encode('utf-8'),
            hashlib.sha1
        ).digest()
        
        return {
            "broker": MQTT_BROKER_CN,
            "port": 8883,
            "client_id": client_id,
            "username": f"{self.device_name}&{self.product_key}",
            "password": base64.b64encode(password).decode('utf-8'),
            "timestamp": timestamp,
            "topic": f"/{self.product_key}/{self.device_name}/user/get"
        }