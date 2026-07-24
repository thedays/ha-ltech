import base64
from Crypto.Cipher import AES
from Crypto.Util.Padding import unpad

SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"

success_data = "UzALKFH-qGUCCyqOPnx_dOVrVq1KKKRg5ZTzVLHDeNTnn46DU7g6MRtn2hLjKmbTGfb8-Li4veUhr03x0cl4MRIc56ywUR9pX0TU47e_FnWK1HHaMQNvzIiTYxc9T1dBWaeBbO3Eh1tQ18OABDg4uMC63uNLulQjB00_tlWXpp5fm9Sq-TQg3gW1l-pDNZH9"

fixed_data = success_data.replace('-', '+').replace('_', '/')
padding_needed = (4 - len(fixed_data) % 4) % 4
fixed_data += '=' * padding_needed

print(f"Fixed data: {fixed_data}")
print(f"Length: {len(fixed_data)}")

key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")

print("\n=== ECB mode ===")
cipher_ecb = AES.new(key_bytes, AES.MODE_ECB)
encrypted_bytes = base64.b64decode(fixed_data)
decrypted_ecb = cipher_ecb.decrypt(encrypted_bytes)

try:
    unpadded = unpad(decrypted_ecb, AES.block_size)
    print(f"Decrypted (ECB): {unpadded.decode('utf-8')}")
except Exception as e:
    print(f"ECB Error: {e}")
    print(f"Raw decrypted (ECB): {decrypted_ecb}")

print("\n=== CBC mode ===")
iv_bytes = key_bytes[:AES.block_size]
cipher_cbc = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
decrypted_cbc = cipher_cbc.decrypt(encrypted_bytes)

try:
    unpadded_cbc = unpad(decrypted_cbc, AES.block_size)
    print(f"Decrypted (CBC): {unpadded_cbc.decode('utf-8')}")
except Exception as e:
    print(f"CBC Error: {e}")
    print(f"Raw decrypted (CBC): {decrypted_cbc}")