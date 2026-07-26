import struct
import hashlib
from Crypto.Cipher import AES
from Crypto.Util import Counter


LEITE_COMPANY_ID = 0x1121
LEITE_VENDOR_MODEL_ID = 0x11111111


def k1(net_key, index):
    """K1 function from Mesh Profile Specification.
    
    Derives: NID (1 byte), encryption key (16 bytes), privacy key (16 bytes)
    
    Args:
        net_key: 16-byte network key
        index: key index (2 bytes)
    
    Returns:
        (nid, encryption_key, privacy_key)
    """
    salt = bytes([0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01])
    info = struct.pack("<H", index) + b"\x00"
    
    t = _hkdf(net_key, salt, info, 33)
    nid = t[0:1]
    encryption_key = t[1:17]
    privacy_key = t[17:33]
    
    return nid, encryption_key, privacy_key


def k2(net_key):
    """K2 function from Mesh Profile Specification.
    
    Derives: Identity key (16 bytes), Beacon key (16 bytes), Network ID (8 bytes)
    
    Args:
        net_key: 16-byte network key
    
    Returns:
        (identity_key, beacon_key, network_id)
    """
    salt = bytes([0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02])
    info = b"\x00"
    
    t = _hkdf(net_key, salt, info, 40)
    identity_key = t[0:16]
    beacon_key = t[16:32]
    network_id = t[32:40]
    
    return identity_key, beacon_key, network_id


def k3(net_key):
    """K3 function from Mesh Profile Specification.
    
    Derives: IV update key (16 bytes)
    
    Args:
        net_key: 16-byte network key
    
    Returns:
        iv_update_key (16 bytes)
    """
    salt = bytes([0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03])
    info = b"\x00"
    
    return _hkdf(net_key, salt, info, 16)


def k4(app_key):
    """K4 function from Mesh Profile Specification.
    
    Derives: Transmit key (16 bytes)
    
    Args:
        app_key: 16-byte application key
    
    Returns:
        transmit_key (16 bytes)
    """
    salt = bytes([0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04])
    info = b"\x00"
    
    return _hkdf(app_key, salt, info, 16)


def _hkdf(key, salt, info, length):
    """HKDF implementation (RFC 5869)."""
    prk = hmac_sha256(salt, key)
    return _hkdf_expand(prk, info, length)


def _hkdf_expand(prk, info, length):
    """HKDF Expand step."""
    t = b""
    last_block = b""
    block_index = 1
    
    while len(t) < length:
        last_block = hmac_sha256(prk, last_block + info + bytes([block_index]))
        t += last_block
        block_index += 1
    
    return t[:length]


def hmac_sha256(key, data):
    """HMAC-SHA256 implementation."""
    if len(key) > 64:
        key = hashlib.sha256(key).digest()
    
    if len(key) < 64:
        key = key + b"\x00" * (64 - len(key))
    
    o_key_pad = bytes([k ^ 0x5c for k in key])
    i_key_pad = bytes([k ^ 0x36 for k in key])
    
    inner_hash = hashlib.sha256(i_key_pad + data).digest()
    outer_hash = hashlib.sha256(o_key_pad + inner_hash).digest()
    
    return outer_hash


def aes_ccm_encrypt(key, nonce, plaintext, auth_data=b""):
    """AES-CCM encryption.
    
    Args:
        key: 16-byte encryption key
        nonce: 13-byte nonce (for Mesh)
        plaintext: plaintext to encrypt
        auth_data: additional authentication data
    
    Returns:
        ciphertext + tag (tag is 8 bytes for Mesh)
    """
    cipher = AES.new(key, AES.MODE_CCM, nonce=nonce, mac_len=8)
    cipher.update(auth_data)
    ciphertext, tag = cipher.encrypt_and_digest(plaintext)
    return ciphertext + tag


def aes_ccm_decrypt(key, nonce, ciphertext, auth_data=b""):
    """AES-CCM decryption.
    
    Args:
        key: 16-byte encryption key
        nonce: 13-byte nonce (for Mesh)
        ciphertext: ciphertext + tag (tag is 8 bytes for Mesh)
        auth_data: additional authentication data
    
    Returns:
        plaintext (or None if authentication fails)
    """
    try:
        tag = ciphertext[-8:]
        encrypted_data = ciphertext[:-8]
        cipher = AES.new(key, AES.MODE_CCM, nonce=nonce, mac_len=8)
        cipher.update(auth_data)
        plaintext = cipher.decrypt_and_verify(encrypted_data, tag)
        return plaintext
    except ValueError:
        return None


def generate_nonce(iv_index, sequence_number, source_address):
    """Generate 13-byte nonce for Mesh encryption.
    
    Nonce format:
    - 4 bytes: IV Index (little-endian)
    - 6 bytes: Sequence Number (little-endian)
    - 3 bytes: Source Address (little-endian, 16-bit address padded to 3 bytes)
    
    Args:
        iv_index: 4-byte IV Index
        sequence_number: 6-byte sequence number
        source_address: 2-byte source address
    
    Returns:
        13-byte nonce
    """
    if isinstance(iv_index, int):
        iv_index = struct.pack("<I", iv_index)
    if isinstance(sequence_number, int):
        sequence_number = struct.pack("<Q", sequence_number)[:6]
    if isinstance(source_address, int):
        source_address = struct.pack("<H", source_address)
    
    source_address_3bytes = source_address + b"\x00"
    
    return iv_index + sequence_number + source_address_3bytes


def build_access_message(address, app_key_index, payload):
    """Build Access Layer message.
    
    Args:
        address: 2-byte destination address
        app_key_index: application key index
        payload: Access Payload (Vendor Model message)
    
    Returns:
        Access Layer message bytes
    """
    message = bytearray()
    message.append(0x00)
    message.extend(struct.pack("<H", address))
    message.append((app_key_index & 0x0F) | 0x40)
    message.extend(payload)
    return bytes(message)


def build_vendor_model_message(opcode, parameters, company_id=LEITE_COMPANY_ID):
    """Build Vendor Model message.
    
    Args:
        opcode: vendor-specific opcode
        parameters: message parameters
        company_id: company identifier (default: 0x1121 for Ltech)
    
    Returns:
        Vendor Model message bytes
    """
    message = bytearray()
    message.append(0xC0 | ((opcode >> 8) & 0x0F))
    if opcode > 0xFF:
        message.append(opcode & 0xFF)
    message.extend(struct.pack("<H", company_id))
    message.extend(struct.pack("<I", LEITE_VENDOR_MODEL_ID))
    if isinstance(parameters, int):
        message.append(parameters)
    elif isinstance(parameters, bytes):
        message.extend(parameters)
    elif isinstance(parameters, bytearray):
        message.extend(parameters)
    elif isinstance(parameters, list):
        message.extend(bytes(parameters))
    return bytes(message)


def build_proxy_pdu(network_pdu, is_segmented=False, segment_offset=0, last_segment=False):
    """Build Proxy Protocol PDU.
    
    Args:
        network_pdu: Network PDU
        is_segmented: True if this is a segmented message
        segment_offset: segment offset (for SAR)
        last_segment: True if this is the last segment
    
    Returns:
        Proxy Protocol PDU bytes
    """
    header = 0x00
    
    if is_segmented:
        header |= 0x80
        if last_segment:
            header |= 0x40
        header |= (segment_offset & 0x3F)
    
    pdu = bytearray([header])
    pdu.extend(network_pdu)
    
    return bytes(pdu)


def parse_proxy_pdu(pdu):
    """Parse Proxy Protocol PDU.
    
    Args:
        pdu: Proxy Protocol PDU bytes
    
    Returns:
        dict with header info and network_pdu
    """
    header = pdu[0]
    is_segmented = bool(header & 0x80)
    last_segment = bool(header & 0x40)
    segment_offset = header & 0x3F
    network_pdu = pdu[1:]
    
    return {
        "is_segmented": is_segmented,
        "last_segment": last_segment,
        "segment_offset": segment_offset,
        "network_pdu": network_pdu
    }


def segment_network_pdu(network_pdu, mtu=517):
    """Segment Network PDU if it exceeds MTU-3.
    
    Args:
        network_pdu: Network PDU to segment
        mtu: maximum transmission unit (default: 517)
    
    Returns:
        list of Proxy Protocol PDUs
    """
    max_segment_size = mtu - 3
    
    if len(network_pdu) <= max_segment_size:
        return [build_proxy_pdu(network_pdu)]
    
    segments = []
    total_length = len(network_pdu)
    offset = 0
    
    while offset < total_length:
        remaining = total_length - offset
        segment_length = min(remaining, max_segment_size)
        is_last_segment = (offset + segment_length) >= total_length
        
        segment = network_pdu[offset:offset + segment_length]
        proxy_pdu = build_proxy_pdu(segment, True, offset, is_last_segment)
        segments.append(proxy_pdu)
        
        offset += segment_length
    
    return segments


def hex_to_bytes(hex_string):
    """Convert hex string to bytes, removing spaces and converting to uppercase."""
    return bytes.fromhex(hex_string.replace(" ", "").strip())


def bytes_to_hex(data):
    """Convert bytes to hex string."""
    return data.hex().upper()
