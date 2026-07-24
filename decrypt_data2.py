import base64
import urllib.parse
from Crypto.Cipher import AES
from Crypto.Util.Padding import unpad

SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"

success_data = "UzALKFH-qGUCCyqOPnx_dOVrVq1KKKRg5ZTzVLHDeNTnn46DU7g6MRtn2hLjKmbTGfb8-Li4veUhr03x0cl4MRIc56ywUR9pX0TU47e_FnWK1HHaMQNvzIiTYxc9T1dBWaeBbO3Eh1tQ18OABDg4uMC63uNLulQjB00_tlWXpp5fm9Sq-TQg3gW1l-pDNZH9"

decoded_data = urllib.parse.unquote(success_data)
print(f"URL decoded: {decoded_data}")

key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
cipher = AES.new(key_bytes, AES.MODE_ECB)

encrypted_bytes = base64.b64decode(decoded_data)
decrypted = cipher.decrypt(encrypted_bytes)

try:
    unpadded = unpad(decrypted, AES.block_size)
    print(f"\nDecrypted data: {unpadded.decode('utf-8')}")
except Exception as e:
    print(f"\nError: {e}")
    print(f"Raw decrypted (hex): {decrypted.hex()}")