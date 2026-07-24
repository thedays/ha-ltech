import sys
sys.path.insert(0, '/Users/k/Documents/project/ha-ltech')

import json
from test_full_api7 import LtechApiClient

def analyze_devices(account, password):
    client = LtechApiClient()
    
    print("Step 1: Login")
    login_result = client.login(account, password)
    if not login_result:
        print("Login failed")
        return
    
    print(f"✅ Login SUCCESS")
    
    print("\nStep 2: Get Place List")
    place_list = client.get_place_list()
    if not place_list:
        print("Place list failed")
        return
    
    first_place = place_list['rows'][0]
    place_id = first_place.get('placeid')
    print(f"✅ Place: {first_place.get('placename')} (placeid={place_id})")
    
    print("\nStep 3: Get Device List")
    device_list = client.get_device_list(place_id)
    if not device_list:
        print("Device list failed")
        return
    
    lights = []
    switches = []
    others = []
    
    LIGHT_PRODUCT_IDS = ["LIGHT_CT", "LIGHT_DIM", "LIGHT_COLOR", "LIGHT_DUV", "LIGHT_512", "CGD_PRO_LIGHT", "DALI_LIGHT"]
    SWITCH_PRODUCT_IDS = ["SWITCH_1G", "SWITCH_2G", "SWITCH_3G", "SWITCH_4G", "RELAY_1CH", "RELAY_2CH", "RELAY_4CH", "SOCKET"]
    
    for device in device_list.get('rows', []):
        devicename = device.get('devicename')
        producttypename = device.get('producttypename', '')
        producttype = device.get('producttype', '')
        productname = device.get('productname', '')
        
        is_switch = (producttypename == "智能开关" or 
                    "开关" in producttypename or
                    producttype == "8")
        
        is_light = (producttypename == "智能照明" or 
                   "灯" in producttypename or
                   producttype == "2")
        
        device_info = {
            'devicename': devicename,
            'producttypename': producttypename,
            'producttype': producttype,
            'productname': productname,
        }
        
        if is_switch and any("SWITCH" in pt for pt in SWITCH_PRODUCT_IDS):
            switches.append(device_info)
        elif is_light and any("LIGHT" in pt for pt in LIGHT_PRODUCT_IDS):
            lights.append(device_info)
        else:
            others.append(device_info)
    
    print(f"\n{'='*80}")
    print(f"Device Analysis Results:")
    print(f"{'='*80}")
    print(f"Total devices: {len(device_list.get('rows', []))}")
    print(f"Light devices: {len(lights)}")
    print(f"Switch devices: {len(switches)}")
    print(f"Other devices: {len(others)}")
    
    print(f"\n--- Lights ({len(lights)}) ---")
    for d in lights:
        print(f"  - {d['devicename']} (type={d['producttype']}, typename={d['producttypename']}, name={d['productname']})")
    
    print(f"\n--- Switches ({len(switches)}) ---")
    for d in switches:
        print(f"  - {d['devicename']} (type={d['producttype']}, typename={d['producttypename']}, name={d['productname']})")
    
    print(f"\n--- Others ({len(others)}) ---")
    for d in others:
        print(f"  - {d['devicename']} (type={d['producttype']}, typename={d['producttypename']}, name={d['productname']})")

if __name__ == "__main__":
    if len(sys.argv) >= 3:
        analyze_devices(sys.argv[1], sys.argv[2])
    else:
        print("Usage: python analyze_devices.py <account> <password>")