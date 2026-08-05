import base64
import hashlib
import hmac
import http.client
import json
import logging
import ssl
import time
import uuid

from Crypto.Cipher import AES
from Crypto.Util.Padding import pad

_LOGGER = logging.getLogger(__name__)

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
    TEST_SERVER_URL,
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

    @staticmethod
    def _parse_server_url(server_url):
        """Parse host and port from server_url (e.g. https://apic.ltsys.com.cn:2443/)."""
        url = server_url.rstrip("/")
        if url.startswith("https://"):
            url = url[8:]
        elif url.startswith("http://"):
            url = url[7:]
        if ":" in url:
            host, port_str = url.split(":", 1)
            port = int(port_str)
        else:
            host = url
            port = 443
        return host, port

    def _md5_sign(self, data_str):
        return hashlib.md5(data_str.encode("utf-8")).hexdigest().lower()

    def _build_request(self, method, data=None):
        timestamp = str(int(time.time()))
        
        if data is None:
            data = ""
        else:
            if isinstance(data, list) and all(isinstance(item, tuple) for item in data):
                data_dict = dict(data)
                ordered_keys = [k for k, _ in data]
                ordered_data = []
                for key in ordered_keys:
                    if key in data_dict:
                        ordered_data.append(f'"{key}":{json.dumps(data_dict[key])}')
                data = "{" + ",".join(ordered_data) + "}"
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
        
        _LOGGER.debug(f"[BUILD_REQUEST] method={method}")
        _LOGGER.debug(f"[BUILD_REQUEST] raw_data={data}")
        _LOGGER.debug(f"[BUILD_REQUEST] encrypted_data={encrypted_data}")
        _LOGGER.debug(f"[BUILD_REQUEST] sign_str={sign_str}")
        _LOGGER.debug(f"[BUILD_REQUEST] sign={sign}")
        _LOGGER.debug(f"[BUILD_REQUEST] session={self.session}")
        _LOGGER.debug(f"[BUILD_REQUEST] timestamp={timestamp}")
        _LOGGER.debug(f"[BUILD_REQUEST] secret_key={self.secret_key}")
        _LOGGER.debug(f"[BUILD_REQUEST] full_payload={json.dumps(payload, separators=(',', ':'))}")
        
        return payload

    def _send_request(self, method, data=None, timeout=60, retry_on_auth_error=True, fallback_to_test=True):
        # Parse host and port from server_url
        host, port = self._parse_server_url(self.server_url)
        url = f"{self.server_url}{REST_URL}"
        payload = self._build_request(method, data)
        payload_str = json.dumps(payload, separators=(',', ':'))
        
        headers = {
            "Host": host,
            "Content-Type": "application/json",
            "User-Agent": "SmartHome/3 CFNetwork/3890.100.1 Darwin/27.0.0",
            "Content-Length": str(len(payload_str)),
            "Connection": "close",
        }
        
        max_retries = 3
        for attempt in range(max_retries):
            try:
                _LOGGER.info(f"[API_REQUEST] method={method}, url={url}, attempt={attempt+1}/{max_retries}, session={self.session[:20]}...")
                _LOGGER.debug(f"[API_REQUEST] full_payload={payload_str}")
                _LOGGER.debug(f"[API_REQUEST] headers={headers}")
                
                import socket
                sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                sock.settimeout(timeout)
                
                try:
                    sock.connect((host, port))
                    
                    ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLSv1_2)
                    ssl_context.check_hostname = False
                    ssl_context.verify_mode = ssl.CERT_NONE
                    ssl_sock = ssl_context.wrap_socket(sock, server_hostname=host)
                    
                    request_line = f"POST /{REST_URL} HTTP/1.1\r\n"
                    headers_str = "\r\n".join(f"{k}: {v}" for k, v in headers.items())
                    request = f"{request_line}{headers_str}\r\n\r\n{payload_str}"
                    
                    ssl_sock.sendall(request.encode("utf-8"))
                    
                    response_data = ssl_sock.recv(8192)
                    while True:
                        try:
                            chunk = ssl_sock.recv(8192)
                            if not chunk:
                                break
                            response_data += chunk
                        except ssl.SSLWantReadError:
                            break
                        except Exception:
                            break
                    
                    ssl_sock.close()
                    
                    response_str = response_data.decode("utf-8")
                    _LOGGER.debug(f"[API_RESPONSE] full_response={response_str[:500]}")

                    # Parse HTTP status code from response
                    http_status = 0
                    if response_str.startswith("HTTP/1."):
                        try:
                            status_part = response_str.split(" ", 2)
                            http_status = int(status_part[1])
                        except (IndexError, ValueError):
                            pass

                    if http_status and http_status != 200:
                        _LOGGER.error(f"[API_HTTP_ERROR] method={method}, HTTP status={http_status}, response={response_str[:300]}")
                        if http_status == 403:
                            if fallback_to_test and self.server_url != TEST_SERVER_URL:
                                _LOGGER.warning(f"[API_FALLBACK] Primary server returned 403, falling back to test server: {TEST_SERVER_URL}")
                                self.server_url = TEST_SERVER_URL
                                return self._send_request(method, data, timeout, retry_on_auth_error, fallback_to_test=False)
                            raise LtechApiError(f"API server rejected request (HTTP 403): {self.server_url} is blocking this IP")

                    if "\r\n\r\n" in response_str:
                        body_start = response_str.find("\r\n\r\n") + 4
                        body_str = response_str[body_start:]
                    else:
                        body_str = response_str
                    
                    if "Transfer-Encoding: chunked" in response_str:
                        lines = body_str.split('\r\n')
                        json_lines = []
                        i = 0
                        while i < len(lines):
                            if lines[i] == '0' or lines[i] == '':
                                i += 1
                                continue
                            try:
                                int(lines[i], 16)
                                i += 1
                                if i < len(lines):
                                    json_lines.append(lines[i])
                            except ValueError:
                                json_lines.append(lines[i])
                            i += 1
                        body_str = '\n'.join(json_lines)
                    
                    body_str = body_str.replace('\x00', '').replace('\x01', '').replace('\x02', '').replace('\x03', '')
                    body_str = body_str.replace('\x04', '').replace('\x05', '').replace('\x06', '').replace('\x07', '')
                    body_str = body_str.replace('\x08', '').replace('\x0b', '').replace('\x0c', '').replace('\x0e', '')
                    body_str = body_str.replace('\x0f', '').replace('\x10', '').replace('\x11', '').replace('\x12', '')
                    body_str = body_str.replace('\x13', '').replace('\x14', '').replace('\x15', '').replace('\x16', '')
                    body_str = body_str.replace('\x17', '').replace('\x18', '').replace('\x19', '').replace('\x1a', '')
                    body_str = body_str.replace('\x1b', '').replace('\x1c', '').replace('\x1d', '').replace('\x1e', '')
                    body_str = body_str.replace('\x1f', '')

                    _LOGGER.debug(f"[API_RESPONSE] cleaned_body_length={len(body_str)}, first_100_chars={body_str[:100]}")

                    try:
                        result = json.loads(body_str)
                    except json.JSONDecodeError:
                        # 严格模式下，JSON 字符串中不允许出现原始控制字符（\t \n \r）
                        # 使用 strict=False 允许这些控制字符存在于字符串中
                        result = json.loads(body_str, strict=False)
                    
                    _LOGGER.info(f"[API_RESPONSE] ret={result.get('ret')}, msg={result.get('msg', '')}, data={str(result.get('data'))[:500]}")
                    
                    if result.get("ret") == 10:
                        if retry_on_auth_error:
                            _LOGGER.warning(f"[API_AUTH] Session expired, attempting re-authentication for method={method}")
                            self.login()
                            return self._send_request(method, data, timeout, retry_on_auth_error=False)
                        raise LtechAuthError("Session expired, need to re-login")
                    
                    if result.get("ret") != 0:
                        _LOGGER.error(f"[API_ERROR] method={method}, ret={result.get('ret')}, msg={result.get('msg', '')}")
                        _LOGGER.error(f"[API_ERROR] Request that failed: data={data}, session={self.session}, secret_key={self.secret_key}")
                        raise LtechApiError(f"API error: {result.get('msg', 'Unknown error')} (ret={result.get('ret')})")
                    
                    return result
                
                finally:
                    try:
                        sock.close()
                    except Exception:
                        pass
            
            except ssl.SSLError as e:
                _LOGGER.warning(f"[API_SSL_ERROR] method={method}, attempt={attempt+1}/{max_retries}, error={str(e)}")
                if attempt < max_retries - 1:
                    time.sleep(2 ** attempt)
                    continue
                raise LtechApiError(f"SSL error: {str(e)}") from e
            
            except Exception as e:
                _LOGGER.error(f"[API_REQUEST_ERROR] method={method}, attempt={attempt+1}/{max_retries}, error={str(e)}")
                if attempt < max_retries - 1:
                    time.sleep(2 ** attempt)
                    continue
                raise LtechApiError(f"Request failed: {str(e)}") from e

    def login(self):
        push_id = str(uuid.uuid4()).replace("-", "")[:32]
        
        login_data = [
            ("devicesn", push_id),
            ("devicetype", "3"),
            ("loginname", self.email),
            ("memberid", APP_ID_DEFAULT),
            ("pwd", self.password),
        ]
        
        result = self._send_request(FUN_URL_LOGIN, login_data, retry_on_auth_error=False)
        
        if isinstance(result, dict):
            data = result.get("data", result)
            self.session = data.get("session", self.session)
            self.user_id = data.get("userid")
            new_secret_key = data.get("secretkey")
            if new_secret_key:
                _LOGGER.info(f"[LOGIN] Updated secret_key: {new_secret_key[:10]}...")
                self.secret_key = new_secret_key
        
        _LOGGER.info(f"[LOGIN] Success - session={self.session[:20]}..., user_id={self.user_id}")
        return result

    def get_place_list(self):
        data = {"userId": self.user_id}
        _LOGGER.info(f"[GET_PLACE_LIST] user_id={self.user_id}")
        result = self._send_request(FUN_URL_PLACE_LIST, data)
        _LOGGER.info(f"[GET_PLACE_LIST] Response: {str(result)[:500]}")
        return result

    def get_place_info(self, place_id):
        data = {"placeid": int(place_id)}
        _LOGGER.info(f"[GET_PLACE_INFO] place_id={place_id}, data={data}")

        # 尝试多种可能的 API 方法名
        method_names = [
            "ysnetwork.base.area.place.info",
            "ysnetwork.base.com.area.place.info",
            "ysnetwork.base.area.place.getinfo",
            "ysnetwork.base.com.area.place.getinfo",
            "ysnetwork.base.area.place.detail",
            "ysnetwork.base.com.area.place.detail",
        ]

        result = None
        for method_name in method_names:
            try:
                _LOGGER.info(f"[GET_PLACE_INFO] Trying method: {method_name}")
                result = self._send_request(method_name, data)
                _LOGGER.info(f"[GET_PLACE_INFO] Method {method_name} succeeded: {str(result)[:200]}")
                break
            except Exception as e:
                _LOGGER.info(f"[GET_PLACE_INFO] Method {method_name} failed: {e}")
                continue

        if result is None:
            _LOGGER.error(f"[GET_PLACE_INFO] All methods failed for place_id={place_id}")
            return {}

        if isinstance(result, dict):
            data = result.get("data", result)
            info = data.get("info", {})
            if isinstance(info, dict):
                self.mesh_net_key = info.get("netkey")
                self.mesh_app_key = info.get("applicationkey")
                self.mesh_uuid = info.get("meshuuid")
                _LOGGER.info(f"[GET_PLACE_INFO] Keys found: netkey={'YES' if self.mesh_net_key else 'NO'}, appkey={'YES' if self.mesh_app_key else 'NO'}, meshuuid={'YES' if self.mesh_uuid else 'NO'}")

        return result

    def select_place(self, place_id):
        self.place_id = place_id

    def get_device_list(self, place_id=None):
        if place_id is None:
            place_id = self.place_id
        
        data = {"placeid": int(place_id)}
        _LOGGER.info(f"[GET_DEVICE_LIST] placeid={place_id}, type={type(place_id)}")
        return self._send_request(FUN_URL_DEVICE_LIST, data, timeout=120)

    def request_device_control(self, device_ids, platform_device_ids=None, action=None):
        deviceid_objs = [{"deviceid": d} for d in device_ids]
        
        platformdeviceid_objs = None
        if platform_device_ids:
            platformdeviceid_objs = [{"platformdeviceid": p} for p in platform_device_ids]
        
        data = {"deviceids": deviceid_objs}
        if platformdeviceid_objs:
            data["platformdeviceids"] = platformdeviceid_objs
        if action:
            data["actions"] = [action]
            _LOGGER.info(f"[REQUEST_CONTROL] Adding action to request: {action}")
        return self._send_request(FUN_URL_DEVICE_REQUEST_CONTROL, data)

    def get_device_online_status(self, device_ids):
        data = {"deviceIds": device_ids}
        return self._send_request(FUN_URL_DEVICE_ONLINE_STATUS, data)

    def control_device(self, device_id, action, platform_device_id=None):
        if platform_device_id:
            platform_device_ids = [platform_device_id]
            result = self.request_device_control([device_id], platform_device_ids, action)
        else:
            result = self.request_device_control([device_id], None, action)
        
        if isinstance(result, dict) and result.get("ret") == 0:
            _LOGGER.info(f"[CONTROL_DEVICE] Got control permission for device {device_id}, action={action}")
        elif result is not None:
            _LOGGER.warning(f"[CONTROL_DEVICE] Failed to get control permission for device {device_id}: {result}")
        else:
            _LOGGER.warning(f"[CONTROL_DEVICE] Failed to get control permission for device {device_id}: None")
        
        return result

    def control_light(self, device_id, on, brightness=None, color_temp=None, platform_device_id=None):
        action = {}
        
        if on:
            action["CharSwitch"] = "66BB0000000001EB"
        else:
            action["CharSwitch"] = "66BB0000000000EB"
        
        if brightness is not None:
            brightness_percent = int((brightness / 255) * 100)
            brightness_hex = f"{brightness_percent:02X}"
            action["CharBrightness"] = f"66BB00000001{brightness_hex}EB"
        
        if color_temp is not None:
            # color_temp is in Kelvin (e.g. 2700, 6500), convert to mireds for 66BB protocol
            temp_mired = 1000000 // color_temp
            temp_hex = f"{temp_mired:04X}"
            action["CharTemp"] = f"66BB00000002{temp_hex}EB"
        
        return self.control_device(device_id, action, platform_device_id)

    def control_switch(self, device_id, on, platform_device_id=None):
        action = {}

        if on:
            action["CharSwitch"] = "66BB0000000001EB"
        else:
            action["CharSwitch"] = "66BB0000000000EB"

        return self.control_device(device_id, action, platform_device_id)

    def control_switch_zone(self, device_id, zone_index, on, platform_device_id=None):
        action = {}
        zone_hex = f"{zone_index:02X}"
        state_hex = "01" if on else "00"
        action["CharSwitch"] = f"66BB00000000{zone_hex}{state_hex}EB"

        return self.control_device(device_id, action, platform_device_id)

    def subscribe_device(self):
        return self._send_request(FUN_URL_DEVICE_SUBSCRIBE, {})

    def sync_device_status(self, place_id):
        _LOGGER.info(f"[SYNC_STATUS] placeid={place_id}")
        result = self._send_request(FUN_URL_DEVICE_SYNC_STATUS, {"placeid": place_id})
        _LOGGER.info(f"[SYNC_STATUS] response={result}")
        return result

    def unsubscribe_device(self):
        return self._send_request(FUN_URL_DEVICE_UNSUBSCRIBE, {})

    def get_device_sync_status(self, place_id):
        data = {"placeId": place_id}
        return self._send_request(FUN_URL_DEVICE_SYNC_STATUS, data)

    def bind_user(self):
        _LOGGER.info(f"[BIND_USER] Calling bind_user API")
        result = self._send_request(FUN_URL_BIND_USER, {})
        _LOGGER.info(f"[BIND_USER] Result: {result}")
        
        if result and isinstance(result, dict):
            data = result.get("data", result)
            if 'param' in data:
                try:
                    param = json.loads(data['param'])
                    self.product_key = param.get('productKey')
                    self.device_name = param.get('deviceName')
                    self.device_secret = param.get('deviceSecret')
                    _LOGGER.info(f"[BIND_USER] Parsed credentials: product_key={self.product_key}, device_name={self.device_name}, device_secret={self.device_secret[:10]}..." if self.device_secret else None)
                    return param
                except json.JSONDecodeError as e:
                    _LOGGER.error(f"[BIND_USER] Failed to parse param JSON: {e}, param={data['param']}")
            else:
                _LOGGER.error(f"[BIND_USER] No 'param' field in result data")
        else:
            _LOGGER.error(f"[BIND_USER] Invalid result: {result}")
        
        return None

    def generate_mqtt_password(self, timestamp=None):
        if not self.product_key or not self.device_name or not self.device_secret:
            return None
        
        if timestamp is None:
            timestamp = str(int(time.time()))
        
        client_id_base = f"{self.product_key}&{self.device_name}"
        client_id = f"{client_id_base}|securemode=3,signmethod=hmacsha1,ext=1,_ss=1,lan=Python,_v=1.2.13,timestamp={timestamp}|"
        sign_content = f"clientId{client_id_base}deviceName{self.device_name}productKey{self.product_key}timestamp{timestamp}"
        
        password = hmac.new(
            self.device_secret.encode('utf-8'),
            sign_content.encode('utf-8'),
            hashlib.sha1
        ).hexdigest()
        
        return {
            "broker": MQTT_BROKER_CN,
            "port": 1883,
            "client_id": client_id,
            "username": f"{self.device_name}&{self.product_key}",
            "password": password,
            "timestamp": timestamp,
            "topic": f"/{self.product_key}/{self.device_name}/user/get"
        }