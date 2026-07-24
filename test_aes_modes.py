import hashlib
import json
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad

SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
TEST_DATA = '{"devicesn":"","devicetype":"3","loginname":"test@test.com","memberid":119011416470103,"pwd":"test123"}'

print("=" * 80)
print("AES Encryption Test - Multiple modes")
print("=" * 80)
print(f"\nTest data: {TEST_DATA}")
print(f"Test data length: {len(TEST_DATA)}")

key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
print(f"\nKey: {SECRET_KEY_DEFAULT}")
print(f"Key bytes: {key_bytes.hex()}")
print(f"Key length: {len(key_bytes)} bytes")

print("\n" + "=" * 80)
print("1. ECB Mode")
print("=" * 80)
cipher = AES.new(key_bytes, AES.MODE_ECB)
padded = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted = cipher.encrypt(padded)
print(f"Encrypted (ECB): {encrypted.hex().upper()}")
print(f"Length: {len(encrypted.hex().upper())}")

print("\n" + "=" * 80)
print("2. CBC Mode (key as IV, first 16 bytes)")
print("=" * 80)
iv_bytes = key_bytes[:AES.block_size]
cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
padded = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted = cipher.encrypt(padded)
print(f"IV: {iv_bytes.hex()}")
print(f"Encrypted (CBC): {encrypted.hex().upper()}")
print(f"Length: {len(encrypted.hex().upper())}")

print("\n" + "=" * 80)
print("3. CBC Mode (zero IV)")
print("=" * 80)
iv_bytes = bytes(AES.block_size)
cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
padded = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted = cipher.encrypt(padded)
print(f"IV: {iv_bytes.hex()}")
print(f"Encrypted (CBC zero IV): {encrypted.hex().upper()}")
print(f"Length: {len(encrypted.hex().upper())}")

print("\n" + "=" * 80)
print("4. Try padding with null bytes")
print("=" * 80)
data_bytes = TEST_DATA.encode("utf-8")
padding_length = AES.block_size - (len(data_bytes) % AES.block_size)
padded_null = data_bytes + b'\x00' * padding_length
cipher = AES.new(key_bytes, AES.MODE_CBC, key_bytes[:AES.block_size])
encrypted = cipher.encrypt(padded_null)
print(f"Encrypted (CBC, null padding): {encrypted.hex().upper()}")
print(f"Length: {len(encrypted.hex().upper())}")

print("\n" + "=" * 80)
print("5. Try different key derivation")
print("=" * 80)
md5_key = hashlib.md5(key_bytes).digest()
print(f"MD5 key: {md5_key.hex()}")
cipher = AES.new(md5_key, AES.MODE_CBC, md5_key)
padded = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted = cipher.encrypt(padded)
print(f"Encrypted (MD5 key, CBC): {encrypted.hex().upper()}")

sha256_key = hashlib.sha256(key_bytes).digest()[:16]
print(f"\nSHA256 key (first 16): {sha256_key.hex()}")
cipher = AES.new(sha256_key, AES.MODE_CBC, sha256_key)
padded = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted = cipher.encrypt(padded)
print(f"Encrypted (SHA256 key, CBC): {encrypted.hex().upper()}")

print("\n" + "=" * 80)
print("6. Try 128-bit key (first 16 chars)")
print("=" * 80)
short_key = SECRET_KEY_DEFAULT[:16].encode("utf-8")
print(f"Short key: {short_key}")
print(f"Short key bytes: {short_key.hex()}")
cipher = AES.new(short_key, AES.MODE_CBC, short_key)
padded = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs7')
encrypted = cipher.encrypt(padded)
print(f"Encrypted (128-bit key, CBC): {encrypted.hex().upper()}")