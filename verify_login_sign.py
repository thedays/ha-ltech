import base64
import hashlib
import json
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad

SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
APP_ID_DEFAULT = 119011416470103

har_login_data = "UzALKFH-qGUCCyqOPnx_dOVrVq1KKKRg5ZTzVLHDeNTnn46DU7g6MRtn2hLjKmbTGfb8-Li4veUhr03x0cl4MRIc56ywUR9pX0TU47e_FnWK1HHaMQNvzIiTYxc9T1dBWaeBbO3Eh1tQ18OABDg4uMC63uNLulQjB00_tlWXpp5fm9Sq-TQg3gW1l-pDNZH9"
har_login_method = "ysnetwork.base.com.user.app.login"
har_login_session = "cqq577bze26adn1wbt5uz0nwk7zlfs03"
har_login_timestamp = "1783739683"
har_login_sign = "b483018e08bdfed0b20c327f5aca7886"

key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
iv_bytes = key_bytes[:AES.block_size]
cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)

fixed_data = har_login_data.replace('-', '+').replace('_', '/')
encrypted_bytes = base64.b64decode(fixed_data)
decrypted = cipher.decrypt(encrypted_bytes)
unpadded = unpad(decrypted, AES.block_size)
print(f"Decrypted login data: {unpadded.decode('utf-8')}")

sign_str = (
    f"{SECRET_KEY_DEFAULT}"
    f"{APP_ID_DEFAULT}"
    f"{har_login_data}"
    f"json"
    f"{har_login_method}"
    f"{har_login_session}"
    f"{har_login_timestamp}"
    f"2.0"
    f"{SECRET_KEY_DEFAULT}"
)

calculated_sign = hashlib.md5(sign_str.encode("utf-8")).hexdigest().lower()
print(f"\nHAR sign: {har_login_sign}")
print(f"Calculated sign: {calculated_sign}")
print(f"Sign match: {har_login_sign == calculated_sign}")