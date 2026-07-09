package com.ltech.smarthome.blemesh;

import android.bluetooth.BluetoothDevice;
import com.feasycom.feasymesh.library.ble.BleManagerCallbacks;

/* loaded from: classes3.dex */
public interface BleMeshManagerCallbacks extends BleManagerCallbacks {
    void onDataReceived(final BluetoothDevice bluetoothDevice, final int mtu, final byte[] pdu);

    void onDataSent(final BluetoothDevice device, final int mtu, final byte[] pdu);
}