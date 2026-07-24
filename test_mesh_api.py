import sys
import os
sys.path.insert(0, os.path.join(os.path.dirname(__file__)))

from custom_components.ltech.api import LtechApiClient
from custom_components.ltech.const import DEFAULT_SERVER_URL

def test_mesh_api():
    print("Testing Ltech Mesh API...")
    
    email = input("Enter your email/phone: ")
    password = input("Enter your password: ")
    
    api = LtechApiClient(DEFAULT_SERVER_URL, email, password)
    
    try:
        print("\n1. Logging in...")
        result = api.login()
        print(f"   Login successful!")
        print(f"   User ID: {api.user_id}")
        print(f"   Session: {api.session[:20]}...")
        
        print("\n2. Getting place list...")
        places = api.get_place_list()
        print(f"   Found {len(places)} places")
        
        if places:
            first_place = places[0]
            place_id = first_place.get("placeId") or first_place.get("placeid")
            place_name = first_place.get("placeName") or first_place.get("placename")
            print(f"   Selected place: {place_name} (ID: {place_id})")
            
            print("\n3. Getting place info (Mesh keys)...")
            place_info = api.get_place_info(place_id)
            
            if isinstance(place_info, dict):
                info = place_info.get("info", {})
                if isinstance(info, dict):
                    net_key = info.get("netkey")
                    app_key = info.get("applicationkey")
                    mesh_uuid = info.get("meshuuid")
                    
                    print(f"   Mesh UUID: {mesh_uuid}")
                    print(f"   Net Key: {net_key}")
                    print(f"   App Key: {app_key}")
                    
                    if net_key and app_key and mesh_uuid:
                        print("\n✅ Mesh keys obtained successfully!")
                        print("   Bluetooth Mesh integration is ready.")
                    else:
                        print("\n❌ Mesh keys not found in API response")
                else:
                    print(f"\n❌ Invalid info format: {type(info)}")
            else:
                print(f"\n❌ Invalid place_info format: {type(place_info)}")
            
            print("\n4. Getting device list...")
            devices = api.get_device_list(place_id)
            if isinstance(devices, dict) and "rows" in devices:
                print(f"   Found {len(devices['rows'])} devices")
                
                for device in devices['rows'][:3]:
                    device_id = device.get("deviceId")
                    device_name = device.get("deviceName")
                    product_id = device.get("productId")
                    unicast_addr = device.get("unicastAddress") or device.get("deviceAddress")
                    
                    print(f"   - {device_name} ({product_id}): {device_id}")
                    if unicast_addr:
                        print(f"     Unicast Address: {unicast_addr}")
                    
                    print()
        else:
            print("\n❌ No places found")
            
    except Exception as e:
        print(f"\n❌ Error: {e}")
        import traceback
        traceback.print_exc()

if __name__ == "__main__":
    test_mesh_api()