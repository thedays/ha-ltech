import sys
import os
import json

sys.path.insert(0, os.path.join(os.path.dirname(__file__), 'custom_components', 'ltech'))

from mesh_crypto import (
    k1, k2, k3, k4, aes_ccm_encrypt, aes_ccm_decrypt,
    generate_nonce, build_access_message, build_vendor_model_message,
    build_proxy_pdu, parse_proxy_pdu, segment_network_pdu, hex_to_bytes, bytes_to_hex
)

def test_k1_function():
    """Test K1 function for NetKey derivation."""
    print("=" * 60)
    print("Testing K1 Function")
    print("=" * 60)
    
    net_key = bytes.fromhex("63964771734FBD76E3B40519D1D94A48")
    index = 0
    
    nid, encryption_key, privacy_key = k1(net_key, index)
    
    print(f"Net Key: {net_key.hex().upper()}")
    print(f"NID: {nid.hex().upper()}")
    print(f"Encryption Key: {encryption_key.hex().upper()}")
    print(f"Privacy Key: {privacy_key.hex().upper()}")
    print(f"NID length: {len(nid)} bytes")
    print(f"Encryption Key length: {len(encryption_key)} bytes")
    print(f"Privacy Key length: {len(privacy_key)} bytes")
    
    assert len(nid) == 1, "NID should be 1 byte"
    assert len(encryption_key) == 16, "Encryption key should be 16 bytes"
    assert len(privacy_key) == 16, "Privacy key should be 16 bytes"
    
    print("✓ K1 function test passed\n")

def test_k2_function():
    """Test K2 function for Identity/Beacon keys."""
    print("=" * 60)
    print("Testing K2 Function")
    print("=" * 60)
    
    net_key = bytes.fromhex("63964771734FBD76E3B40519D1D94A48")
    
    identity_key, beacon_key, network_id = k2(net_key)
    
    print(f"Net Key: {net_key.hex().upper()}")
    print(f"Identity Key: {identity_key.hex().upper()}")
    print(f"Beacon Key: {beacon_key.hex().upper()}")
    print(f"Network ID: {network_id.hex().upper()}")
    
    assert len(identity_key) == 16, "Identity key should be 16 bytes"
    assert len(beacon_key) == 16, "Beacon key should be 16 bytes"
    assert len(network_id) == 8, "Network ID should be 8 bytes"
    
    print("✓ K2 function test passed\n")

def test_k3_function():
    """Test K3 function for IV update key."""
    print("=" * 60)
    print("Testing K3 Function")
    print("=" * 60)
    
    net_key = bytes.fromhex("63964771734FBD76E3B40519D1D94A48")
    
    iv_update_key = k3(net_key)
    
    print(f"Net Key: {net_key.hex().upper()}")
    print(f"IV Update Key: {iv_update_key.hex().upper()}")
    
    assert len(iv_update_key) == 16, "IV Update key should be 16 bytes"
    
    print("✓ K3 function test passed\n")

def test_k4_function():
    """Test K4 function for Transmit key."""
    print("=" * 60)
    print("Testing K4 Function")
    print("=" * 60)
    
    app_key = bytes.fromhex("63964771734FBD76E3B40519D1D94A48")
    
    transmit_key = k4(app_key)
    
    print(f"App Key: {app_key.hex().upper()}")
    print(f"Transmit Key: {transmit_key.hex().upper()}")
    
    assert len(transmit_key) == 16, "Transmit key should be 16 bytes"
    
    print("✓ K4 function test passed\n")

def test_aes_ccm():
    """Test AES-CCM encryption/decryption."""
    print("=" * 60)
    print("Testing AES-CCM Encryption/Decryption")
    print("=" * 60)
    
    key = bytes.fromhex("63964771734FBD76E3B40519D1D94A48")
    nonce = bytes(13)
    plaintext = b"Hello, Bluetooth Mesh!"
    auth_data = b"Additional authentication data"
    
    ciphertext = aes_ccm_encrypt(key, nonce, plaintext, auth_data)
    print(f"Key: {key.hex().upper()}")
    print(f"Nonce: {nonce.hex().upper()}")
    print(f"Plaintext: {plaintext.decode()}")
    print(f"Auth Data: {auth_data.decode()}")
    print(f"Ciphertext (with tag): {ciphertext.hex().upper()}")
    print(f"Ciphertext length: {len(ciphertext)} bytes (tag is 8 bytes)")
    
    decrypted = aes_ccm_decrypt(key, nonce, ciphertext, auth_data)
    print(f"Decrypted: {decrypted.decode()}")
    
    assert decrypted == plaintext, "Decryption should return original plaintext"
    
    print("✓ AES-CCM test passed\n")

def test_generate_nonce():
    """Test nonce generation."""
    print("=" * 60)
    print("Testing Nonce Generation")
    print("=" * 60)
    
    iv_index = 0x12345678
    seq_number = 0x000000000001
    source_address = 0x0001
    
    nonce = generate_nonce(iv_index, seq_number, source_address)
    
    print(f"IV Index: 0x{iv_index:08X}")
    print(f"Sequence Number: 0x{seq_number:012X}")
    print(f"Source Address: 0x{source_address:04X}")
    print(f"Nonce: {nonce.hex().upper()}")
    print(f"Nonce length: {len(nonce)} bytes")
    
    assert len(nonce) == 13, "Nonce should be 13 bytes"
    
    print("✓ Nonce generation test passed\n")

def test_build_access_message():
    """Test Access Layer message building."""
    print("=" * 60)
    print("Testing Access Message Building")
    print("=" * 60)
    
    address = 0x0001
    app_key_index = 0
    payload = bytes([0x82, 0x01])
    
    message = build_access_message(address, app_key_index, payload)
    
    print(f"Address: 0x{address:04X}")
    print(f"App Key Index: {app_key_index}")
    print(f"Payload: {payload.hex().upper()}")
    print(f"Access Message: {message.hex().upper()}")
    print(f"Message length: {len(message)} bytes")
    
    assert message[0] == 0x00, "Opcode should be 0x00 for Access message"
    assert len(message) == 6, "Access message should be 6 bytes"
    
    print("✓ Access message test passed\n")

def test_build_vendor_model_message():
    """Test Vendor Model message building."""
    print("=" * 60)
    print("Testing Vendor Model Message Building")
    print("=" * 60)
    
    opcode = 0x01
    parameters = bytes([0x01, 0x00])
    
    message = build_vendor_model_message(opcode, parameters)
    
    print(f"Opcode: 0x{opcode:02X}")
    print(f"Parameters: {parameters.hex().upper()}")
    print(f"Vendor Model Message: {message.hex().upper()}")
    print(f"Message length: {len(message)} bytes")
    
    assert message[0] == 0xC0, "First byte should be 0xC0 for Vendor Model"
    
    print("✓ Vendor Model message test passed\n")

def test_proxy_pdu():
    """Test Proxy Protocol PDU building and parsing."""
    print("=" * 60)
    print("Testing Proxy Protocol PDU")
    print("=" * 60)
    
    network_pdu = bytes([0x11, 0x22, 0x33, 0x44, 0x55])
    
    pdu = build_proxy_pdu(network_pdu)
    print(f"Network PDU: {network_pdu.hex().upper()}")
    print(f"Proxy PDU: {pdu.hex().upper()}")
    
    parsed = parse_proxy_pdu(pdu)
    print(f"Parsed - Segmented: {parsed['is_segmented']}")
    print(f"Parsed - Last Segment: {parsed['last_segment']}")
    print(f"Parsed - Network PDU: {parsed['network_pdu'].hex().upper()}")
    
    assert not parsed['is_segmented'], "Should not be segmented"
    assert parsed['network_pdu'] == network_pdu, "Network PDU should match"
    
    print("✓ Proxy PDU test passed\n")

def test_sar():
    """Test Segmentation and Reassembly."""
    print("=" * 60)
    print("Testing SAR (Segmentation and Reassembly)")
    print("=" * 60)
    
    long_network_pdu = bytes([i for i in range(100)])
    
    segments = segment_network_pdu(long_network_pdu, mtu=50)
    print(f"Original PDU length: {len(long_network_pdu)} bytes")
    print(f"Number of segments: {len(segments)}")
    
    for i, seg in enumerate(segments):
        parsed = parse_proxy_pdu(seg)
        print(f"Segment {i+1}: {len(seg)} bytes, segmented={parsed['is_segmented']}, last={parsed['last_segment']}")
    
    print("✓ SAR test passed\n")

def test_hex_conversion():
    """Test hex conversion utilities."""
    print("=" * 60)
    print("Testing Hex Conversion Utilities")
    print("=" * 60)
    
    hex_str = "63964771734FBD76E3B40519D1D94A48"
    data = hex_to_bytes(hex_str)
    
    print(f"Hex string: {hex_str}")
    print(f"Bytes: {data}")
    print(f"Back to hex: {bytes_to_hex(data)}")
    
    assert bytes_to_hex(data) == hex_str.upper(), "Hex conversion should be round-trip"
    
    print("✓ Hex conversion test passed\n")

def main():
    """Run all tests."""
    print("\n" + "=" * 60)
    print("Ltech Mesh Crypto Library Tests")
    print("=" * 60 + "\n")
    
    try:
        test_k1_function()
        test_k2_function()
        test_k3_function()
        test_k4_function()
        test_aes_ccm()
        test_generate_nonce()
        test_build_access_message()
        test_build_vendor_model_message()
        test_proxy_pdu()
        test_sar()
        test_hex_conversion()
        
        print("=" * 60)
        print("ALL TESTS PASSED!")
        print("=" * 60)
        
    except AssertionError as e:
        print(f"\n✗ Test failed: {e}")
        sys.exit(1)
    except Exception as e:
        print(f"\n✗ Unexpected error: {e}")
        import traceback
        traceback.print_exc()
        sys.exit(1)

if __name__ == "__main__":
    main()