import struct
from Crypto.Cipher import AES
from Crypto.Hash import CMAC


LEITE_COMPANY_ID = 0x1111
LEITE_VENDOR_MODEL_ID = 0x1111


def aes_cmac(key, data):
    cmac = CMAC.new(key, ciphermod=AES)
    cmac.update(data)
    return cmac.digest()


def s1(m):
    return aes_cmac(b'\x00' * 16, m)


def k1(net_key, index):
    nid, enc_key, priv_key = _derive_network_keys(net_key)
    return nid, enc_key, priv_key


def k2(net_key):
    identity_key = aes_cmac(aes_cmac(s1(b'nkik'), net_key), b'nkik' + b'\x01')
    beacon_key = aes_cmac(aes_cmac(s1(b'nkbk'), net_key), b'nkbk' + b'\x01')
    network_id = k3(net_key)
    return identity_key, beacon_key, network_id


def k3(net_key):
    salt = s1(b'smk3')
    t = aes_cmac(salt, net_key)
    output = aes_cmac(t, b'id64' + b'\x01')
    return output[-8:]


def k4(app_key):
    salt = s1(b'smk4')
    t = aes_cmac(salt, app_key)
    output = aes_cmac(t, b'id6' + b'\x01')
    return output[15] & 0x3F


def _derive_network_keys(net_key):
    salt = s1(b'smk2')
    t = aes_cmac(salt, net_key)
    t0 = b''
    t1 = aes_cmac(t, t0 + b'\x00' + b'\x01')
    t2 = aes_cmac(t, t1 + b'\x00' + b'\x02')
    t3 = aes_cmac(t, t2 + b'\x00' + b'\x03')
    nid = t1[15] & 0x7F
    return nid, t2, t3


def aes_ccm_encrypt(key, nonce, plaintext, auth_data=b""):
    cipher = AES.new(key, AES.MODE_CCM, nonce=nonce, mac_len=4)
    cipher.update(auth_data)
    ciphertext, tag = cipher.encrypt_and_digest(plaintext)
    return ciphertext + tag


def aes_ccm_decrypt(key, nonce, ciphertext_with_tag, auth_data=b""):
    try:
        tag = ciphertext_with_tag[-4:]
        encrypted = ciphertext_with_tag[:-4]
        cipher = AES.new(key, AES.MODE_CCM, nonce=nonce, mac_len=4)
        cipher.update(auth_data)
        plaintext = cipher.decrypt_and_verify(encrypted, tag)
        return plaintext
    except ValueError:
        return None


def generate_network_nonce(iv_index, ctl_ttl, seq, src):
    return (b'\x00' +
            bytes([ctl_ttl]) +
            struct.pack(">I", seq)[1:] +
            struct.pack(">H", src) +
            b'\x00\x00' +
            struct.pack(">I", iv_index))


def generate_app_nonce(iv_index, seq, src, dst):
    return (b'\x01' +
            b'\x00' +
            struct.pack(">I", seq)[1:] +
            struct.pack(">H", src) +
            struct.pack(">H", dst) +
            struct.pack(">I", iv_index))


def generate_device_nonce(iv_index, seq, src, dst):
    return (b'\x02' +
            b'\x00' +
            struct.pack(">I", seq)[1:] +
            struct.pack(">H", src) +
            struct.pack(">H", dst) +
            struct.pack(">I", iv_index))


def generate_nonce(iv_index, sequence_number, source_address):
    if isinstance(iv_index, int):
        iv_bytes = struct.pack(">I", iv_index)
    else:
        iv_bytes = iv_index
    if isinstance(sequence_number, int):
        seq_bytes = struct.pack(">I", sequence_number)[1:]
    else:
        seq_bytes = sequence_number
    if isinstance(source_address, int):
        src_bytes = struct.pack(">H", source_address)
    else:
        src_bytes = source_address
    return b'\x00' + b'\x00' + seq_bytes + src_bytes + b'\x00\x00' + iv_bytes


def deobfuscate_network_pdu(network_pdu, privacy_key, iv_index):
    if len(network_pdu) < 7:
        return network_pdu
    obfuscated_data = network_pdu[1:7]
    privacy_random = network_pdu[7:14] if len(network_pdu) >= 14 else b'\x00' * 7
    pecb_input = b'\x00' * 5 + struct.pack(">I", iv_index) + privacy_random
    pecb = AES.new(privacy_key, AES.MODE_ECB).encrypt(pecb_input)
    deobfuscated = bytes(a ^ b for a, b in zip(obfuscated_data, pecb[:6]))
    return bytes([network_pdu[0]]) + deobfuscated + network_pdu[7:]


def build_network_pdu(ctl, ttl, seq, src, dst, access_pdu, enc_key, priv_key, iv_index,
                      nid, app_key=None, akf=1, aid=0, device_key=None):
    ctl_ttl = (ctl << 7) | (ttl & 0x7F)
    mac_len = 8 if ctl else 4
    dst_bytes = struct.pack(">H", dst)
    
    if app_key is not None or device_key is not None:
        # 根据 akf 选择使用 AppKey 还是 DeviceKey
        use_app_key = (akf == 1)
        key_to_use = app_key if use_app_key else device_key
        
        if key_to_use is None:
            # 如果所需密钥不可用，回退到另一个密钥
            key_to_use = device_key if use_app_key else app_key
            if key_to_use is not None:
                import logging
                logging.getLogger(__name__).warning(
                    f"Using fallback key: {'DeviceKey' if use_app_key else 'AppKey'} not available, "
                    f"using {'AppKey' if use_app_key else 'DeviceKey'} instead"
                )
        
        if key_to_use is None:
            raise ValueError("No encryption key available for upper transport")
        
        transport_pdu = encrypt_upper_transport(
            access_pdu, key_to_use, iv_index, seq, src, dst,
            use_app_key=use_app_key, akf=akf, aid=aid
        )
    else:
        transport_pdu = access_pdu
    
    nonce = generate_network_nonce(iv_index, ctl_ttl, seq, src)
    encrypted = aes_ccm_encrypt(enc_key, nonce, dst_bytes + transport_pdu, b"")
    ciphertext = encrypted[:-mac_len]
    mac = encrypted[-mac_len:]
    
    network_plain = bytearray()
    network_plain.append(nid & 0x7F)
    network_plain.extend(bytes([ctl_ttl]))
    network_plain.extend(struct.pack(">I", seq)[1:])
    network_plain.extend(struct.pack(">H", src))
    network_plain.extend(ciphertext)
    network_plain.extend(mac)
    
    privacy_random = bytes(network_plain[7:14]) if len(network_plain) >= 14 else b'\x00' * 7
    pecb_input = b'\x00' * 5 + struct.pack(">I", iv_index) + privacy_random
    pecb = AES.new(priv_key, AES.MODE_ECB).encrypt(pecb_input)
    obfuscated_data = bytes(a ^ b for a, b in zip(bytes(network_plain[1:7]), pecb[:6]))
    
    ivi = iv_index & 0x01
    first_byte = (ivi << 7) | (nid & 0x7F)
    
    network_pdu = bytearray()
    network_pdu.append(first_byte)
    network_pdu.extend(obfuscated_data)
    network_pdu.extend(network_plain[7:])
    
    return bytes(network_pdu)


def decrypt_network_pdu(network_pdu, enc_key, priv_key, iv_index):
    if len(network_pdu) < 12:
        return None, "PDU too short"
    
    first_byte = network_pdu[0]
    ivi = (first_byte >> 7) & 0x01
    nid = first_byte & 0x7F
    
    actual_iv = iv_index if ivi == (iv_index & 1) else iv_index ^ 1
    
    deobfuscated = deobfuscate_network_pdu(network_pdu, priv_key, actual_iv)
    
    ctl_ttl = deobfuscated[1]
    ctl = (ctl_ttl >> 7) & 0x01
    ttl = ctl_ttl & 0x7F
    seq = int.from_bytes(deobfuscated[2:5], "big")
    src = struct.unpack(">H", deobfuscated[5:7])[0]
    
    nonce = generate_network_nonce(actual_iv, ctl_ttl, seq, src)
    mac_len = 8 if ctl else 4
    
    ciphertext = deobfuscated[7:-mac_len]
    mac = deobfuscated[-mac_len:]
    
    try:
        cipher = AES.new(enc_key, AES.MODE_CCM,
                        nonce=nonce,
                        mac_len=mac_len,
                        msg_len=len(ciphertext),
                        assoc_len=0)
        cleartext = cipher.decrypt_and_verify(ciphertext, mac)
        return cleartext, {
            "ctl": ctl, "ttl": ttl, "seq": seq, "src": src,
            "iv_index": actual_iv, "mac_len": mac_len
        }
    except ValueError:
        return None, "MAC verification failed"


def build_access_message(dst_address, akf, aid, access_payload):
    return access_payload


def encrypt_upper_transport(access_pdu, key, iv_index, seq, src, dst, use_app_key=True, akf=1, aid=0):
    akf_aid = (akf << 6) | (aid & 0x3F)
    if use_app_key:
        nonce = generate_app_nonce(iv_index, seq, src, dst)
    else:
        nonce = generate_device_nonce(iv_index, seq, src, dst)
    encrypted = aes_ccm_encrypt(key, nonce, access_pdu, b"")
    return bytes([akf_aid]) + encrypted


def decrypt_upper_transport(transport_pdu, key, iv_index, seq, src, dst, use_app_key=True):
    if len(transport_pdu) < 5:
        return None, "TransportPDU too short"
    
    akf_aid = transport_pdu[0]
    akf = (akf_aid >> 6) & 0x01
    aid = akf_aid & 0x3F
    
    encrypted_payload = transport_pdu[1:-4]
    trans_mic = transport_pdu[-4:]
    
    if use_app_key:
        nonce = generate_app_nonce(iv_index, seq, src, dst)
    else:
        nonce = generate_device_nonce(iv_index, seq, src, dst)
    
    try:
        cipher = AES.new(key, AES.MODE_CCM,
                        nonce=nonce,
                        mac_len=4,
                        msg_len=len(encrypted_payload),
                        assoc_len=0)
        plaintext = cipher.decrypt_and_verify(encrypted_payload, trans_mic)
        return plaintext, {
            "akf": akf, "aid": aid,
            "nonce_type": "app" if use_app_key else "device"
        }
    except ValueError:
        return None, "TransMIC verification failed"


def build_vendor_model_message(opcode, parameters, company_id=LEITE_COMPANY_ID):
    """Build Vendor Model Access Message per Bluetooth Mesh spec.

    Standard format: [opcode 1B (0xC0-0xFF)] [company_id 2B LE] [parameters]

    Verified against Ltech APP (FeasyMesh library) and observed network traffic:
    - company_id = 0x1111 (from FeasyController VendorModelMessageAcked(appKey, modelId, 4369, ...))
    - Observed messages: c41111000105, c71111000101, c611110001d6ff all match this format.
    """
    message = bytearray()
    # Opcode (1 byte). Ltech funcCode values (0xC4, 0xC6, 0xC7...) are already in the
    # vendor opcode range (0xC0-0xFF), so use directly. For raw opcode values < 0xC0,
    # set the vendor opcode marker (0xC0 | value).
    if opcode < 0xC0:
        message.append(0xC0 | (opcode & 0x3F))
    else:
        message.append(opcode & 0xFF)
    # Company ID (2 octets, little-endian)
    message.extend(struct.pack("<H", company_id))
    # Parameters
    if isinstance(parameters, int):
        message.append(parameters)
    elif isinstance(parameters, (bytes, bytearray)):
        message.extend(parameters)
    elif isinstance(parameters, list):
        message.extend(bytes(parameters))
    return bytes(message)


def parse_vendor_model_message(data):
    """Parse Vendor Model Access Message per Bluetooth Mesh spec.

    Standard format: [opcode 1B (0xC0-0xFF)] [company_id 2B LE] [parameters]
    """
    if len(data) < 3:  # Minimum: 1 (opcode) + 2 (company_id)
        return None

    opcode_first = data[0]
    if (opcode_first & 0xC0) != 0xC0:
        return None  # Not a valid vendor model opcode

    # Company ID (2 octets, little-endian)
    company_id = struct.unpack("<H", data[1:3])[0]

    result = {
        "company_id": company_id,
        "opcode": opcode_first,
    }

    # Parameters (remaining bytes)
    if len(data) > 3:
        result["parameters"] = data[3:]
    else:
        result["parameters"] = b""

    return result


def build_proxy_pdu(network_pdu, is_segmented=False, segment_offset=0, last_segment=False):
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
    return bytes.fromhex(hex_string.replace(" ", "").strip())


def bytes_to_hex(data):
    return data.hex().upper()
