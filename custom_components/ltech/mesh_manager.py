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
        self._nid_validated = False  # set True when a PDU with matching NID is received
        self._known_device_macs = set()  # known Ltech device MACs for scan prioritization
        
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

    def set_known_device_macs(self, macs: list):
        """Set list of known Ltech device BLE MAC addresses (uppercase, colon-separated).
        These will be prioritized when scanning for Mesh Proxy candidates."""
        self._known_device_macs = set((m or "").upper() for m in macs)
        _LOGGER.info(f"[MESH] Set {len(self._known_device_macs)} known device MACs for scan prioritization")
    
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
        """Scan for Mesh devices. Returns list of (BLEDevice, rssi) tuples."""
        devices = []
        try:
            _LOGGER.info(f"[MESH] Scanning for Bluetooth Mesh devices (timeout={timeout}s)...")
            # Use return_adv=True to access advertisement data (service UUIDs, RSSI)
            found = await BleakScanner.discover(timeout=timeout, return_adv=True)
            _LOGGER.info(f"[MESH] Found {len(found)} Bluetooth devices total")

            mesh_proxy_uuid_lower = MESH_PROXY_SERVICE_UUID.lower()
            mesh_prov_uuid_lower = MESH_PROVISIONING_SERVICE_UUID.lower()

            for address, (device, adv_data) in found.items():
                rssi = getattr(adv_data, 'rssi', None) or getattr(device, 'rssi', None)
                name = device.name or getattr(adv_data, 'local_name', None) or '(no name)'
                service_uuids = list(getattr(adv_data, 'service_uuids', None) or [])
                uuids_lower = [u.lower() for u in service_uuids]

                _LOGGER.info(f"[MESH] Device: name={name}, address={address}, RSSI={rssi}, service_uuids={service_uuids}")

                # Match by Mesh Proxy / Provisioning service UUID (most reliable)
                is_mesh_by_uuid = (
                    mesh_proxy_uuid_lower in uuids_lower or
                    mesh_prov_uuid_lower in uuids_lower
                )

                # Match by name (fallback)
                name_lower = (device.name or '').lower()
                is_mesh_by_name = bool(device.name) and (
                    "ltech" in name_lower or
                    "lt" in name_lower or
                    "mesh" in name_lower or
                    "gateway" in name_lower or
                    "ainice" in name_lower or
                    "smart" in name_lower
                )

                if is_mesh_by_uuid or is_mesh_by_name:
                    match_reason = "service_uuid" if is_mesh_by_uuid else "name"
                    devices.append((device, rssi))
                    _LOGGER.info(f"[MESH] Found matching Mesh device (by {match_reason}): {name} ({address}) RSSI={rssi}")

            # Sort: known Ltech device MACs first (descending by RSSI), then others (descending by RSSI)
            def sort_key(item):
                device, rssi = item
                addr_upper = (device.address or "").upper()
                is_known = addr_upper in self._known_device_macs
                # (not is_known) so known devices sort first (False < True)
                return (not is_known, -(rssi if rssi is not None else -100))

            devices.sort(key=sort_key)
            _LOGGER.info(f"[MESH] Matched {len(devices)} Mesh device(s), sorted (known Ltech MACs first, then by RSSI)")
            for i, (dev, rssi) in enumerate(devices):
                addr_upper = (dev.address or "").upper()
                known_tag = " [KNOWN-LTECH]" if addr_upper in self._known_device_macs else ""
                _LOGGER.info(f"[MESH]   #{i+1}: {dev.name or '(no name)'} ({dev.address}) RSSI={rssi}{known_tag}")

            if not devices:
                _LOGGER.warning("[MESH] No matching Mesh devices found. Showing all devices:")
                for address, (device, adv_data) in found.items():
                    rssi = getattr(adv_data, 'rssi', None) or getattr(device, 'rssi', None)
                    name = device.name or getattr(adv_data, 'local_name', None) or '(no name)'
                    service_uuids = list(getattr(adv_data, 'service_uuids', None) or [])
                    _LOGGER.warning(f"[MESH]   {name} ({address}) RSSI={rssi} uuids={service_uuids}")

        except Exception as e:
            _LOGGER.error(f"[MESH] Scan failed: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
        return devices

    async def connect(self, device_address: Optional[str] = None):
        _LOGGER.info(f"[MESH] Connect called: device_address={device_address}, currently_connected={self.connected}")

        # Always clean up any existing client first. A previous failed connect may have
        # left a stale client / underlying HCI connection that leaks BLE slots.
        if self.client is not None or self.connected:
            _LOGGER.info("[MESH] Cleaning up existing client before connecting")
            await self.disconnect()

        try:
            candidates = []
            if device_address:
                # Use the explicitly provided address
                _LOGGER.info(f"[MESH] Looking for device by address: {device_address}")
                dev = await BleakScanner.find_device_by_address(device_address)
                if not dev:
                    _LOGGER.error(f"[MESH] Device {device_address} not found by address")
                    return
                candidates = [(dev, None)]
            else:
                _LOGGER.info("[MESH] No device address provided, scanning...")
                candidates = await self.scan_for_mesh_devices(timeout=5)
                if not candidates:
                    _LOGGER.warning("[MESH] No Mesh devices found during scan")
                    _LOGGER.warning("[MESH] Please ensure:")
                    _LOGGER.warning("[MESH]   1. The Ltech gateway is powered on")
                    _LOGGER.warning("[MESH]   2. The gateway is within Bluetooth range (10-30 meters)")
                    _LOGGER.warning("[MESH]   3. Your Mac's Bluetooth is turned on")
                    return

            # Try each candidate until one connects successfully with Mesh Proxy service.
            # Limit to 2 attempts to reduce BLE slot leakage from failed connections.
            max_attempts = min(len(candidates), 2)
            for idx, (device, rssi) in enumerate(candidates[:max_attempts]):
                _LOGGER.info(f"[MESH] Attempt {idx+1}/{max_attempts}: {device.name or '(no name)'} ({device.address}) RSSI={rssi}")
                try:
                    self.device = device
                    self._nid_validated = False  # reset validation flag per attempt
                    _LOGGER.info("[MESH] Establishing BLE connection via bleak_retry_connector...")

                    # Use bleak_retry_connector for HA-compatible connection management
                    try:
                        from bleak_retry_connector import establish_connection
                        self.client = await establish_connection(
                            BleakClient, device, device.address,
                        )
                    except ImportError:
                        _LOGGER.warning("[MESH] bleak_retry_connector not available, using direct BleakClient")
                        self.client = BleakClient(device, timeout=15.0)
                        await self.client.connect()

                    _LOGGER.info("[MESH] BLE connection established")

                    _LOGGER.info("[MESH] Discovering services...")
                    await self._discover_services()

                    if not self._data_in_char or not self._data_out_char:
                        _LOGGER.warning(f"[MESH] No Mesh Proxy service on {device.address}, disconnecting and trying next")
                        try:
                            await self.client.disconnect()
                        except Exception:
                            pass
                        self.client = None
                        self.device = None
                        continue

                    try:
                        await self.client.request_mtu(DEFAULT_MTU)
                        self.mtu = DEFAULT_MTU
                        _LOGGER.info(f"[MESH] MTU set to {self.mtu}")
                    except Exception as e:
                        _LOGGER.warning(f"[MESH] Failed to set MTU: {e}, using default 23")

                    # NID validation: skip for known Ltech device MACs (already trusted),
                    # otherwise wait up to 4s for a PDU with matching NID.
                    addr_upper = (device.address or "").upper()
                    is_known_ltech = addr_upper in self._known_device_macs
                    if self._nid is not None and not is_known_ltech:
                        _LOGGER.info(f"[MESH] Waiting up to 4s for a PDU with matching NID to validate network (unknown device {device.address})...")
                        for wait_i in range(40):
                            if self._nid_validated:
                                break
                            await asyncio.sleep(0.1)

                        if not self._nid_validated:
                            _LOGGER.warning(f"[MESH] NID not validated on {device.address} (no matching PDU received), trying next device")
                            try:
                                await self.client.disconnect()
                            except Exception:
                                pass
                            self.client = None
                            self.device = None
                            self._data_in_char = None
                            self._data_out_char = None
                            continue
                        _LOGGER.info(f"[MESH] NID validated on {device.address}, this is the correct Mesh network")
                    elif is_known_ltech:
                        _LOGGER.info(f"[MESH] Skipping NID validation for known Ltech device {device.address}")

                    self.connected = True
                    _LOGGER.info(f"[MESH] Connected to Mesh network via {device.address}, mtu={self.mtu}")

                    if self._reconnect_task:
                        self._reconnect_task.cancel()
                    self._reconnect_task = asyncio.create_task(self._reconnect_loop())
                    _LOGGER.info("[MESH] Reconnect loop started")
                    return

                except Exception as e:
                    _LOGGER.warning(f"[MESH] Connection to {device.address} failed: {e}")
                    try:
                        if self.client:
                            await self.client.disconnect()
                    except Exception:
                        pass
                    self.client = None
                    self.device = None
                    # Clean up any stale connections leaked by failed establish_connection
                    try:
                        from bleak_retry_connector import close_stale_connections
                        await close_stale_connections(device.address)
                        _LOGGER.info(f"[MESH] Cleaned stale connections for {device.address}")
                    except Exception as cleanup_err:
                        _LOGGER.debug(f"[MESH] close_stale_connections failed (may not be available): {cleanup_err}")
                    continue

            _LOGGER.error(f"[MESH] Failed to connect to any of the {max_attempts} candidate device(s)")
            self.connected = False

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
        try:
            # In newer bleak/HA wrapper, services is a property that auto-populates
            services = self.client.services
        except Exception as e:
            _LOGGER.warning(f"[MESH] Failed to access .services property: {e}, trying get_services()")
            try:
                services = await self.client.get_services()
            except Exception as e2:
                _LOGGER.error(f"[MESH] Both services access methods failed: {e2}")
                raise

        # services may be a BleakGATTServiceCollection or a list depending on backend
        try:
            service_iter = list(services.services.values()) if hasattr(services, 'services') else list(services)
        except Exception:
            service_iter = list(services)

        _LOGGER.info(f"[MESH] Found {len(service_iter)} services")

        for service in service_iter:
            _LOGGER.info(f"[MESH] Service: {service.uuid}")
            for char in service.characteristics:
                _LOGGER.info(f"[MESH]   Characteristic: {char.uuid}, properties={char.properties}")

        _LOGGER.info(f"[MESH] Looking for Mesh Proxy Service: {MESH_PROXY_SERVICE_UUID}")
        mesh_proxy_service = None
        try:
            mesh_proxy_service = services.get_service(MESH_PROXY_SERVICE_UUID)
        except Exception:
            # Fallback: iterate to find by UUID
            for s in service_iter:
                if s.uuid.lower() == MESH_PROXY_SERVICE_UUID.lower():
                    mesh_proxy_service = s
                    break

        if mesh_proxy_service:
            _LOGGER.info(f"[MESH] Mesh Proxy service found: {mesh_proxy_service.uuid}")
            # Find Data IN / Data OUT characteristics
            self._data_in_char = None
            self._data_out_char = None
            for char in mesh_proxy_service.characteristics:
                char_uuid_lower = char.uuid.lower()
                if char_uuid_lower == MESH_PROXY_DATA_IN_UUID.lower():
                    self._data_in_char = char
                elif char_uuid_lower == MESH_PROXY_DATA_OUT_UUID.lower():
                    self._data_out_char = char

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
            _LOGGER.warning(f"[MESH] Mesh Proxy service not found. Available services: {[s.uuid for s in service_iter]}")

    async def disconnect(self):
        if self._reconnect_task:
            self._reconnect_task.cancel()
            self._reconnect_task = None

        # Unconditionally attempt to disconnect the client. The HA bleak wrapper's
        # is_connected can report False while the underlying HCI connection is still
        # open (state desync), which leaks BLE connection slots (slots=0/5).
        if self.client:
            try:
                # Force disconnect even if wrapper thinks it's already disconnected
                await self.client.disconnect()
                _LOGGER.info("[MESH] Client disconnect() called")
            except Exception as e:
                _LOGGER.warning(f"[MESH] Disconnect call failed (may already be disconnected): {e}")

        self.connected = False
        self.client = None
        self.device = None
        self._data_in_char = None
        self._data_out_char = None
        self._nid_validated = False
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

        # NID matches (or no expected NID set) - mark connection as validated
        if not self._nid_validated:
            self._nid_validated = True
            _LOGGER.info(f"[MESH] NID validated: 0x{nid:02X} matches expected network")
        
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
        """Parse Access Message payload per Bluetooth Mesh spec.
        
        Opcode length determination based on first byte high 2 bits:
        - 00: 1-byte opcode
        - 01: 2-byte opcode
        - 10: 3-byte opcode (standard models)
        - 11: 3-byte opcode (vendor models)
        """
        if len(payload) < 2:
            return None
        
        first_byte = payload[0]
        opcode_type = (first_byte & 0xC0) >> 6  # Get high 2 bits
        
        # Vendor model messages (3-byte, high 2 bits = 11)
        if opcode_type == 3:  # 11
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
        
        # Standard model messages
        if opcode_type == 2:  # 10 - 3-byte opcode
            if len(payload) < 4:
                return None
            opcode = (first_byte << 16) | (payload[1] << 8) | payload[2]
            params = payload[3:]
        elif opcode_type == 1:  # 01 - 2-byte opcode
            if len(payload) < 3:
                return None
            opcode = (first_byte << 8) | payload[1]
            params = payload[2:]
        else:  # 00 - 1-byte opcode
            opcode = first_byte
            params = payload[1:]
        
        result = {"opcode": opcode, "params": params.hex()}
        
        # Common standard model opcodes
        if opcode == 0x8202:  # Generic OnOff Status
            result["type"] = "generic_onoff_status"
            result["on"] = params[0] == 1 if len(params) >= 1 else None
        elif opcode == 0x8201:  # Generic OnOff Set
            result["type"] = "generic_onoff_set"
            result["on"] = params[1] == 1 if len(params) >= 2 else None  # params[0]=TID, params[1]=OnOff
        elif opcode == 0x8200:  # Generic OnOff Get
            result["type"] = "generic_onoff_get"
        elif opcode == 0x8402:  # Generic Level Status
            result["type"] = "generic_level_status"
            result["level"] = int.from_bytes(params[:2], "big", signed=True) if len(params) >= 2 else None
        elif opcode == 0x8401:  # Generic Level Set
            result["type"] = "generic_level_set"
            result["level"] = int.from_bytes(params[1:3], "big", signed=True) if len(params) >= 3 else None  # params[0]=TID
        elif opcode == 0x8400:  # Generic Level Get
            result["type"] = "generic_level_get"
        elif opcode == 0x8405:  # Generic Default Status
            result["type"] = "generic_default_status"
        elif opcode == 0x8408:  # Generic Power OnOff Status
            result["type"] = "generic_power_onoff_status"
            result["state"] = params[0] if len(params) >= 1 else None
        
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
            # akf=0 (DeviceKey) 时 AID 必须为 0; akf=1 (AppKey) 时使用 AppKey AID
            aid = 0 if akf == 0 else (self._app_aid or 0)
            
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

    async def set_device_on(self, device_id: str, on: bool, zone: int = 1):
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

            # Vendor Model OnOff (opcode=0xC7) using AppKey (akf=1).
            # Verified from Ltech APP observed network traffic:
            #   c71111000101 = zone1 ON, c71111000100 = zone1 OFF
            #   format: [opcode 0xC7][company_id 0x1111 LE][zone_hi][zone_lo][state]
            # The zone bitmask is a 16-bit big-endian value (e.g. 0x0001 for zone 1),
            # followed by a single state byte (0x01=ON, 0x00=OFF).
            # This matches CmdBleFactory.setOnOff(zoneBitmask, onoff) in the Ltech APP:
            #   zoneBitmask=1 -> bytes 00 01, then state byte.
            _LOGGER.info(f"[MESH] Trying Vendor Model OnOff {'ON' if on else 'OFF'} (opcode=0xC7, akf=1, AppKey) for zone {zone}...")
            zone_bitmask = 1 << (zone - 1)  # zone 1 → bit 0 (0x0001), zone 2 → bit 1 (0x0002), etc.
            state_byte = 0x01 if on else 0x00
            # 16-bit big-endian zone bitmask + state byte = 3 bytes total
            parameters = struct.pack(">H", zone_bitmask) + bytes([state_byte])

            vendor_result = await self.send_vendor_model_message(device_id, 0xC7, parameters, akf=1)

            if vendor_result:
                _LOGGER.info(f"[MESH] Successfully set {device_id} via Vendor Model OnOff (addr=0x{address:04X}), zone={zone}, bitmask=0x{zone_bitmask:04X}, state={state_byte}")
                return True

            # 如果 Vendor Model 失败，回退到标准 Generic OnOff 模型 (akf=1, AppKey)
            _LOGGER.warning(f"[MESH] Vendor Model failed, falling back to Generic OnOff (akf=1)...")
            generic_result = await self.send_generic_onoff(address, on)

            if generic_result:
                _LOGGER.info(f"[MESH] Successfully set {device_id} via Generic OnOff model")
            else:
                _LOGGER.error(f"[MESH] Both Vendor Model and Generic OnOff failed for {device_id}")
            return generic_result

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

            brightness_percent = int((brightness / 255) * 100)
            if brightness_percent < 1:
                brightness_percent = 1

            # Vendor Model Brightness (opcode=0xC4) using AppKey (akf=1).
            # Verified from Ltech APP observed network traffic:
            #   c41111000105 = zone1 brightness=5
            #   format: [opcode 0xC4][company_id 0x1111 LE][zone_hi][zone_lo][brightness]
            # Matches the OnOff parameter layout: 16-bit BE zone bitmask + 1 byte value.
            _LOGGER.info(f"[MESH] Trying Vendor Model brightness for {device_id}, brightness={brightness}, percent={brightness_percent}")
            zone_bitmask = 0x01  # zone 1
            parameters = struct.pack(">H", zone_bitmask) + bytes([brightness_percent])

            vendor_result = await self.send_vendor_model_message(device_id, 0xC4, parameters, akf=1)

            if vendor_result:
                _LOGGER.info(f"[MESH] Successfully set {device_id} brightness via Vendor Model, percent={brightness_percent}")
                return True

            # 如果 Vendor Model 失败，回退到 Generic Level (akf=1, AppKey)
            _LOGGER.warning(f"[MESH] Vendor Model failed, falling back to Generic Level (akf=1)...")
            generic_level = int((brightness / 255) * 65535) - 32768
            generic_result = await self.send_generic_level(address, generic_level)

            if generic_result:
                _LOGGER.info(f"[MESH] Successfully set {device_id} brightness via Generic Level model")
            else:
                _LOGGER.error(f"[MESH] Both Vendor Model and Generic Level failed for {device_id} brightness")
            return generic_result

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

            # Vendor Model Color Temp (opcode=0xC6) using AppKey (akf=1).
            # Verified from Ltech APP observed network traffic:
            #   c611110001d6ff = zone1 color_temp=0xd6ff (LE) = 0xffd6 = 65494 mired
            #   format: [opcode 0xC6][company_id 0x1111 LE][zone_hi][zone_lo][temp_lo][temp_hi]
            # 16-bit BE zone bitmask + 16-bit LE color temp value.
            zone_bitmask = 0x01  # zone 1
            # color_temp is in mired (HA standard). Ltech uses 16-bit LE mired value.
            color_temp_clamped = max(0, min(0xFFFF, int(color_temp)))
            parameters = struct.pack(">H", zone_bitmask) + struct.pack("<H", color_temp_clamped)

            result = await self.send_vendor_model_message(device_id, 0xC6, parameters, akf=1)

            if result:
                _LOGGER.info(f"[MESH] Set {device_id} color temp via Vendor Model, mired={color_temp_clamped}")
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
        """Send Generic OnOff Set message (standard Mesh model, akf=1).
        
        Format: [Opcode(2B)] [TID(1B)] [OnOff(1B)]
        Opcode: 0x8201 (Generic OnOff Set)
        """
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            # Generic OnOff Set: opcode 0x8201
            # TID is incremented for each set command to prevent duplicate processing
            self._tid = (getattr(self, '_tid', 0) + 1) & 0xFF
            onoff_value = 0x01 if on else 0x00
            
            # Build Access Payload: [opcode_hi, opcode_lo, TID, OnOff]
            access_payload = bytes([0x82, 0x01, self._tid, onoff_value])
            
            _LOGGER.info(f"[MESH] Generic OnOff payload: {access_payload.hex()} (ON={on}, TID={self._tid})")
            
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

            _LOGGER.info(f"[MESH] Sent Generic OnOff {'ON' if on else 'OFF'} to 0x{device_address:04X} (TID={self._tid})")
            return True
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send Generic OnOff: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
            return False

    async def send_generic_level(self, device_address: int, level: int) -> bool:
        """Send Generic Level Set message (standard Mesh model, akf=1).
        
        Format: [Opcode(2B)] [TID(1B)] [Level(2B signed BE)]
        Opcode: 0x8401 (Generic Level Set)
        """
        if not self.connected or not self._data_in_char:
            _LOGGER.warning("[MESH] Not connected to Mesh network")
            return False

        try:
            # Generic Level Set: opcode 0x8401
            # TID is incremented for each set command to prevent duplicate processing
            self._tid = (getattr(self, '_tid', 0) + 1) & 0xFF
            level_clamped = max(-32768, min(32767, level))
            
            # Build Access Payload: [opcode_hi, opcode_lo, TID, Level(2B signed BE)]
            access_payload = bytes([0x84, 0x01, self._tid]) + struct.pack(">h", level_clamped)
            
            _LOGGER.info(f"[MESH] Generic Level payload: {access_payload.hex()} (level={level}, TID={self._tid})")
            
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

            _LOGGER.info(f"[MESH] Sent Generic Level {level} to 0x{device_address:04X} (TID={self._tid})")
            return True
        except Exception as e:
            _LOGGER.error(f"[MESH] Failed to send Generic Level: {e}")
            import traceback
            _LOGGER.error(f"[MESH] Traceback: {traceback.format_exc()}")
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
