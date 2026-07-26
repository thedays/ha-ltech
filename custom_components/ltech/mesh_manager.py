import asyncio
import logging
import struct
import json
import os
from typing import Optional, Dict, Any, Callable

from bleak import BleakClient, BleakScanner, BLEDevice
from bleak.backends.characteristic import BleakGATTCharacteristic

from .mesh_crypto import (
    k1, k2, k3, k4, aes_ccm_encrypt, aes_ccm_decrypt,
    generate_nonce, build_access_message, build_vendor_model_message,
    build_proxy_pdu, parse_proxy_pdu, segment_network_pdu, hex_to_bytes
)

_LOGGER = logging.getLogger(__name__)

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

DEFAULT_MTU = 517
RECONNECT_INTERVAL = 30


class LtechMeshManager:
    def __init__(self):
        self.client: Optional[BleakClient] = None
        self.device: Optional[BLEDevice] = None
        self.connected = False
        
        self.net_key: Optional[bytes] = None
        self.app_key: Optional[bytes] = None
        self.mesh_uuid: Optional[str] = None
        
        self._data_out_char: Optional[BleakGATTCharacteristic] = None
        self._data_in_char: Optional[BleakGATTCharacteristic] = None
        self._message_callback: Optional[Callable[[Dict[str, Any]], None]] = None
        
        self._reconnect_task: Optional[asyncio.Task] = None
        self._reconnect_interval = RECONNECT_INTERVAL
        
        self.device_addresses: Dict[str, int] = {}
        self.mtu = DEFAULT_MTU
        
        self.iv_index = 0
        self.seq_number = 0
        self._seq_lock = asyncio.Lock()
        
        self._nid = None
        self._encryption_key = None
        self._privacy_key = None
        self._transmit_key = None
        
        self._sar_buffer = {}
        self._sar_timeout = 5.0
        
        self._local_address = 0x0001

    def set_keys(self, net_key: str, app_key: str, mesh_uuid: str):
        self.net_key = hex_to_bytes(net_key)
        self.app_key = hex_to_bytes(app_key) if app_key else hex_to_bytes("63964771734FBD76E3B40519D1D94A48")
        self.mesh_uuid = mesh_uuid
        
        self._derive_keys()

    def _derive_keys(self):
        if self.net_key:
            self._nid, self._encryption_key, self._privacy_key = k1(self.net_key, NET_KEY_INDEX)
        
        if self.app_key:
            self._transmit_key = k4(self.app_key)

    def set_device_addresses(self, addresses: Dict[str, int]):
        self.device_addresses = addresses

    def set_message_callback(self, callback: Callable[[Dict[str, Any]], None]):
        self._message_callback = callback

    def set_iv_index(self, iv_index: int):
        self.iv_index = iv_index

    def set_local_address(self, address: int):
        self._local_address = address

    async def scan_for_mesh_devices(self, timeout: int = 10) -> list:
        devices = []
        try:
            _LOGGER.info("[MESH] Scanning for Bluetooth Mesh devices...")
            found_devices = await BleakScanner.discover(timeout=timeout)
            for device in found_devices:
                if device.name and (
                    "Ltech" in device.name or "Mesh" in device.name or "Gateway" in device.name
                ):
                    devices.append(device)
                    _LOGGER.info(f"[MESH] Found Mesh device: {device.name} ({device.address})")
        except Exception as e:
            _LOGGER.error(f"[MESH] Scan failed: {e}")
        return devices

    async def connect(self, device_address: Optional[str] = None):
        if self.connected:
            await self.disconnect()

        try:
            if not device_address:
                devices = await self.scan_for_mesh_devices(timeout=5)
                if not devices:
                    _LOGGER.warning("[MESH] No Mesh devices found")
                    return
                device_address = devices[0].address

            self.device = await BleakScanner.find_device_by_address(device_address)
            if not self.device:
                _LOGGER.error(f"[MESH] Device {device_address} not found")
                return

            _LOGGER.info(f"[MESH] Connecting to Mesh device: {self.device.name} ({self.device.address})")
            self.client = BleakClient(self.device)
            await self.client.connect()

            await self._discover_services()
            
            try:
                await self.client.request_mtu(DEFAULT_MTU)
                self.mtu = DEFAULT_MTU
                _LOGGER.info(f"[MESH] MTU set to {self.mtu}")
            except Exception as e:
                _LOGGER.warning(f"[MESH] Failed to set MTU: {e}, using default 23")

            self.connected = True
            _LOGGER.info("[MESH] Connected to Mesh network")

            if self._reconnect_task:
                self._reconnect_task.cancel()
            self._reconnect_task = asyncio.create_task(self._reconnect_loop())

        except Exception as e:
            _LOGGER.error(f"[MESH] Connection failed: {e}")
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
                _LOGGER.info("[MESH] Mesh Proxy service found and notifications enabled")
        else:
            _LOGGER.warning("[MESH] Mesh Proxy service not found")

    async def disconnect(self):
        if self._reconnect_task:
            self._reconnect_task.cancel()
            self._reconnect_task = None

        if self.client and self.client.is_connected:
            try:
                await self.client.disconnect()
            except Exception as e:
                _LOGGER.error(f"[MESH] Disconnect failed: {e}")

        self.connected = False
        self.client = None
        _LOGGER.info("[MESH] Disconnected from Mesh network")

    async def _reconnect_loop(self):
        while self.connected:
            try:
                await asyncio.sleep(5)
                if self.client and not self.client.is_connected:
                    _LOGGER.warning("[MESH] Connection lost, reconnecting...")
                    self.connected = False
                    await asyncio.sleep(self._reconnect_interval)
                    await self.connect(self.device.address if self.device else None)
            except asyncio.CancelledError:
                break
            except Exception as e:
                _LOGGER.error(f"[MESH] Reconnect loop error: {e}")

    def _on_data_received(self, sender, data: bytearray):
        _LOGGER.debug(f"[MESH] Received Mesh data: {data.hex()}")
        try:
            parsed = parse_proxy_pdu(bytes(data))
            
            if parsed["is_segmented"]:
                network_pdu = self._handle_sar_receive(parsed)
                if not network_pdu:
                    return
            else:
                network_pdu = parsed["network_pdu"]
            
            message = self._parse_network_pdu(network_pdu)
            if message and self._message_callback:
                self._message_callback(message)
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to parse Mesh message: {e}")

    def _handle_sar_receive(self, parsed):
        network_pdu = parsed["network_pdu"]
        offset = parsed["segment_offset"]
        is_last = parsed["last_segment"]
        
        buffer_key = "current"
        if buffer_key not in self._sar_buffer:
            self._sar_buffer[buffer_key] = {"data": b"", "expected_offset": 0}
        
        buffer = self._sar_buffer[buffer_key]
        
        if offset != buffer["expected_offset"]:
            _LOGGER.warning(f"[MESH] SAR segment out of order: expected {buffer['expected_offset']}, got {offset}")
            return None
        
        buffer["data"] += network_pdu
        buffer["expected_offset"] += len(network_pdu)
        
        if is_last:
            full_pdu = buffer["data"]
            del self._sar_buffer[buffer_key]
            return full_pdu
        
        return None

    def _parse_network_pdu(self, network_pdu):
        if len(network_pdu) < 12:
            _LOGGER.debug(f"[MESH] Network PDU too short: {network_pdu.hex()}")
            return None
        
        nid = network_pdu[0]
        if nid != self._nid:
            _LOGGER.debug(f"[MESH] NID mismatch: expected {self._nid.hex()}, got {nid:02X}")
            return None
        
        iv_index = struct.unpack("<I", network_pdu[1:5])[0]
        seq_number = int.from_bytes(network_pdu[5:11], "little")
        source = struct.unpack("<H", network_pdu[11:13])[0]
        
        encrypted_data = network_pdu[13:]
        
        nonce = generate_nonce(iv_index, seq_number, source)
        auth_data = bytes([nid]) + network_pdu[1:13]
        
        plaintext = aes_ccm_decrypt(self._encryption_key, nonce, encrypted_data, auth_data)
        
        if plaintext is None:
            _LOGGER.debug(f"[MESH] Decryption failed")
            return None
        
        return self._parse_access_pdu(plaintext)

    def _parse_access_pdu(self, access_pdu):
        if len(access_pdu) < 4:
            return None
        
        opcode = access_pdu[0]
        params = access_pdu[1:]
        
        message = {"opcode": opcode, "raw": access_pdu.hex()}
        
        if opcode == 0x80:
            message["type"] = "generic_onoff_status"
            message["on"] = params[0] == 1
        elif opcode == 0x81:
            message["type"] = "generic_onoff_get"
        elif opcode == 0x82:
            message["type"] = "generic_onoff_set"
            message["on"] = params[0] == 1
        elif opcode == 0x83:
            message["type"] = "generic_level_set"
            message["level"] = int.from_bytes(params[:2], "little", signed=True)
        elif opcode == 0x84:
            message["type"] = "generic_level_get"
        elif opcode == 0x85:
            message["type"] = "generic_level_status"
            message["level"] = int.from_bytes(params[:2], "little", signed=True)
        elif opcode == 0xC0:
            message["type"] = "vendor_model"
            company_id = struct.unpack("<H", params[:2])[0]
            model_id = struct.unpack("<I", params[2:6])[0]
            vendor_opcode = params[6] if len(params) > 6 else 0
            vendor_params = params[7:] if len(params) > 7 else b""
            
            message["company_id"] = company_id
            message["model_id"] = model_id
            message["vendor_opcode"] = vendor_opcode
            message["payload"] = vendor_params.hex()
            
            if company_id == LEITE_COMPANY_ID and model_id == LEITE_VENDOR_MODEL_ID:
                message["type"] = "leite_vendor_model"
                message = self._parse_leite_vendor_message(message, vendor_opcode, vendor_params)
        else:
            message["type"] = "unknown"
        
        return message

    def _parse_leite_vendor_message(self, message, opcode, params):
        if opcode == 0x01:
            message["sub_type"] = "device_control"
            if len(params) >= 2:
                status = params[0]
                device_data = params[1:].hex()
                message["status"] = status
                message["device_data"] = device_data
        elif opcode == 0x02:
            message["sub_type"] = "device_status"
            if len(params) >= 1:
                message["status_byte"] = params[0]
        elif opcode == 0x03:
            message["sub_type"] = "brightness"
            if len(params) >= 2:
                message["brightness"] = int.from_bytes(params[:2], "little")
        elif opcode == 0x04:
            message["sub_type"] = "color_temp"
            if len(params) >= 2:
                message["color_temp_mired"] = int.from_bytes(params[:2], "little")
        else:
            message["sub_type"] = "unknown"
        
        return message

    async def _build_and_send_network_pdu(self, destination: int, access_pdu: bytes):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False
        
        if not self._encryption_key:
            _LOGGER.warning("[MESH] Encryption key not set")
            return False

        try:
            async with self._seq_lock:
                seq_number = self.seq_number
                self.seq_number = (self.seq_number + 1) % (2**48)

            nonce = generate_nonce(self.iv_index, seq_number, self._local_address)
            
            network_pdu_header = bytearray()
            network_pdu_header.extend(self._nid)
            network_pdu_header.extend(struct.pack("<I", self.iv_index))
            network_pdu_header.extend(int.to_bytes(seq_number, 6, "little"))
            network_pdu_header.extend(struct.pack("<H", destination))
            
            auth_data = bytes(network_pdu_header)
            encrypted_data = aes_ccm_encrypt(self._encryption_key, nonce, access_pdu, auth_data)
            
            network_pdu = network_pdu_header + encrypted_data
            
            proxy_pdus = segment_network_pdu(network_pdu, self.mtu)
            
            for pdu in proxy_pdus:
                await self.client.write_gatt_char(self._data_in_char, pdu, response=False)
                _LOGGER.debug(f"[MESH] Sent PDU: {len(pdu)} bytes")
            
            _LOGGER.debug(f"[MESH] Sent network PDU to {destination}: seq={seq_number}")
            return True
        
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send network PDU: {e}")
            return False

    async def send_vendor_model_message(self, device_id: str, opcode: int, parameters: bytes, acknowledged: bool = False):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            address = self.device_addresses.get(device_id, 0)
            if address == 0:
                _LOGGER.warning(f"[MESH] No address found for device {device_id}")
                return False

            vendor_payload = build_vendor_model_message(opcode, parameters)
            access_pdu = build_access_message(address, APP_KEY_INDEX, vendor_payload)

            result = await self._build_and_send_network_pdu(address, access_pdu)
            
            if result:
                _LOGGER.info(f"[MESH] Sent vendor model to {device_id} (addr={address}): opcode={opcode}")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send vendor model: {e}")
            return False

    async def send_device_control(self, device_id: str, control_data: str):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            address = self.device_addresses.get(device_id, 0)
            if address == 0:
                _LOGGER.warning(f"[MESH] No address found for device {device_id}")
                return False

            parameters = hex_to_bytes(control_data)
            result = await self.send_vendor_model_message(device_id, 0x01, parameters, acknowledged=True)
            
            if result:
                _LOGGER.info(f"[MESH] Sent control to {device_id} (addr={address})")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send device control: {e}")
            return False

    async def set_device_on(self, device_id: str, on: bool):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            address = self.device_addresses.get(device_id, 0)
            if address == 0:
                _LOGGER.warning(f"[MESH] No address found for device {device_id}")
                return False

            opcode = 0x82 if on else 0x81
            parameters = bytes([0x01 if on else 0x00])
            vendor_payload = build_vendor_model_message(opcode, parameters)
            access_pdu = build_access_message(address, APP_KEY_INDEX, vendor_payload)

            result = await self._build_and_send_network_pdu(address, access_pdu)
            
            if result:
                _LOGGER.info(f"[MESH] Set {device_id} (addr={address}) to {'ON' if on else 'OFF'}")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to set device on/off: {e}")
            return False

    async def set_device_brightness(self, device_id: str, brightness: int):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            address = self.device_addresses.get(device_id, 0)
            if address == 0:
                _LOGGER.warning(f"[MESH] No address found for device {device_id}")
                return False

            level = int((brightness / 255) * 65535)
            level_bytes = struct.pack("<h", level)
            parameters = level_bytes + bytes([0x00, 0x00])
            
            result = await self.send_vendor_model_message(device_id, 0x03, parameters, acknowledged=True)
            
            if result:
                _LOGGER.info(f"[MESH] Set {device_id} (addr={address}) brightness to {brightness}")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to set device brightness: {e}")
            return False

    async def set_device_color_temp(self, device_id: str, color_temp: int):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            address = self.device_addresses.get(device_id, 0)
            if address == 0:
                _LOGGER.warning(f"[MESH] No address found for device {device_id}")
                return False

            parameters = struct.pack("<H", color_temp)
            
            result = await self.send_vendor_model_message(device_id, 0x04, parameters, acknowledged=True)
            
            if result:
                _LOGGER.info(f"[MESH] Set {device_id} (addr={address}) color temp to {color_temp}")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to set device color temp: {e}")
            return False

    def is_connected(self):
        return self.connected
