import hashlib
import json
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad

SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03"
TEST_DATA = '{"memberid":119011416470103,"loginname":"test@test.com","pwd":"test123","devicetype":"3","devicesn":""}'

def test_ecb():
    print("=== ECB Mode ===")
    key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
    cipher = AES.new(key_bytes, AES.MODE_ECB)
    padded_data = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    print(f"Encrypted: {encrypted.hex().upper()}")
    print(f"Length: {len(encrypted.hex().upper())}")
    
    decrypted = cipher.decrypt(encrypted)
    unpadded = unpad(decrypted, AES.block_size, style='pkcs7')
    print(f"Decrypted: {unpadded.decode('utf-8')}")

def test_cbc():
    print("\n=== CBC Mode (key as IV) ===")
    key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
    iv_bytes = key_bytes[:AES.block_size]
    cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
    padded_data = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    print(f"Encrypted: {encrypted.hex().upper()}")
    print(f"Length: {len(encrypted.hex().upper())}")
    
    cipher2 = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
    decrypted = cipher2.decrypt(encrypted)
    unpadded = unpad(decrypted, AES.block_size, style='pkcs7')
    print(f"Decrypted: {unpadded.decode('utf-8')}")

def test_cbc_zero_iv():
    print("\n=== CBC Mode (zero IV) ===")
    key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
    iv_bytes = bytes(AES.block_size)
    cipher = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
    padded_data = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs7')
    encrypted = cipher.encrypt(padded_data)
    print(f"Encrypted: {encrypted.hex().upper()}")
    print(f"Length: {len(encrypted.hex().upper())}")
    
    cipher2 = AES.new(key_bytes, AES.MODE_CBC, iv_bytes)
    decrypted = cipher2.decrypt(encrypted)
    unpadded = unpad(decrypted, AES.block_size, style='pkcs7')
    print(f"Decrypted: {unpadded.decode('utf-8')}")

def test_pkcs5():
    print("\n=== ECB Mode (pkcs5 padding) ===")
    key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
    cipher = AES.new(key_bytes, AES.MODE_ECB)
    padded_data = pad(TEST_DATA.encode("utf-8"), AES.block_size, style='pkcs5')
    encrypted = cipher.encrypt(padded_data)
    print(f"Encrypted: {encrypted.hex().upper()}")
    print(f"Length: {len(encrypted.hex().upper())}")

def test_key_derivation():
    print("\n=== Key Derivation Tests ===")
    key_bytes = SECRET_KEY_DEFAULT.encode("utf-8")
    print(f"Key bytes: {key_bytes.hex()}")
    print(f"Key length: {len(key_bytes)}")
    
    if len(key_bytes) < 16:
        padded_key = key_bytes + b'\x00' * (16 - len(key_bytes))
        print(f"Padded key (16 bytes): {padded_key.hex()}")
    
    md5_key = hashlib.md5(key_bytes).digest()
    print(f"MD5 key: {md5_key.hex()}")
    
    sha256_key = hashlib.sha256(key_bytes).digest()[:16]
    print(f"SHA256 key (first 16 bytes): {sha256_key.hex()}")

if __name__ == "__main__":
    test_ecb()
    test_cbc()
    test_cbc_zero_iv()
    test_pkcs5()
    test_key_derivation()