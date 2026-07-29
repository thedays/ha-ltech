import asyncio
import logging
import struct
import json
import os
from typing import Optional, Dict, Any, Callable

from bleak import BleakClient, BleakScanner, BLEDevice
from bleak.backends.characteristic import BleakGATTCharacteristic

from .mesh_crypto import (
    k1, k4,
    decrypt_network_pdu, decrypt_upper_transport,
    encrypt_upper_transport,
    build_network_pdu, build_access_message, build_vendor_model_message,
    parse_vendor_model_message,
    build_proxy_pdu, parse_proxy_pdu, segment_network_pdu,
    hex_to_bytes,
    LEITE_COMPANY_ID, LEITE_VENDOR_MODEL_ID
)

_LOGGER = logging.getLogger(__name__)

MESH_PROVISIONING_SERVICE_UUID = "00001827-0000-1000-8000-00805f9b34fb"
MESH_PROVISIONING_DATA_IN_UUID = "00002adb-0000-1000-8000-00805f9b34fb"
MESH_PROVISIONING_DATA_OUT_UUID = "00002adc-0000-1000-8000-00805f9b34fb"

MESH_PROXY_SERVICE_UUID = "00001828-0000-1000-8000-00805f9b34fb"
MESH_PROXY_DATA_IN_UUID = "00002add-0000-1000-8000-00805f9b34fb"
MESH_PROXY_DATA_OUT_UUID = "00002ade-0000-1000-8000-00805f9b34fb"

NET_KEY_INDEX = 0
APP_KEY_INDEX = 0

DEFAULT_MTU = 517
RECONNECT_INTERVAL = 30
DEFAULT_TTL = 8


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
        self.address_to_device: Dict[int, str] = {}
        self.device_keys: Dict[int, bytes] = {}
        self.address_to_device_key: Dict[int, bytes] = {}
        self.mtu = DEFAULT_MTU
        
        self.iv_index = 0
        self.seq_number = 0
        self._seq_lock = asyncio.Lock()
        
        self._nid = None
        self._encryption_key = None
        self._privacy_key = None
        self._app_aid = None
        
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
            _LOGGER.info(f"[MESH] Derived keys: NID=0x{self._nid:02X}")
        
        if self.app_key:
            self._app_aid = k4(self.app_key)
            _LOGGER.info(f"[MESH] Derived App AID: 0x{self._app_aid:02X} ({self._app_aid})")

    def set_device_addresses(self, addresses: Dict[str, int]):
        self.device_addresses = addresses
        self.address_to_device = {addr: dev_id for dev_id, addr in addresses.items()}
    
    def set_device_keys(self, device_keys_map: Dict[str, str]):
        for dev_id, key_hex in device_keys_map.items():
            try:
                key_bytes = hex_to_bytes(key_hex)
                address = self.device_addresses.get(dev_id)
                if address is not None:
                    self.device_keys[address] = key_bytes
                    self.address_to_device_key[address] = key_bytes
                    _LOGGER.info(f"[MESH] Set device key for {dev_id} (addr=0x{address:04X}): {key_hex[:8]}...")
            except Exception as e:
                _LOGGER.warning(f"[MESH] Failed to set device key for {dev_id}: {e}")
    
    def get_device_key_by_address(self, address: int) -> Optional[bytes]:
        return self.address_to_device_key.get(address)
    
    def get_device_by_address(self, address: int) -> Optional[str]:
        return self.address_to_device.get(address)

    def set_message_callback(self, callback: Callable[[Dict[str, Any]], None]):
        self._message_callback = callback

    def set_iv_index(self, iv_index: int):
        self.iv_index = iv_index

    def set_local_address(self, address: int):
        self._local_address = address

    async def scan_for_mesh_devices(self, timeout: int = 10) -> list:
        devices = []
        try:
            _LOGGER.info(f"[MESH] Scanning for Bluetooth Mesh devices (timeout={timeout}s)...")
            found_devices = await BleakScanner.discover(timeout=timeout)
            _LOGGER.info(f"[MESH] Found {len(found_devices)} Bluetooth devices total")
            
            for device in found_devices:
                _LOGGER.info(f"[MESH] Device: name={device.name}, address={device.address}, RSSI={device.rssi}")
                
                if device.name and (
                    "Ltech" in device.name or 
                    "Mesh" in device.name or 
                    "Gateway" in device.name or
                    "LTech" in device.name or
                    "ltech" in device.name.lower()
                ):
                    devices.append(device)
                    _LOGGER.info(f"[MESH] Found matching Mesh device: {device.name} ({device.address})")
            
            if not devices:
                _LOGGER.warning("[MESH] No matching Mesh devices found. Showing all devices:")
                for device in found_devices:
                    _LOGGER.warning(f"[MESH]   {device.name or '(no name)'} ({device.address}) RSSI={device.rssi}")
                    
        except Exception as e:
            _LOGGER.error(f"[MESH] Scan failed: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
        return devices

    async def connect(self, device_address: Optional[str] = None):
        _LOGGER.info(f"[MESH] Connect called: device_address={device_address}, currently_connected={self.connected}")
        
        if self.connected:
            _LOGGER.info("[MESH] Already connected, disconnecting first")
            await self.disconnect()

        try:
            if not device_address:
                _LOGGER.info("[MESH] No device address provided, scanning...")
                devices = await self.scan_for_mesh_devices(timeout=5)
                if not devices:
                    _LOGGER.warning("[MESH] No Mesh devices found during scan")
                    _LOGGER.warning("[MESH] Please ensure:")
                    _LOGGER.warning("[MESH]   1. The Ltech gateway is powered on")
                    _LOGGER.warning("[MESH]   2. The gateway is within Bluetooth range (10-30 meters)")
                    _LOGGER.warning("[MESH]   3. Your Mac's Bluetooth is turned on")
                    return
                device_address = devices[0].address
                _LOGGER.info(f"[MESH] Selected device: {device_address}")

            _LOGGER.info(f"[MESH] Looking for device by address: {device_address}")
            self.device = await BleakScanner.find_device_by_address(device_address)
            if not self.device:
                _LOGGER.error(f"[MESH] Device {device_address} not found by address")
                return

            _LOGGER.info(f"[MESH] Connecting to Mesh device: {self.device.name} ({self.device.address})")
            self.client = BleakClient(self.device)
            _LOGGER.info("[MESH] BleakClient created, connecting...")
            await self.client.connect()
            _LOGGER.info("[MESH] BLE connection established")

            _LOGGER.info("[MESH] Discovering services...")
            await self._discover_services()
            
            try:
                await self.client.request_mtu(DEFAULT_MTU)
                self.mtu = DEFAULT_MTU
                _LOGGER.info(f"[MESH] MTU set to {self.mtu}")
            except Exception as e:
                _LOGGER.warning(f"[MESH] Failed to set MTU: {e}, using default 23")

            self.connected = True
            _LOGGER.info("[MESH] Connected to Mesh network, mtu={self.mtu}")

            if self._reconnect_task:
                self._reconnect_task.cancel()
            self._reconnect_task = asyncio.create_task(self._reconnect_loop())
            _LOGGER.info("[MESH] Reconnect loop started")

        except Exception as e:
            _LOGGER.error(f"[MESH] Connection failed: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Connection traceback: {traceback.format_exc()}")
            self.connected = False

    async def _discover_services(self):
        if not self.client:
            _LOGGER.warning("[MESH] No BLE client, cannot discover services")
            return

        _LOGGER.info("[MESH] Discovering BLE services...")
        services = await self.client.get_services()
        _LOGGER.info(f"[MESH] Found {len(services)} services")

        for service in services:
            _LOGGER.info(f"[MESH] Service: {service.uuid}")
            for char in service.characteristics:
                _LOGGER.info(f"[MESH]   Characteristic: {char.uuid}, properties={char.properties}")

        _LOGGER.info(f"[MESH] Looking for Mesh Proxy Service: {MESH_PROXY_SERVICE_UUID}")
        mesh_proxy_service = services.get_service(MESH_PROXY_SERVICE_UUID)
        if mesh_proxy_service:
            _LOGGER.info("[MESH] Mesh Proxy service found")
            self._data_in_char = mesh_proxy_service.get_characteristic(MESH_PROXY_DATA_IN_UUID)
            self._data_out_char = mesh_proxy_service.get_characteristic(MESH_PROXY_DATA_OUT_UUID)
            _LOGGER.info(f"[MESH] Data IN characteristic: {'found' if self._data_in_char else 'not found'}")
            _LOGGER.info(f"[MESH] Data OUT characteristic: {'found' if self._data_out_char else 'not found'}")

            if self._data_out_char:
                _LOGGER.info(f"[MESH] Data OUT properties: {self._data_out_char.properties}")
                if "notify" in self._data_out_char.properties or "indicate" in self._data_out_char.properties:
                    await self.client.start_notify(self._data_out_char, self._on_data_received)
                    _LOGGER.info("[MESH] Notifications enabled on Data OUT characteristic")
                else:
                    _LOGGER.warning("[MESH] Data OUT characteristic does not support notifications/indications")
            else:
                _LOGGER.warning("[MESH] Data OUT characteristic not found")
        else:
            _LOGGER.warning(f"[MESH] Mesh Proxy service not found. Available services: {[s.uuid for s in services]}")

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
        _LOGGER.info(f"[MESH] Received Mesh data: {data.hex()} (len={len(data)})")
        try:
            parsed = parse_proxy_pdu(bytes(data))
            _LOGGER.info(f"[MESH] Proxy PDU parsed: {parsed}")
            
            if parsed["is_segmented"]:
                _LOGGER.info(f"[MESH] SAR segment: offset={parsed['segment_offset']}, last={parsed['last_segment']}")
                network_pdu = self._handle_sar_receive(parsed)
                if not network_pdu:
                    _LOGGER.info(f"[MESH] SAR buffer incomplete, waiting for more segments")
                    return
            else:
                network_pdu = parsed["network_pdu"]
            
            _LOGGER.info(f"[MESH] Network PDU: {network_pdu.hex()} (len={len(network_pdu)})")
            
            message = self._parse_network_pdu(network_pdu)
            _LOGGER.info(f"[MESH] Parsed message: {message}")
            
            if message and self._message_callback:
                self._message_callback(message)
            elif not message:
                _LOGGER.warning(f"[MESH] Message parsing returned None")
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to parse Mesh message: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")

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
        if not self._encryption_key or not self._privacy_key:
            _LOGGER.error("[MESH] Keys not derived, cannot decrypt - check if set_keys() was called")
            return None
        
        if len(network_pdu) < 12:
            _LOGGER.error(f"[MESH] Network PDU too short: {len(network_pdu)} bytes (need at least 12)")
            return None
        
        first_byte = network_pdu[0]
        nid = first_byte & 0x7F
        expected_nid = self._nid
        if expected_nid and nid != expected_nid:
            _LOGGER.error(f"[MESH] NID mismatch: expected 0x{expected_nid:02X}, got 0x{nid:02X}")
            return None
        
        _LOGGER.info(f"[MESH] Decrypting network PDU: {network_pdu.hex()[:40]}...")
        _LOGGER.info(f"[MESH] Using encryption_key={self._encryption_key.hex()[:16]}..., privacy_key={self._privacy_key.hex()[:16]}..., iv_index={self.iv_index}")
        
        cleartext, info = decrypt_network_pdu(
            network_pdu, self._encryption_key, self._privacy_key, self.iv_index
        )
        
        if cleartext is None:
            _LOGGER.error(f"[MESH] Network decryption failed: {info}")
            return None
        
        dst = struct.unpack(">H", cleartext[:2])[0]
        transport_pdu = cleartext[2:]
        
        _LOGGER.info(f"[MESH] Network decrypted: DST=0x{dst:04X}, SRC=0x{info['src']:04X}, SEQ={info['seq']}, IVI={info['iv_index']}, TransportPDU={transport_pdu.hex()}")
        
        result = {
            "ctl": info["ctl"], "ttl": info["ttl"],
            "seq": info["seq"], "src": info["src"],
            "dst": dst
        }
        
        if len(transport_pdu) >= 5:
            akf_aid = transport_pdu[0]
            akf = (akf_aid >> 6) & 0x01
            aid = akf_aid & 0x3F
            result["akf"] = akf
            result["aid"] = aid
            
            _LOGGER.info(f"[MESH] Transport PDU: akf={akf}, aid={aid}, len={len(transport_pdu)}")
            _LOGGER.info(f"[MESH] Trying upper transport decryption with src=0x{info['src']:04X}, dst=0x{dst:04X}, seq={info['seq']}, iv_index={info['iv_index']}")
            
            plaintext = self._try_decrypt_upper_transport(
                transport_pdu, akf, aid,
                info["iv_index"], info["seq"], info["src"], dst
            )
            
            if plaintext:
                _LOGGER.info(f"[MESH] Upper transport decrypted successfully: {plaintext.hex()}")
                result["payload"] = plaintext.hex()
                result["raw"] = plaintext.hex()
                
                parsed = self._parse_access_payload(plaintext)
                if parsed:
                    _LOGGER.info(f"[MESH] Access payload parsed: type={parsed.get('type')}, opcode={parsed.get('opcode')}")
                    result.update(parsed)
                else:
                    _LOGGER.warning(f"[MESH] Access payload parsing returned None")
            else:
                _LOGGER.error(f"[MESH] Upper transport decryption failed")
        else:
            _LOGGER.warning(f"[MESH] Transport PDU too short: {len(transport_pdu)} bytes")
        
        return result
    
    def _try_decrypt_upper_transport(self, transport_pdu, akf, aid, iv_index, seq, src, dst):
        _LOGGER.info(f"[MESH] _try_decrypt_upper_transport: akf={akf}, aid={aid}, iv_index={iv_index}, seq={seq}, src=0x{src:04X}, dst=0x{dst:04X}")
        
        if akf == 1:
            primary_key = self.app_key
            primary_use_app = True
            fallback_key = self.address_to_device_key.get(src) or self.address_to_device_key.get(dst)
            fallback_use_app = False
            primary_label = "AppKey"
            fallback_label = "DeviceKey"
        else:
            primary_key = self.address_to_device_key.get(src) or self.address_to_device_key.get(dst)
            primary_use_app = False
            fallback_key = self.app_key
            fallback_use_app = True
            primary_label = "DeviceKey"
            fallback_label = "AppKey"
        
        _LOGGER.info(f"[MESH] Primary: {primary_label}, key={'set' if primary_key else 'None'}, use_app={primary_use_app}")
        _LOGGER.info(f"[MESH] Fallback: {fallback_label}, key={'set' if fallback_key else 'None'}, use_app={fallback_use_app}")
        
        if primary_key:
            _LOGGER.info(f"[MESH] Trying {primary_label} decryption...")
            plaintext, info = decrypt_upper_transport(
                transport_pdu, primary_key,
                iv_index, seq, src, dst,
                use_app_key=primary_use_app
            )
            if plaintext:
                _LOGGER.info(f"[MESH] SUCCESS: Decrypted with {primary_label}")
                return plaintext
            _LOGGER.info(f"[MESH] {primary_label} decryption failed: {info}")
        else:
            _LOGGER.warning(f"[MESH] No {primary_label} available, trying {fallback_label}")
        
        if fallback_key and fallback_key != primary_key:
            _LOGGER.info(f"[MESH] Trying {fallback_label} decryption...")
            plaintext, info = decrypt_upper_transport(
                transport_pdu, fallback_key,
                iv_index, seq, src, dst,
                use_app_key=fallback_use_app
            )
            if plaintext:
                _LOGGER.info(f"[MESH] SUCCESS: Decrypted with {fallback_label} (fallback)")
                return plaintext
            _LOGGER.error(f"[MESH] {fallback_label} decryption also failed: {info}")
        
        _LOGGER.error(f"[MESH] All decryption attempts failed")
        return None

    def _parse_access_payload(self, payload):
        if len(payload) < 2:
            return None
        
        first_byte = payload[0]
        
        if (first_byte & 0xC0) == 0xC0:
            vendor_parsed = parse_vendor_model_message(payload)
            if vendor_parsed:
                result = {
                    "type": "vendor_model",
                    "opcode": vendor_parsed["opcode"],
                    "company_id": vendor_parsed.get("company_id"),
                    "vendor_model_id": vendor_parsed.get("vendor_model_id"),
                    "parameters": vendor_parsed.get("parameters", b"").hex()
                }
                
                if vendor_parsed.get("company_id") == LEITE_COMPANY_ID:
                    result["type"] = "leite_vendor_model"
                    self._parse_leite_vendor_message(result, vendor_parsed)
                
                return result
            return {"type": "unknown_vendor", "raw": payload.hex()}
        
        if (first_byte & 0x80) == 0x80:
            if len(payload) < 3:
                return None
            opcode = (first_byte << 8) | payload[1]
            params = payload[2:]
            result = {"opcode": opcode, "params": params.hex()}
            
            if opcode == 0x8411:
                result["type"] = "generic_onoff_status"
                result["on"] = params[0] == 1 if len(params) >= 1 else None
            elif opcode == 0x8410:
                result["type"] = "generic_onoff_get"
            elif opcode == 0x8211:
                result["type"] = "generic_onoff_set"
                result["on"] = params[0] == 1 if len(params) >= 1 else None
            elif opcode == 0x8405:
                result["type"] = "generic_level_status"
                result["level"] = int.from_bytes(params[:2], "big", signed=True) if len(params) >= 2 else None
            elif opcode == 0x8205:
                result["type"] = "generic_level_set"
                result["level"] = int.from_bytes(params[:2], "big", signed=True) if len(params) >= 2 else None
            elif opcode == 0x8406:
                result["type"] = "generic_level_get"
            elif opcode == 0x8202:
                result["type"] = "generic_default_set"
            elif opcode == 0x8402:
                result["type"] = "generic_default_status"
            elif opcode == 0x8408:
                result["type"] = "generic_power_onoff_status"
                result["state"] = params[0] if len(params) >= 1 else None
            
            return result
        
        opcode = first_byte
        params = payload[1:]
        result = {"opcode": opcode, "params": params.hex()}
        
        if opcode == 0x02:
            result["type"] = "generic_onoff_get"
        elif opcode == 0x03:
            result["type"] = "generic_level_get"
        
        return result

    def _parse_leite_vendor_message(self, result, vendor_parsed):
        opcode = vendor_parsed["opcode"]
        params = vendor_parsed.get("parameters", b"")
        
        if opcode == 0x01:
            result["sub_type"] = "device_control"
            self._parse_leite_control_data(result, params)
        elif opcode == 0x02:
            result["sub_type"] = "device_status"
            self._parse_leite_control_data(result, params)
        elif opcode == 0x03:
            result["sub_type"] = "brightness"
            if len(params) >= 2:
                result["brightness"] = int.from_bytes(params[:2], "big")
        elif opcode == 0x04:
            result["sub_type"] = "color_temp"
            if len(params) >= 2:
                result["color_temp_mired"] = int.from_bytes(params[:2], "big")
    
    def _parse_leite_control_data(self, result, params):
        if len(params) >= 7 and params[0] == 0x66 and params[1] == 0xBB:
            cmd_subtype = params[5]
            
            if cmd_subtype == 0x00:
                value = params[6]
                result["on"] = value == 1
                result["status"] = value
                result["status_byte"] = value
            elif cmd_subtype == 0x01:
                value = params[6]
                result["brightness"] = value
            elif cmd_subtype == 0x02:
                if len(params) >= 9:
                    temp_raw = int.from_bytes(params[6:8], "big")
                    result["color_temp_mired"] = temp_raw
            
            result["control_data"] = params.hex()
            return
        
        if len(params) >= 1:
            result["status"] = params[0]
            result["status_byte"] = params[0]
            result["on"] = params[0] == 1
        if len(params) >= 2:
            result["brightness"] = int.from_bytes(params[:2], "big") if len(params) >= 2 else None

    async def _build_and_send_network_pdu(self, destination: int, access_payload: bytes,
                                          akf: int = 1, aid: int = None):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False
        
        if not self._encryption_key:
            _LOGGER.warning("[MESH] Encryption key not set")
            return False
        
        if aid is None:
            aid = self._app_aid or 0
        
        # 根据 akf 选择加密密钥
        app_key = self.app_key
        device_key = None
        
        if akf == 0:
            # Vendor Model 使用 DeviceKey
            device_key = self.address_to_device_key.get(destination)
            if device_key is None:
                _LOGGER.warning(f"[MESH] No device key for address 0x{destination:04X}, trying app_key fallback")
        
        async with self._seq_lock:
            seq = self.seq_number
            self.seq_number = (self.seq_number + 1) % (2**48)
        
        access_pdu = build_access_message(destination, akf, aid, access_payload)
        
        _LOGGER.info(f"[MESH] Building network PDU: dst=0x{destination:04X}, akf={akf}, aid={aid}, seq={seq}, use_device_key={device_key is not None}")
        
        network_pdu = build_network_pdu(
            ctl=0, ttl=DEFAULT_TTL,
            seq=seq, src=self._local_address, dst=destination,
            access_pdu=access_pdu,
            enc_key=self._encryption_key, priv_key=self._privacy_key,
            iv_index=self.iv_index,
            nid=self._nid,
            app_key=app_key, akf=akf, aid=aid,
            device_key=device_key
        )
        
        proxy_pdus = segment_network_pdu(network_pdu, self.mtu)
        
        for pdu in proxy_pdus:
            await self.client.write_gatt_char(self._data_in_char, pdu, response=False)
            _LOGGER.debug(f"[MESH] Sent PDU: {len(pdu)} bytes")
        
        _LOGGER.info(f"[MESH] Sent network PDU to 0x{destination:04X}: seq={seq}, akf={akf}, pdu_len={len(network_pdu)}")
        return True

    async def send_vendor_model_message(self, device_id: str, opcode: int, parameters: bytes, acknowledged: bool = False, akf: int = 0):
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            address = self.device_addresses.get(device_id, 0)
            if address == 0:
                _LOGGER.warning(f"[MESH] No address found for device {device_id}")
                return False

            vendor_payload = build_vendor_model_message(opcode, parameters)
            aid = self._app_aid or 0
            
            _LOGGER.info(f"[MESH] Sending vendor model: device={device_id}, addr=0x{address:04X}, opcode=0x{opcode:04X}, akf={akf}, aid={aid}, params_len={len(parameters)}")
            _LOGGER.debug(f"[MESH] Vendor payload hex: {vendor_payload.hex()}")
            
            result = await self._build_and_send_network_pdu(address, vendor_payload, akf=akf, aid=aid)
            
            if result:
                _LOGGER.info(f"[MESH] Sent vendor model to {device_id} (addr=0x{address:04X}): opcode=0x{opcode:04X}, akf={akf}")
            else:
                _LOGGER.error(f"[MESH] Failed to send vendor model to {device_id}")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send vendor model: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
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
            # Vendor Model 消息使用 akf=0 (DeviceKey)
            result = await self.send_vendor_model_message(device_id, 0x01, parameters, acknowledged=True, akf=0)
            
            if result:
                _LOGGER.info(f"[MESH] Sent control to {device_id} (addr=0x{address:04X}), data={control_data}")
            else:
                _LOGGER.error(f"[MESH] Failed to send control to {device_id}")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send device control: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
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

            _LOGGER.info(f"[MESH] Preparing to set {device_id} (addr=0x{address:04X}) to {'ON' if on else 'OFF'}")
            _LOGGER.info(f"[MESH] Net key: {self.net_key.hex() if self.net_key else 'None'}")
            _LOGGER.info(f"[MESH] App key: {self.app_key.hex() if self.app_key else 'None'}")
            _LOGGER.info(f"[MESH] NID: 0x{self._nid:02X}, IV Index: {self.iv_index}")
            _LOGGER.info(f"[MESH] Encryption key derived: {self._encryption_key is not None}")
            
            # 尝试标准 Generic OnOff 模型 (akf=1, 使用 AppKey)
            _LOGGER.info(f"[MESH] Trying Generic OnOff {'ON' if on else 'OFF'} via standard model (akf=1)...")
            generic_result = await self.send_generic_onoff(address, on)
            
            if generic_result:
                _LOGGER.info(f"[MESH] Successfully set {device_id} via Generic OnOff model")
                return True
            
            # 如果标准模型失败，回退到 Vendor Model (akf=0, 使用 DeviceKey)
            _LOGGER.warning(f"[MESH] Generic OnOff failed, falling back to Vendor Model (akf=0)...")
            control_data = "66BB0000000001EB" if on else "66BB0000000000EB"
            parameters = hex_to_bytes(control_data)
            
            result = await self.send_vendor_model_message(device_id, 0x01, parameters, akf=0)
            
            if result:
                _LOGGER.info(f"[MESH] Set {device_id} via Vendor Model (addr=0x{address:04X}), data={control_data}")
            else:
                _LOGGER.error(f"[MESH] Both Generic OnOff and Vendor Model failed for {device_id}")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to set device on/off: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
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

            # 将亮度从 0-255 转换为 -32768-32767 (Generic Level)
            # 或者直接使用 0-100 的百分比值
            brightness_percent = int((brightness / 255) * 100)
            generic_level = int((brightness / 255) * 65535) - 32768  # 转换为有符号 16 位
            
            _LOGGER.info(f"[MESH] Trying Generic Level set for {device_id}, brightness={brightness}, level={generic_level}")
            
            # 尝试标准 Generic Level 模型 (akf=1, 使用 AppKey)
            generic_result = await self.send_generic_level(address, generic_level)
            
            if generic_result:
                _LOGGER.info(f"[MESH] Successfully set {device_id} brightness via Generic Level model")
                return True
            
            # 如果标准模型失败，回退到 Vendor Model (akf=0, 使用 DeviceKey)
            _LOGGER.warning(f"[MESH] Generic Level failed, falling back to Vendor Model (akf=0)...")
            control_data = f"66BB00000001{brightness_percent:02X}EB"
            parameters = hex_to_bytes(control_data)
            
            result = await self.send_vendor_model_message(device_id, 0x01, parameters, akf=0)
            
            if result:
                _LOGGER.info(f"[MESH] Set {device_id} brightness via Vendor Model, data={control_data}")
            else:
                _LOGGER.error(f"[MESH] Both Generic Level and Vendor Model failed for {device_id} brightness")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to set device brightness: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
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

            _LOGGER.info(f"[MESH] Setting color temp for {device_id}, color_temp={color_temp}")
            
            # 先回退到 Vendor Model (akf=0, 使用 DeviceKey) - 因为色温没有标准的 HSL/CTL 模型直接支持
            control_data = f"66BB00000002{color_temp:04X}EB"
            parameters = hex_to_bytes(control_data)
            
            result = await self.send_vendor_model_message(device_id, 0x01, parameters, akf=0)
            
            if result:
                _LOGGER.info(f"[MESH] Set {device_id} color temp via Vendor Model, data={control_data}")
            else:
                _LOGGER.error(f"[MESH] Failed to set color temp for {device_id}")
            return result
            
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to set device color temp: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
            return False

    def is_connected(self):
        return self.connected

    def get_encryption_key(self):
        return self._encryption_key

    def get_privacy_key(self):
        return self._privacy_key

    def get_nid(self):
        return self._nid

    async def send_generic_onoff(self, device_address: int, on: bool) -> bool:
        """Send Generic OnOff Set message (standard Mesh model)."""
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            # Generic OnOff Set: opcode 0x82, params=[on]
            access_payload = bytes([0x82, 0x01 if on else 0x00])
            access_pdu = build_access_message(device_address, 1, self._app_aid or 0, access_payload)

            async with self._seq_lock:
                seq = self.seq_number
                self.seq_number = (self.seq_number + 1) % (2**48)

            network_pdu = build_network_pdu(
                ctl=0, ttl=DEFAULT_TTL,
                seq=seq, src=self._local_address, dst=device_address,
                access_pdu=access_pdu,
                enc_key=self._encryption_key, priv_key=self._privacy_key,
                iv_index=self.iv_index,
                nid=self._nid,
                app_key=self.app_key, akf=1, aid=self._app_aid or 0
            )

            proxy_pdus = segment_network_pdu(network_pdu, self.mtu)
            for pdu in proxy_pdus:
                await self.client.write_gatt_char(self._data_in_char, pdu, response=False)

            _LOGGER.info(f"[MESH] Sent Generic OnOff {'ON' if on else 'OFF'} to 0x{device_address:04X}")
            return True
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send Generic OnOff: {e}")
            return False

    async def send_generic_level(self, device_address: int, level: int) -> bool:
        """Send Generic Level Set message (standard Mesh model)."""
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            # Generic Level Set: opcode 0x84, params=[level(2, signed BE)]
            level_clamped = max(-32768, min(32767, level))
            access_payload = bytes([0x84]) + struct.pack(">h", level_clamped)
            access_pdu = build_access_message(device_address, 1, self._app_aid or 0, access_payload)

            async with self._seq_lock:
                seq = self.seq_number
                self.seq_number = (self.seq_number + 1) % (2**48)

            network_pdu = build_network_pdu(
                ctl=0, ttl=DEFAULT_TTL,
                seq=seq, src=self._local_address, dst=device_address,
                access_pdu=access_pdu,
                enc_key=self._encryption_key, priv_key=self._privacy_key,
                iv_index=self.iv_index,
                nid=self._nid,
                app_key=self.app_key, akf=1, aid=self._app_aid or 0
            )

            proxy_pdus = segment_network_pdu(network_pdu, self.mtu)
            for pdu in proxy_pdus:
                await self.client.write_gatt_char(self._data_in_char, pdu, response=False)

            _LOGGER.info(f"[MESH] Sent Generic Level {level} to 0x{device_address:04X}")
            return True
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send Generic Level: {e}")
            return False

    async def send_vendor_model(self, device_address: int, opcode: int,
                                parameters: bytes = b"", app_key_index: int = 0) -> bool:
        """Send Vendor Model message (using DeviceKey, akf=0)."""
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            vendor_payload = build_vendor_model_message(opcode, parameters)
            
            # Vendor Model 使用 akf=0 (DeviceKey)
            akf = 0
            aid = 0  # Vendor Model 的 AID 为 0
            
            # 查找设备密钥
            device_key = self.address_to_device_key.get(device_address)
            
            async with self._seq_lock:
                seq = self.seq_number
                self.seq_number = (self.seq_number + 1) % (2**48)

            network_pdu = build_network_pdu(
                ctl=0, ttl=DEFAULT_TTL,
                seq=seq, src=self._local_address, dst=device_address,
                access_pdu=vendor_payload,
                enc_key=self._encryption_key, priv_key=self._privacy_key,
                iv_index=self.iv_index,
                nid=self._nid,
                app_key=self.app_key, akf=akf, aid=aid,
                device_key=device_key
            )

            proxy_pdus = segment_network_pdu(network_pdu, self.mtu)
            for pdu in proxy_pdus:
                await self.client.write_gatt_char(self._data_in_char, pdu, response=False)

            _LOGGER.info(f"[MESH] Sent Vendor Model opcode=0x{opcode:04X} to 0x{device_address:04X}, akf={akf}, has_device_key={device_key is not None}")
            return True
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send Vendor Model: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
            return False
