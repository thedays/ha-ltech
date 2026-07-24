import base64
import hashlib
import hmac
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

MQTT_BROKER_CN = "iot-060a5shm.mqtt.iothub.aliyuncs.com"
MQTT_BROKER_GLOBAL = "iot-600a65gi.mqtt.iothub.aliyuncs.com"

class LtechApiClient:
    def __init__(self):
        self.session = SESSION_DEFAULT
        self.app_id = APP_ID_DEFAULT
        self.secret_key = SECRET_KEY_DEFAULT
        self.user_id = None
        self.product_key = None
        self.device_name = None
        self.device_secret = None
        self.iot_id = None

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
        result = self._send_request(FUN_URL_BIND_USER, {})
        if result and isinstance(result, dict) and 'param' in result:
            try:
                param = json.loads(result['param'])
                self.product_key = param.get('productKey')
                self.device_name = param.get('deviceName')
                self.device_secret = param.get('deviceSecret')
                self.iot_id = param.get('iotId')
                return param
            except json.JSONDecodeError:
                pass
        return result

    def generate_mqtt_password(self):
        if not self.product_key or not self.device_name or not self.device_secret:
            return None
        
        timestamp = str(int(time.time() * 1000))
        sign_content = f"clientId{self.product_key}@@@{self.device_name}deviceName{self.device_name}productKey{self.product_key}timestamp{timestamp}"
        
        password = hmac.new(
            self.device_secret.encode('utf-8'),
            sign_content.encode('utf-8'),
            hashlib.sha1
        ).digest()
        
        return base64.b64encode(password).decode('utf-8')

def test_api(account, password):
    client = LtechApiClient()
    
    print(f"\n{'='*80}")
    print(f"Step 1: Login")
    print(f"{'='*80}")
    login_result = client.login(account, password)
    
    if not login_result:
        return
    
    print(f"✅ Login SUCCESS")
    
    print(f"\n{'='*80}")
    print(f"Step 2: Bind User (获取MQTT凭证)")
    print(f"{'='*80}")
    bind_result = client.bind_user()
    if bind_result is not None:
        print(f"✅ Bind User SUCCESS")
        print(f"  - productKey: {client.product_key}")
        print(f"  - deviceName: {client.device_name}")
        print(f"  - deviceSecret: {client.device_secret}")
        print(f"  - iotId: {client.iot_id}")
        
        print(f"\n{'='*60}")
        print(f"阿里云IoT MQTT连接信息:")
        print(f"{'='*60}")
        print(f"Broker (中国区): {MQTT_BROKER_CN}")
        print(f"Broker (国际区): {MQTT_BROKER_GLOBAL}")
        print(f"Port: 1883")
        print(f"Client ID: {client.product_key}@@@{client.device_name}")
        print(f"Username: {client.device_name}&{client.product_key}")
        
        password = client.generate_mqtt_password()
        if password:
            print(f"Password: {password}")
            
            print(f"\n{'='*60}")
            print(f"测试MQTT连接 (中国区Broker)...")
            print(f"{'='*60}")
            
            try:
                import paho.mqtt.client as mqtt
                
                broker = MQTT_BROKER_CN
                port = 1883
                client_id = f"{client.product_key}@@@{client.device_name}"
                username = f"{client.device_name}&{client.product_key}"
                
                mqtt_client = mqtt.Client(client_id=client_id, protocol=mqtt.MQTTv311)
                mqtt_client.username_pw_set(username, password)
                
                connected_event = False
                
                def on_connect(client, userdata, flags, rc):
                    nonlocal connected_event
                    connected_event = True
                    print(f"✅ MQTT连接成功! rc={rc}")
                    topic = f"/{client.product_key}/{client.device_name}/user/get"
                    client.subscribe(topic)
                    print(f"✅ 已订阅主题: {topic}")
                
                def on_message(client, userdata, msg):
                    print(f"\n📩 收到消息:")
                    print(f"  Topic: {msg.topic}")
                    print(f"  Payload: {msg.payload.decode('utf-8')}")
                
                def on_disconnect(client, userdata, rc):
                    print(f"❌ MQTT断开连接, rc={rc}")
                
                def on_log(client, userdata, level, buf):
                    print(f"🔍 Log: {buf}")
                
                mqtt_client.on_connect = on_connect
                mqtt_client.on_message = on_message
                mqtt_client.on_disconnect = on_disconnect
                mqtt_client.on_log = on_log
                
                mqtt_client.connect(broker, port, 60)
                mqtt_client.loop_start()
                
                print(f"MQTT客户端已启动, 等待消息...")
                time.sleep(15)
                
                if not connected_event:
                    print("❌ MQTT连接失败")
                
                mqtt_client.loop_stop()
                mqtt_client.disconnect()
                print("\n✅ MQTT测试完成")
                
            except ImportError:
                print("需要安装paho-mqtt: pip install paho-mqtt")
            except Exception as e:
                print(f"MQTT连接失败: {e}")

if __name__ == "__main__":
    if len(sys.argv) >= 3:
        test_api(sys.argv[1], sys.argv[2])
    else:
        print("Usage: python test_mqtt_bind_user3.py <account> <password>")