import asyncio
import struct
import logging

logging.basicConfig(level=logging.INFO)

from bleak import BleakClient, BleakScanner, BLEDevice
from bleak.backends.characteristic import BleakGATTCharacteristic


MESH_PROVISIONING_SERVICE_UUID = "00001827-0000-1000-8000-00805f9b34fb"
MESH_PROVISIONING_DATA_IN_UUID = "00002adb-0000-1000-8000-00805f9b34fb"
MESH_PROVISIONING_DATA_OUT_UUID = "00002adc-0000-1000-8000-00805f9b34fb"

MESH_PROXY_SERVICE_UUID = "00001828-0000-1000-8000-00805f9b34fb"
MESH_PROXY_DATA_IN_UUID = "00002add-0000-1000-8000-00805f9b34fb"
MESH_PROXY_DATA_OUT_UUID = "00002ade-0000-1000-8000-00805f9b34fb"

LEITE_COMPANY_ID = 0x1121
LEITE_VENDOR_MODEL_ID = 0x11111111

NET_KEY_INDEX = 0
APP_KEY_INDEX = 0


class LtechMeshManager:
    def __init__(self):
        self.client = None
        self.device = None
        self.connected = False
        self.net_key = None
        self.app_key = None
        self.mesh_uuid = None
        self._data_out_char = None
        self._data_in_char = None
        self.device_addresses = {}
        self.mtu = 23

    def set_keys(self, net_key, app_key, mesh_uuid):
        self.net_key = net_key
        self.app_key = app_key if app_key else "63964771734FBD76E3B40519D1D94A48"
        self.mesh_uuid = mesh_uuid

    def set_device_addresses(self, addresses):
        self.device_addresses = addresses

    async def scan_for_mesh_devices(self, timeout=10):
        devices = []
        try:
            print("[MESH] Scanning for Bluetooth Mesh devices...")
            found_devices = await BleakScanner.discover(timeout=timeout)
            for device in found_devices:
                if device.name and (
                    "Ltech" in device.name or "Mesh" in device.name or "Gateway" in device.name
                ):
                    devices.append(device)
                    print(f"[MESH] Found Mesh device: {device.name} ({device.address})")
        except Exception as e:
            print(f"[MESH] Scan failed: {e}")
        return devices

    async def connect(self, device_address=None):
        if self.connected:
            await self.disconnect()

        try:
            if not device_address:
                devices = await self.scan_for_mesh_devices(timeout=5)
                if not devices:
                    print("[MESH] No Mesh devices found")
                    return
                device_address = devices[0].address

            self.device = await BleakScanner.find_device_by_address(device_address)
            if not self.device:
                print(f"[MESH] Device {device_address} not found")
                return

            print(f"[MESH] Connecting to Mesh device: {self.device.name} ({self.device.address})")
            self.client = BleakClient(self.device)
            await self.client.connect()

            await self._discover_services()

            try:
                await self.client.request_mtu(517)
                self.mtu = 517
                print(f"[MESH] MTU set to {self.mtu}")
            except Exception as e:
                print(f"[MESH] Failed to set MTU: {e}, using default 23")

            self.connected = True
            print("[MESH] Connected to Mesh network")

        except Exception as e:
            print(f"[MESH] Connection failed: {e}")
            self.connected = False

    async def _discover_services(self):
        if not self.client:
            return

        services = await self.client.get_services()

        mesh_proxy_service = services.get_service(MESH_PROXY_SERVICE_UUID)
        if mesh_proxy_service:
            self._data_in_char = mesh_proxy_service.get_characteristic(MESH_PROXY_DATA_IN_UUID)
            self._data_out_char = mesh_proxy_service.get_characteristic(MESH_PROXY_DATA_OUT_UUID)

            if self._data_out_char and self._data_out_char.properties.read:
                await self.client.start_notify(self._data_out_char, self._on_data_received)
                print("[MESH] Mesh Proxy service found and notifications enabled")
        else:
            print("[MESH] Mesh Proxy service not found")

    async def disconnect(self):
        if self.client and self.client.is_connected:
            try:
                await self.client.disconnect()
            except Exception as e:
                print(f"[MESH] Disconnect failed: {e}")

        self.connected = False
        self.client = None
        print("[MESH] Disconnected from Mesh network")

    def _on_data_received(self, sender, data):
        print(f"[MESH] Received Mesh data: {data.hex()}")

    def _build_access_message(self, address, payload):
        message = bytearray()
        message.append(0x00)
        message.extend(struct.pack("<H", address))
        message.append((APP_KEY_INDEX & 0x0F) | 0x40)
        message.extend(payload)
        return bytes(message)

    async def set_device_on(self, device_id, on):
        if not self.connected or not self._data_in_char:
            print("[MESH] Not connected to Mesh network")
            return False

        try:
            address = self.device_addresses.get(device_id, 0)
            if address == 0:
                print(f"[MESH] No address found for device {device_id}")
                return False

            payload = bytearray([0x82 if on else 0x81])
            message = self._build_access_message(address, payload)

            await self.client.write_gatt_char(self._data_in_char, message, response=True)
            print(f"[MESH] Set {device_id} (addr={address}) to {'ON' if on else 'OFF'}")
            return True
        except Exception as e:
            print(f"[MESH] Failed to set device on/off: {e}")
            return False

    async def set_device_brightness(self, device_id, brightness):
        if not self.connected or not self._data_in_char:
            print("[MESH] Not connected to Mesh network")
            return False

        try:
            address = self.device_addresses.get(device_id, 0)
            if address == 0:
                print(f"[MESH] No address found for device {device_id}")
                return False

            level = int((brightness / 255) * 65535)
            level_bytes = struct.pack("<h", level)
            payload = bytearray([0x83]) + level_bytes
            message = self._build_access_message(address, payload)

            await self.client.write_gatt_char(self._data_in_char, message, response=True)
            print(f"[MESH] Set {device_id} (addr={address}) brightness to {brightness}")
            return True
        except Exception as e:
            print(f"[MESH] Failed to set device brightness: {e}")
            return False

    def is_connected(self):
        return self.connected


async def test_mesh_flow():
    print("=" * 60)
    print("Ltech Bluetooth Mesh Test")
    print("=" * 60)

    mesh_manager = LtechMeshManager()

    try:
        print("\n1. Testing Bluetooth Mesh Scan...")
        devices = await mesh_manager.scan_for_mesh_devices(timeout=10)
        print(f"   Found {len(devices)} Mesh devices:")
        for i, device in enumerate(devices):
            print(f"     [{i+1}] {device.name} ({device.address})")

        if not devices:
            print("\n   No Mesh devices found.")
            print("   Please ensure:")
            print("   - A Ltech Mesh Gateway is powered on and nearby")
            print("   - Bluetooth is enabled on this machine")
            print("   - The gateway is in Mesh Proxy mode")
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