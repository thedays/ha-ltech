DOMAIN = "ltech"
VERSION = "2.3.26"
CONF_ACCOUNT = "account"
CONF_PASSWORD = "password"
CONF_SERVER_URL = "server_url"

DEFAULT_SERVER_URL = "https://apic.ltsys.com.cn:2443/"
TEST_SERVER_URL = "https://ltsit.ltechcloud.cn/"

APP_ID_DEFAULT = 119011416470103
SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
SESSION_DEFAULT = "cqq577bze26adn1wbt5uz0nwk7zlfs03"

REST_URL = "openapi/rest"
FUN_URL_BASE = "ysnetwork.base.com"

FUN_URL_LOGIN = f"{FUN_URL_BASE}.user.app.login"
FUN_URL_DEVICE_LIST = "ysnetwork.base.area.device.list"
FUN_URL_DEVICE_CONTROL = "ysnetwork.base.com.app.device.requestcontrol"
FUN_URL_DEVICE_REQUEST_CONTROL = "ysnetwork.base.com.app.device.requestcontrol"
FUN_URL_DEVICE_ONLINE_STATUS = f"{FUN_URL_BASE}.app.device.getstatus"
FUN_URL_DEVICE_SYNC_STATUS = f"{FUN_URL_BASE}.app.device.syncstatus"
FUN_URL_DEVICE_SUBSCRIBE = f"{FUN_URL_BASE}.app.device.subscribe"
FUN_URL_DEVICE_UNSUBSCRIBE = f"{FUN_URL_BASE}.app.device.unsubscribe"
FUN_URL_PLACE_LIST = "ysnetwork.base.com.area.place.user.list"
FUN_URL_PLACE_INFO = f"{FUN_URL_BASE}.area.place.info"
FUN_URL_BIND_USER = f"{FUN_URL_BASE}.deviceparam.binduser"

MQTT_BROKER_CN = "iot-060a5shm.mqtt.iothub.aliyuncs.com"
MQTT_BROKER_GLOBAL = "iot-600a65gi.mqtt.iothub.aliyuncs.com"

LOGIN_SUCCESS = 0
LOGIN_FAILED = 10

DEVICE_TYPE_LIGHT = ["LIGHT", "DALI_LIGHT", "CGD_PRO_LIGHT"]
DEVICE_TYPE_SWITCH = ["SWITCH", "RELAY", "SOCKET"]
DEVICE_TYPE_SENSOR = ["TEMP_SENSOR", "HUMIDITY_SENSOR", "PIR_SENSOR", "DOOR_SENSOR"]
DEVICE_TYPE_AC = ["AIR_CONDITIONER"]
DEVICE_TYPE_CURTAIN = ["CURTAIN", "RS8_CURTAIN"]
DEVICE_TYPE_FAN = ["FAN"]
DEVICE_TYPE_IR = ["IR_DEVICE"]

LIGHT_PRODUCT_IDS = [
    "LIGHT_CT",
    "LIGHT_DIM",
    "LIGHT_COLOR",
    "LIGHT_DUV",
    "LIGHT_512",
    "CGD_PRO_LIGHT",
    "DALI_LIGHT",
]

SWITCH_PRODUCT_IDS = [
    "SWITCH_1G",
    "SWITCH_2G",
    "SWITCH_3G",
    "SWITCH_4G",
    "RELAY_1CH",
    "RELAY_2CH",
    "RELAY_4CH",
    "SOCKET",
]

SENSOR_PRODUCT_IDS = [
    "HSDSENSOR_TEMP",
    "HSDSENSOR_HUMI",
    "HSDSENSOR_PIR",
    "HSDSENSOR_DOOR",
    "HSDSENSOR_BATTERY",
]

ACTUATOR_PRODUCT_IDS = LIGHT_PRODUCT_IDS + SWITCH_PRODUCT_IDS