import hashlib
import json
import time
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad

APP_ID_DEFAULT = 119011416470103
SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
SESSION_DEFAULT = "cqq577bze26adn1wbt5uz0nwk7zlfs03"
FUN_URL_LOGIN = "ysnetwork.base.com.user.app.login"

login_data = {
    "devicesn": "",
    "devicetype": "3",
    "loginname": "15019860713",
    "memberid": APP_ID_DEFAULT,
    "pwd": "Kaggie00",
}

login_json = json.dumps(login_data, separators=(',', ':'))
print(f"Login JSON: {login_json}")
print(f"Login JSON length: {len(login_json)}")

key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
print(f"\nKey: {SECRET_KEY_DEFAULT}")
print(f"Key bytes: {key_bytes.hex()}")
print(f"Key length: {len(key_bytes)}")

print("\n" + "=" * 80)
print("Test ECB mode")
print("=" * 80)
cipher_ecb = AES.new(key_bytes, AES.MODE_ECB)
padded_ecb = pad(login_json.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted_ecb = cipher_ecb.encrypt(padded_ecb)
ecb_hex = encrypted_ecb.hex().upper()
print(f"Encrypted (ECB): {ecb_hex}")
print(f"Length: {len(ecb_hex)}")

print("\n" + "=" * 80)
print("Test CBC mode (key as IV)")
print("=" * 80)
iv_bytes = key_bytes[:AES.block_size]
cipher_cbc = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
padded_cbc = pad(login_json.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted_cbc = cipher_cbc.encrypt(padded_cbc)
cbc_hex = encrypted_cbc.hex().upper()
print(f"IV: {iv_bytes.hex()}")
print(f"Encrypted (CBC): {cbc_hex}")
print(f"Length: {len(cbc_hex)}")

print("\n" + "=" * 80)
print("Sign calculation for both modes")
print("=" * 80)

timestamp = "1783738675995"
format_val = "json"
v_val = "2.0"

sign_str_ecb = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{ecb_hex}"
    f"{format_val}"
    f"{FUN_URL_LOGIN}"
    f"{SESSION_DEFAULT}"
    f"{timestamp}"
    f"{v_val}"
    f"{SECRET_KEY_DEFAULT}"
)

sign_str_cbc = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{cbc_hex}"
    f"{format_val}"
    f"{FUN_URL_LOGIN}"
    f"{SESSION_DEFAULT}"
    f"{timestamp}"
    f"{v_val}"
    f"{SECRET_KEY_DEFAULT}"
)

print(f"\nECB sign string length: {len(sign_str_ecb)}")
print(f"CBC sign string length: {len(sign_str_cbc)}")

sign_ecb = hashlib.md5(sign_str_ecb.encode("utf-8")).hexdigest().upper()
sign_cbc = hashlib.md5(sign_str_cbc.encode("utf-8")).hexdigest().upper()

print(f"\nECB sign: {sign_ecb}")
print(f"CBC sign: {sign_cbc}")

print("\n" + "=" * 80)
print("Try different sign string order")
print("=" * 80)

sign_str_order2 = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{ecb_hex}"
    f"{FUN_URL_LOGIN}"
    f"{format_val}"
    f"{SESSION_DEFAULT}"
    f"{timestamp}"
    f"{v_val}"
    f"{SECRET_KEY_DEFAULT}"
)
sign_order2 = hashlib.md5(sign_str_order2.encode("utf-8")).hexdigest().upper()
print(f"Order: secretKey + appid + data + method + format + session + timestamp + v + secretKey")
print(f"Sign: {sign_order2}")

sign_str_order3 = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{ecb_hex}"
    f"{format_val}"
    f"{SESSION_DEFAULT}"
    f"{FUN_URL_LOGIN}"
    f"{timestamp}"
    f"{v_val}"
    f"{SECRET_KEY_DEFAULT}"
)
sign_order3 = hashlib.md5(sign_str_order3.encode("utf-8")).hexdigest().upper()
print(f"\nOrder: secretKey + appid + data + format + session + method + timestamp + v + secretKey")
print(f"Sign: {sign_order3}")

sign_str_order4 = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{ecb_hex}"
    f"{format_val}"
    f"{FUN_URL_LOGIN}"
    f"{timestamp}"
    f"{SESSION_DEFAULT}"
    f"{v_val}"
    f"{SECRET_KEY_DEFAULT}"
)
sign_order4 = hashlib.md5(sign_str_order4.encode("utf-8")).hexdigest().upper()
print(f"\nOrder: secretKey + appid + data + format + method + timestamp + session + v + secretKey")
print(f"Sign: {sign_order4}")