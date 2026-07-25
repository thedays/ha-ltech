import json
import logging
import ssl
import time
import hashlib
import hmac
import base64

_LOGGER = logging.getLogger(__name__)

try:
    import paho.mqtt.client as mqtt
    MQTT_AVAILABLE = True
except ImportError as e:
    MQTT_AVAILABLE = False
    _LOGGER.warning(f"paho-mqtt import failed: {e}. MQTT push will be disabled, falling back to polling.")


class LtechMqttClient:
    def __init__(self, api_client, on_message_callback=None):
        self.api_client = api_client
        self.on_message_callback = on_message_callback
        self.client = None
        self.connected = False
        self.topic = None

    def _generate_password(self):
        if not all([self.api_client.product_key, self.api_client.device_name, self.api_client.device_secret]):
            return None
        
        timestamp = str(int(time.time()))
        client_id_base = f"{self.api_client.product_key}&{self.api_client.device_name}"
        client_id = f"{client_id_base}|securemode=3,signmethod=hmacsha1,ext=1,_ss=1,lan=Python,_v=1.2.13,timestamp={timestamp}|"
        sign_content = f"clientId{client_id_base}deviceName{self.api_client.device_name}productKey{self.api_client.product_key}timestamp{timestamp}"
        
        password = hmac.new(
            self.api_client.device_secret.encode('utf-8'),
            sign_content.encode('utf-8'),
            hashlib.sha1
        ).hexdigest()
        
        return {
            "client_id": client_id,
            "username": f"{self.api_client.device_name}&{self.api_client.product_key}",
            "password": password,
            "timestamp": timestamp
        }

    def connect(self):
        if not MQTT_AVAILABLE:
            _LOGGER.warning("paho-mqtt not installed, MQTT support disabled")
            return False

        if not self.api_client.product_key:
            _LOGGER.info("[MQTT] product_key not set, calling bind_user")
            bind_result = self.api_client.bind_user()
            if not bind_result:
                _LOGGER.error("Failed to bind user for MQTT credentials")
                return False

        _LOGGER.info(f"[MQTT] Credentials: product_key={self.api_client.product_key}, device_name={self.api_client.device_name}, device_secret={self.api_client.device_secret[:10]}..." if self.api_client.device_secret else f"[MQTT] Credentials: product_key={self.api_client.product_key}, device_name={self.api_client.device_name}, device_secret=None")
        
        if not all([self.api_client.product_key, self.api_client.device_name, self.api_client.device_secret]):
            _LOGGER.error("[MQTT] Missing credentials: product_key=%s, device_name=%s, device_secret=%s", 
                         self.api_client.product_key, self.api_client.device_name, "set" if self.api_client.device_secret else "not set")
            return False

        try:
            mqtt_config = self._generate_password()
            if not mqtt_config:
                _LOGGER.error("[MQTT] Failed to generate MQTT password")
                return False

            self.topic = f"/{self.api_client.product_key}/{self.api_client.device_name}/user/get"
            
            _LOGGER.info(f"[MQTT] Creating paho-mqtt client: broker={self.api_client.mqtt_broker}, port=1883")
            self.client = mqtt.Client(
                client_id=mqtt_config["client_id"],
                protocol=mqtt.MQTTv311
            )
            self.client.username_pw_set(
                mqtt_config["username"],
                mqtt_config["password"]
            )
            
            _LOGGER.info(f"[MQTT] MQTT config: client_id={mqtt_config['client_id']}, username={mqtt_config['username']}, topic={self.topic}")

            self.client.on_connect = self._on_connect
            self.client.on_disconnect = self._on_disconnect
            self.client.on_message = self._on_message
            _LOGGER.info("[MQTT] Callbacks registered")

            _LOGGER.info(f"[MQTT] Connecting to {self.api_client.mqtt_broker}:1883")
            rc = self.client.connect(
                self.api_client.mqtt_broker,
                1883,
                60
            )
            
            if rc != 0:
                _LOGGER.error(f"[MQTT] Connection failed with code {rc}")
                _LOGGER.error(f"[MQTT] Error codes: 0=Success, 1=Protocol version, 2=Invalid client ID")
                _LOGGER.error(f"[MQTT]              3=Server unavailable, 4=Bad username/password")
                _LOGGER.error(f"[MQTT]              5=Not authorized")
                return False

            _LOGGER.info("[MQTT] Connected successfully (sync)")
            
            result, mid = self.client.subscribe(self.topic, 0)
            if result == 0:
                _LOGGER.info(f"[MQTT] Subscribed to topic: {self.topic}, mid={mid}")
            else:
                _LOGGER.error(f"[MQTT] Subscribe failed with result {result}")

            self.client.loop_start()
            _LOGGER.info("[MQTT] Background loop started")
            
            return True

        except Exception as e:
            _LOGGER.error(f"[MQTT] Connection failed: {e}")
            import traceback
            _LOGGER.error(f"[MQTT] Connection traceback: {traceback.format_exc()}")
            return False

    def disconnect(self):
        if self.client:
            try:
                self.client.loop_stop()
                self.client.disconnect()
                self.connected = False
                _LOGGER.info("[MQTT] Disconnected")
            except Exception as e:
                _LOGGER.error(f"[MQTT] Disconnect failed: {e}")

    def _on_connect(self, client, userdata, flags, rc):
        if rc == 0:
            self.connected = True
            _LOGGER.info("[MQTT] Connected successfully")
        else:
            _LOGGER.error(f"[MQTT] Connection failed with code {rc}")

    def _on_disconnect(self, client, userdata, rc):
        self.connected = False
        if rc == 0:
            _LOGGER.info("[MQTT] Disconnected")
        else:
            _LOGGER.warning(f"[MQTT] Unexpected disconnection with code {rc}")

    def _on_message(self, client, userdata, msg):
        try:
            message = msg.payload.decode("utf-8")
            _LOGGER.info(f"[MQTT] Message received: {msg.topic} -> {message[:200]}")

            if self.on_message_callback:
                self.on_message_callback(message)

        except Exception as e:
            _LOGGER.error(f"[MQTT] Failed to process message: {e}")

    def is_connected(self):
        return self.connected