import json
import logging
import ssl
import sys
import time

try:
    import paho.mqtt.client as mqtt
    import requests
    from Crypto.Cipher import AES
    from Crypto.Util.Padding import pad
    import hashlib
    import hmac
    import base64
    MQTT_AVAILABLE = True
except ImportError as e:
    print(f"Import error: {e}")
    MQTT_AVAILABLE = False

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s - %(levelname)s - %(message)s')
_LOGGER = logging.getLogger(__name__)

APP_ID_DEFAULT = 119011416470103
SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
SESSION_DEFAULT = "cqq577bze26adn1wbt5uz0nwk7zlfs03"
REST_URL = "openapi/rest"
DEFAULT_SERVER_URL = "https://apic.ltsys.com.cn:2443/"
MQTT_BROKER_CN = "iot-060a5shm.mqtt.iothub.aliyuncs.com"
MQTT_PORT_CN = 8883

FUN_URL_LOGIN = "ysnetwork.base.com.user.app.login"
FUN_URL_BIND_USER = "ysnetwork.base.com.deviceparam.binduser"


class LtechApiClient:
    def __init__(self, server_url, email=None, password=None):
        self.server_url = server_url
        self.email = email
        self.password = password
        self.session = SESSION_DEFAULT
        self.app_id = APP_ID_DEFAULT
        self.secret_key = SECRET_KEY_DEFAULT
        self.user_id = None
        self.device_name = None
        self.device_secret = None
        self.product_key = None

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
        
        try:
            response = requests.post(url, json=payload, verify=False, timeout=30)
            response.raise_for_status()
            result = response.json()
            
            if result.get("ret") != 0:
                raise Exception(f"API error: {result.get('msg', 'Unknown error')} (ret={result.get('ret')})")
            
            return result.get("data", result.get("message"))
        
        except requests.exceptions.RequestException as e:
            raise Exception(f"Request failed: {str(e)}") from e

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
            self.secret_key = result.get("secretkey", self.secret_key)
            self.user_id = result.get("userid")
            self.device_name = result.get("deviceName")
            self.device_secret = result.get("deviceSecret")
            self.product_key = result.get("productKey")
        
        return result

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
            "port": MQTT_PORT_CN,
            "client_id": client_id,
            "username": f"{self.device_name}&{self.product_key}",
            "password": base64.b64encode(password).decode('utf-8'),
            "timestamp": timestamp,
            "topic": f"/{self.product_key}/{self.device_name}/user/get"
        }


def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("✅ MQTT connected successfully")
        topic = userdata.get("topic")
        client.subscribe(topic)
        print(f"✅ Subscribed to topic: {topic}")
    else:
        print(f"❌ MQTT connection failed with code {rc}")
        print(f"   Error codes: 0=Success, 1=Protocol version, 2=Invalid client ID")
        print(f"                3=Server unavailable, 4=Bad username/password")
        print(f"                5=Not authorized")


def on_message(client, userdata, msg):
    try:
        payload = msg.payload.decode("utf-8")
        print(f"\n📩 MQTT message received:")
        print(f"   Topic: {msg.topic}")
        print(f"   Payload: {payload}")
        
        try:
            data = json.loads(payload)
            if isinstance(data, dict):
                device_name = data.get("devicename")
                payload_data = data.get("payload")
                print(f"   Device: {device_name}")
                print(f"   Payload (hex): {payload_data}")
        except json.JSONDecodeError:
            pass
            
    except Exception as e:
        print(f"❌ Failed to process MQTT message: {e}")


def on_disconnect(client, userdata, rc):
    print(f"\n⚠️ MQTT disconnected with code {rc}")


def on_log(client, userdata, level, buf):
    print(f"📝 MQTT log: {buf}")


def test_mqtt(email=None, password=None):
    if not MQTT_AVAILABLE:
        print("❌ paho-mqtt not installed. Please install with: pip install paho-mqtt")
        return

    if email is None or password is None:
        if len(sys.argv) >= 3:
            email = sys.argv[1]
            password = sys.argv[2]
        else:
            print("Usage: python test_mqtt.py <email> <password>")
            return

    api = LtechApiClient(DEFAULT_SERVER_URL, email, password)

    try:
        print("\n" + "="*60)
        print("Step 1: Login to Ltech API")
        print("="*60)
        result = api.login()
        print(f"✅ Login successful!")
        print(f"   User ID: {api.user_id}")
        print(f"   Session: {api.session[:20]}...")
        print(f"   Secret Key: {api.secret_key}")

        print("\n" + "="*60)
        print("Step 2: Bind User for MQTT credentials")
        print("="*60)
        bind_result = api.bind_user()
        print(f"✅ Bind user result: {bind_result}")
        print(f"   Product Key: {api.product_key}")
        print(f"   Device Name: {api.device_name}")
        print(f"   Device Secret: {api.device_secret}")

        print("\n" + "="*60)
        print("Step 3: Generate MQTT password")
        print("="*60)
        mqtt_config = api.generate_mqtt_password()
        if mqtt_config:
            print(f"✅ MQTT config generated successfully!")
            print(f"   Broker: {mqtt_config['broker']}")
            print(f"   Port: {mqtt_config['port']}")
            print(f"   Client ID: {mqtt_config['client_id']}")
            print(f"   Username: {mqtt_config['username']}")
            print(f"   Password: {mqtt_config['password'][:30]}...")
            print(f"   Topic: {mqtt_config['topic']}")
        else:
            print("❌ Failed to generate MQTT password")
            return

        print("\n" + "="*60)
        print("Step 4: Connect to MQTT broker")
        print("="*60)
        client = mqtt.Client(
            client_id=mqtt_config["client_id"],
            protocol=mqtt.MQTTv311
        )
        client.username_pw_set(
            mqtt_config["username"],
            mqtt_config["password"]
        )

        ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
        ssl_context.minimum_version = ssl.TLSVersion.TLSv1_2
        ssl_context.check_hostname = False
        ssl_context.verify_mode = ssl.CERT_NONE
        client.tls_set_context(ssl_context)
        client.tls_insecure_set(True)

        client.user_data_set({"topic": mqtt_config["topic"]})
        client.on_connect = on_connect
        client.on_message = on_message
        client.on_disconnect = on_disconnect
        client.on_log = on_log

        print(f"🔌 Connecting to {mqtt_config['broker']}:{mqtt_config['port']}...")
        try:
            client.connect(
                mqtt_config["broker"],
                mqtt_config["port"],
                60
            )
            client.loop_start()
            
            print("\n⏳ Waiting for messages (press Ctrl+C to exit)...")
            while True:
                time.sleep(1)
                if not client.is_connected():
                    print("❌ MQTT connection lost")
                    break

        except KeyboardInterrupt:
            print("\n👋 Exiting...")
        except Exception as e:
            print(f"❌ MQTT connection failed: {e}")
            import traceback
            traceback.print_exc()
        finally:
            client.loop_stop()
            client.disconnect()
            print("✅ MQTT disconnected")

    except Exception as e:
        print(f"\n❌ Error: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    test_mqtt()