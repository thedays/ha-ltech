import json
import logging
import time

_LOGGER = logging.getLogger(__name__)

try:
    from linkkit import linkkit
    LINKKIT_AVAILABLE = True
except ImportError as e:
    LINKKIT_AVAILABLE = False
    _LOGGER.warning(f"aliyun-iot-linkkit import failed: {e}. MQTT push will be disabled, falling back to polling.")
    _LOGGER.warning("To enable MQTT push, install manually: pip install aliyun-iot-linkkit paho-mqtt==1.6.1")


class LtechMqttClient:
    def __init__(self, api_client, on_message_callback=None):
        self.api_client = api_client
        self.on_message_callback = on_message_callback
        self.lk = None
        self.connected = False

    def connect(self):
        if not LINKKIT_AVAILABLE:
            _LOGGER.warning("aliyun-iot-linkkit not installed, MQTT support disabled")
            return False

        if not self.api_client.product_key:
            bind_result = self.api_client.bind_user()
            if not bind_result:
                _LOGGER.error("Failed to bind user for MQTT credentials")
                return False

        try:
            self.lk = linkkit.LinkKit(
                host_name="cn-shanghai",
                product_key=self.api_client.product_key,
                device_name=self.api_client.device_name,
                device_secret=self.api_client.device_secret
            )

            self.lk.config_mqtt(
                endpoint=self.api_client.mqtt_broker,
                port=8883,
                transport="TCP",
                secure="TLS",
                keep_alive=60
            )

            self.lk.on_connect = self._on_connect
            self.lk.on_disconnect = self._on_disconnect
            self.lk.on_message = self._on_message

            _LOGGER.info(f"Connecting to Aliyun IoT via LinkKit")
            self.lk.connect_async()

            time.sleep(3)

            if self.connected:
                _LOGGER.info("MQTT connected successfully via LinkKit")
                return True
            else:
                _LOGGER.warning("MQTT connection failed via LinkKit")
                return False

        except Exception as e:
            _LOGGER.error(f"LinkKit connection failed: {e}")
            return False

    def disconnect(self):
        if self.lk:
            try:
                self.lk.disconnect()
                self.connected = False
                _LOGGER.info("MQTT disconnected")
            except Exception as e:
                _LOGGER.error(f"MQTT disconnect failed: {e}")

    def _on_connect(self, session_flag, rc, userdata):
        if rc == 0:
            self.connected = True
            _LOGGER.info("LinkKit MQTT connected successfully")
        else:
            _LOGGER.error(f"LinkKit MQTT connection failed with code {rc}")

    def _on_disconnect(self, session_flag, rc, userdata):
        self.connected = False
        _LOGGER.warning(f"LinkKit MQTT disconnected with code {rc}")

    def _on_message(self, session_flag, topic, payload, qos, userdata):
        try:
            message = payload.decode("utf-8")
            _LOGGER.debug(f"LinkKit MQTT message received: {topic} -> {message}")

            if self.on_message_callback:
                self.on_message_callback(message)

        except Exception as e:
            _LOGGER.error(f"Failed to process LinkKit MQTT message: {e}")

    def is_connected(self):
        return self.connected