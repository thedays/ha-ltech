import asyncio
import logging
import struct
import uuid
from typing import Optional, Dict, Any, Callable

from bleak import BleakClient, BleakScanner, BLEDevice
from bleak.backends.characteristic import BleakGATTCharacteristic

_LOGGER = logging.getLogger(__name__)

MESH_PROVISIONING_SERVICE_UUID = "00001827-0000-1000-8000-00805f9b34fb"
MESH_PROVISIONING_DATA_IN_UUID = "00002adb-0000-1000-8000-00805f9b34fb"
MESH_PROVISIONING_DATA_OUT_UUID = "00002adc-0000-1000-8000-00805f9b34fb"

MESH_PROXY_SERVICE_UUID = "00001828-0000-1000-8000-00805f9b34fb"
MESH_PROXY_DATA_IN_UUID = "00002add-0000-1000-8000-00805f9b34fb"
MESH_PROXY_DATA_OUT_UUID = "00002ade-0000-1000-8000-00805f9b34fb"

LEITE_VENDOR_MODEL_ID = 0x11111111
LEITE_APP_KEY = "63964771734FBD76E3B40519D1D94A48"


class LtechMeshManager:
    def __init__(self):
        self.client: Optional[BleakClient] = None
        self.device: Optional[BLEDevice] = None
        self.connected = False
        self.net_key: Optional[str] = None
        self.app_key: Optional[str] = None
        self.mesh_uuid: Optional[str] = None
        self._data_out_char: Optional[BleakGATTCharacteristic] = None
        self._data_in_char: Optional[BleakGATTCharacteristic] = None
        self._message_callback: Optional[Callable[[Dict[str, Any]], None]] = None
        self._reconnect_task: Optional[asyncio.Task] = None
        self._reconnect_interval = 30

    def set_keys(self, net_key: str, app_key: str, mesh_uuid: str):
        self.net_key = net_key
        self.app_key = app_key if app_key else LEITE_APP_KEY
        self.mesh_uuid = mesh_uuid

    def set_message_callback(self, callback: Callable[[Dict[str, Any]], None]):
        self._message_callback = callback

    async def scan_for_mesh_devices(self, timeout: int = 10) -> list:
        devices = []
        try:
            _LOGGER.info("Scanning for Bluetooth Mesh devices...")
            found_devices = await BleakScanner.discover(timeout=timeout)
            for device in found_devices:
                if device.name and (
                    "Ltech" in device.name or "Mesh" in device.name or "Gateway" in device.name
                ):
                    devices.append(device)
                    _LOGGER.info(f"Found Mesh device: {device.name} ({device.address})")
        except Exception as e:
            _LOGGER.error(f"Scan failed: {e}")
        return devices

    async def connect(self, device_address: Optional[str] = None):
        if self.connected:
            await self.disconnect()

        try:
            if not device_address:
                devices = await self.scan_for_mesh_devices(timeout=5)
                if not devices:
                    _LOGGER.warning("No Mesh devices found")
                    return
                device_address = devices[0].address

            self.device = await BleakScanner.find_device_by_address(device_address)
            if not self.device:
                _LOGGER.error(f"Device {device_address} not found")
                return

            _LOGGER.info(f"Connecting to Mesh device: {self.device.name} ({self.device.address})")
            self.client = BleakClient(self.device)
            await self.client.connect()

            await self._discover_services()
            self.connected = True
            _LOGGER.info("Connected to Mesh network")

            if self._reconnect_task:
                self._reconnect_task.cancel()
            self._reconnect_task = asyncio.create_task(self._reconnect_loop())

        except Exception as e:
            _LOGGER.error(f"Connection failed: {e}")
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
                _LOGGER.info("Mesh Proxy service found and notifications enabled")
        else:
            _LOGGER.warning("Mesh Proxy service not found")

    async def disconnect(self):
        if self._reconnect_task:
            self._reconnect_task.cancel()
            self._reconnect_task = None

        if self.client and self.client.is_connected:
            try:
                await self.client.disconnect()
            except Exception as e:
                _LOGGER.error(f"Disconnect failed: {e}")

        self.connected = False
        self.client = None
        _LOGGER.info("Disconnected from Mesh network")

    async def _reconnect_loop(self):
        while self.connected:
            try:
                await asyncio.sleep(5)
                if self.client and not self.client.is_connected:
                    _LOGGER.warning("Connection lost, reconnecting...")
                    self.connected = False
                    await asyncio.sleep(self._reconnect_interval)
                    await self.connect(self.device.address if self.device else None)
            except asyncio.CancelledError:
                break
            except Exception as e:
                _LOGGER.error(f"Reconnect loop error: {e}")

    def _on_data_received(self, sender, data: bytearray):
        _LOGGER.debug(f"Received Mesh data: {data.hex()}")
        try:
            message = self._parse_mesh_message(data)
            if message and self._message_callback:
                self._message_callback(message)
        except Exception as e:
            _LOGGER.error(f"Failed to parse Mesh message: {e}")

    def _parse_mesh_message(self, data: bytes) -> Dict[str, Any]:
        if len(data) < 4:
            return {}

        opcode = data[0]
        params = data[1:]

        message = {"opcode": opcode, "raw": data.hex()}

        if opcode == 0x80:
            message["type"] = "generic_onoff_status"
            message["on"] = params[0] == 1
        elif opcode == 0x82:
            message["type"] = "generic_level_status"
            message["level"] = int.from_bytes(params[:2], "little", signed=True)
        elif opcode == 0xC0:
            message["type"] = "vendor_model"
            message["model_id"] = int.from_bytes(params[:4], "little")
            message["payload"] = params[4:].hex()
        else:
            message["type"] = "unknown"

        return message

    async def send_generic_onoff(self, address: int, on: bool, app_key_index: int = 0):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("Not connected to Mesh network")
            return False

        try:
            payload = bytes([0x82 if on else 0x81])
            message = self._build_mesh_message(address, app_key_index, payload)

            await self.client.write_gatt_char(self._data_in_char, message, response=True)
            _LOGGER.debug(f"Sent generic onoff to {address}: {on}")
            return True
        except Exception as e:
            _LOGGER.error(f"Failed to send generic onoff: {e}")
            return False

    async def send_generic_level(self, address: int, level: int, app_key_index: int = 0):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("Not connected to Mesh network")
            return False

        try:
            level_bytes = level.to_bytes(2, "little", signed=True)
            payload = bytes([0x83]) + level_bytes
            message = self._build_mesh_message(address, app_key_index, payload)

            await self.client.write_gatt_char(self._data_in_char, message, response=True)
            _LOGGER.debug(f"Sent generic level to {address}: {level}")
            return True
        except Exception as e:
            _LOGGER.error(f"Failed to send generic level: {e}")
            return False

    async def send_vendor_model(self, address: int, opcode: int, parameters: bytes, app_key_index: int = 0):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("Not connected to Mesh network")
            return False

        try:
            opcode_bytes = opcode.to_bytes(2, "little") if opcode > 0xFF else bytes([opcode])
            model_id_bytes = LEITE_VENDOR_MODEL_ID.to_bytes(4, "little")
            payload = bytes([0xC0]) + model_id_bytes + opcode_bytes + parameters

            message = self._build_mesh_message(address, app_key_index, payload)

            await self.client.write_gatt_char(self._data_in_char, message, response=True)
            _LOGGER.debug(f"Sent vendor model to {address}: opcode={opcode}, params={parameters.hex()}")
            return True
        except Exception as e:
            _LOGGER.error(f"Failed to send vendor model: {e}")
            return False

    def _build_mesh_message(self, address: int, app_key_index: int, payload: bytes) -> bytes:
        message = bytearray()

        message.append(0x00)

        message.extend(address.to_bytes(2, "little"))

        message.append((app_key_index & 0x0F) | 0x40)

        message.extend(payload)

        return bytes(message)