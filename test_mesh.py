import asyncio
import json
import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), 'custom_components'))

from ltech.mesh_manager import LtechMeshManager


async def test_mesh_flow():
    print("=" * 60)
    print("Ltech Bluetooth Mesh Test")
    print("=" * 60)

    api = None
    mesh_manager = LtechMeshManager()

    try:
        print("\n1. Testing Bluetooth Mesh Scan...")
        devices = await mesh_manager.scan_for_mesh_devices(timeout=10)
        print(f"   Found {len(devices)} Mesh devices:")
        for i, device in enumerate(devices):
            print(f"     [{i+1}] {device.name} ({device.address})")

        if not devices:
            print("   No Mesh devices found. Please ensure a Mesh gateway is nearby.")
            return

        selected_device = devices[0]
        print(f"\n2. Connecting to {selected_device.name} ({selected_device.address})...")
        await mesh_manager.connect(selected_device.address)

        if not mesh_manager.connected:
            print("   Connection failed!")
            return

        print("   Connected successfully!")
        print(f"   MTU: {mesh_manager.mtu}")

        print("\n3. Testing device address setup...")
        test_addresses = {
            "test_device_1": 0x0001,
            "test_device_2": 0x0002,
            "test_device_3": 0x0003,
        }
        mesh_manager.set_device_addresses(test_addresses)
        print(f"   Set {len(test_addresses)} device addresses")

        print("\n4. Testing device control commands...")

        print("\n   - Testing set_device_on (ON)...")
        result = await mesh_manager.set_device_on("test_device_1", True)
        print(f"     Result: {'SUCCESS' if result else 'FAILED'}")

        await asyncio.sleep(1)

        print("\n   - Testing set_device_on (OFF)...")
        result = await mesh_manager.set_device_on("test_device_1", False)
        print(f"     Result: {'SUCCESS' if result else 'FAILED'}")

        await asyncio.sleep(1)

        print("\n   - Testing set_device_brightness (50%)...")
        result = await mesh_manager.set_device_brightness("test_device_1", 128)
        print(f"     Result: {'SUCCESS' if result else 'FAILED'}")

        await asyncio.sleep(1)

        print("\n   - Testing set_device_brightness (100%)...")
        result = await mesh_manager.set_device_brightness("test_device_1", 255)
        print(f"     Result: {'SUCCESS' if result else 'FAILED'}")

        await asyncio.sleep(1)

        print("\n   - Testing set_device_color_temp (3000K = 333 mired)...")
        result = await mesh_manager.set_device_color_temp("test_device_1", 333)
        print(f"     Result: {'SUCCESS' if result else 'FAILED'}")

        print("\n5. Testing send_device_control...")
        control_data = "66BB27C000002A002200090ED8010000000018001400180040010004015A00AD0204030605040018031C0001EB"
        result = await mesh_manager.send_device_control("test_device_1", control_data)
        print(f"   Result: {'SUCCESS' if result else 'FAILED'}")

        print("\n6. Testing send_vendor_model_message...")
        result = await mesh_manager.send_vendor_model_message("test_device_1", 0x01, bytes([0x01, 0x00]), acknowledged=True)
        print(f"   Result: {'SUCCESS' if result else 'FAILED'}")

        print("\n7. Monitoring for Mesh messages (5 seconds)...")
        await asyncio.sleep(5)

    except KeyboardInterrupt:
        print("\n\nTest interrupted by user")
    except Exception as e:
        print(f"\n\nError during test: {e}")
        import traceback
        traceback.print_exc()
    finally:
        print("\n8. Disconnecting...")
        await mesh_manager.disconnect()
        print("   Disconnected")

    print("\n" + "=" * 60)
    print("Test completed")
    print("=" * 60)


if __name__ == "__main__":
    asyncio.run(test_mesh_flow())