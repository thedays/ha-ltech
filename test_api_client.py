import sys
import os
sys.path.insert(0, '/Users/k/Documents/project/ha-ltech')

from custom_components.ltech.api import LtechApiClient

def test_login():
    server_url = "https://apic.ltsys.com.cn:2443/"
    
    if len(sys.argv) >= 3:
        account = sys.argv[1]
        password = sys.argv[2]
    else:
        print("Usage: python test_api_client.py <account> <password>")
        return
    
    print(f"Testing login with account: {account}")
    
    client = LtechApiClient(server_url, account, password)
    
    try:
        result = client.login()
        print(f"✅ Login SUCCESS!")
        print(f"Result: {result}")
        print(f"Session: {client.session}")
        print(f"User ID: {client.user_id}")
        
        if client.user_id:
            places = client.get_place_list()
            print(f"\nPlace List: {places}")
            
            if places and isinstance(places, list) and len(places) > 0:
                first_place = places[0]
                place_id = first_place.get("placeId") or first_place.get("id")
                print(f"\nGetting devices for place: {place_id}")
                devices = client.get_device_list(place_id)
                print(f"Devices: {devices}")
                
    except Exception as e:
        print(f"❌ Login FAILED: {e}")

if __name__ == "__main__":
    test_login()