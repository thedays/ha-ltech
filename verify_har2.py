import base64
import hashlib
import json
from Crypto.Cipher import AES
from Crypto.Util.Padding import unpad

SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
APP_ID_DEFAULT = 119011416470103

har_data = "NuYaMwhdtY0qbDRZwTtySwWuhlOYGbY9inQn6otkXHfnm00fSUzBMs-cS-FtvAibxtvvfBk_P32b6_WmeEZv9w.."
har_method = "ysnetwork.base.com.leite.app.mcu.deviceversion.list"
har_session = "q0ffb073i97cltnh6aqutm7dxc98zksv"
har_timestamp = "1783739684"
har_sign = "34951fb68babb9d71e472b9df77a29d2"

sign_str = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{har_data}"
    f"json"
    f"{har_method}"
    f"{har_session}"
    f"{har_timestamp}"
    f"2.0"
    f"{SECRET_KEY_DEFAULT}"
)

calculated_sign = hashlib.md5(sign_str.encode("utf-8")).hexdigest().lower()
print(f"HAR sign: {har_sign}")
print(f"Calculated sign: {calculated_sign}")
print(f"Sign match: {har_sign == calculated_sign}")

print(f"\nSign string length: {len(sign_str)}")

fixed_data = har_data.replace('-', '+').replace('_', '/')
key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
iv_bytes = key_bytes[:AES.block_size]
cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)

try:
    encrypted_bytes = base64.b64decode(fixed_data)
    decrypted = cipher.decrypt(encrypted_bytes)
    print(f"\nRaw decrypted: {decrypted}")
    print(f"Raw decrypted hex: {decrypted.hex()}")
except Exception as e:
    print(f"\nBase64 decode error: {e}")

test_data = json.dumps({}, separators=(',', ':'))
print(f"\nEmpty object JSON: {test_data}")

padded = unpad(cipher.decrypt(base64.b64decode(fixed_data)), AES.block_size)
print(f"Decrypted: {padded.decode('utf-8')}")