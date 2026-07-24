import base64
import hashlib
import hmac
import json

product_key = "a1bGeiLIk4d"
device_name = "JTSmGQuiWnYT6yi7Lwhy"
device_secret = "ac7b34c1fc560aa033ed4887508adcd9"
timestamp = "1783742084969"

client_id = f"{product_key}@@@{device_name}"

sign_content = f"clientId{client_id}deviceName{device_name}productKey{product_key}timestamp{timestamp}"
print(f"Sign content: {sign_content}")

password = hmac.new(
    device_secret.encode('utf-8'),
    sign_content.encode('utf-8'),
    hashlib.sha1
).digest()

password_b64 = base64.b64encode(password).decode('utf-8')
print(f"Password: {password_b64}")

sign_content2 = f"clientId{product_key}@@@{device_name}deviceName{device_name}productKey{product_key}timestamp{timestamp}"
print(f"\nSign content 2: {sign_content2}")

password2 = hmac.new(
    device_secret.encode('utf-8'),
    sign_content2.encode('utf-8'),
    hashlib.sha1
).digest()

password_b64_2 = base64.b64encode(password2).decode('utf-8')
print(f"Password 2: {password_b64_2}")

print(f"\n{'='*60}")
print(f"测试其他可能的格式...")
print(f"{'='*60}")

sign_content3 = f"clientId={client_id},deviceName={device_name},productKey={product_key},timestamp={timestamp}"
print(f"Sign content 3: {sign_content3}")
password3 = hmac.new(device_secret.encode('utf-8'), sign_content3.encode('utf-8'), hashlib.sha1).digest()
print(f"Password 3: {base64.b64encode(password3).decode('utf-8')}")

sign_content4 = f"clientId{product_key}@{device_name}deviceName{device_name}productKey{product_key}timestamp{timestamp}"
print(f"\nSign content 4 (single @): {sign_content4}")
password4 = hmac.new(device_secret.encode('utf-8'), sign_content4.encode('utf-8'), hashlib.sha1).digest()
print(f"Password 4: {base64.b64encode(password4).decode('utf-8')}")

sign_content5 = f"productKey{product_key}deviceName{device_name}clientId{client_id}timestamp{timestamp}"
print(f"\nSign content 5 (不同顺序): {sign_content5}")
password5 = hmac.new(device_secret.encode('utf-8'), sign_content5.encode('utf-8'), hashlib.sha1).digest()
print(f"Password 5: {base64.b64encode(password5).decode('utf-8')}")

sign_content6 = f"productKey{product_key}deviceName{device_name}timestamp{timestamp}"
print(f"\nSign content 6 (不含clientId): {sign_content6}")
password6 = hmac.new(device_secret.encode('utf-8'), sign_content6.encode('utf-8'), hashlib.sha1).digest()
print(f"Password 6: {base64.b64encode(password6).decode('utf-8')}")