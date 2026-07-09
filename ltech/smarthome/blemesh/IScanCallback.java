package com.ltech.smarthome.blemesh;

import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;

/* loaded from: classes3.dex */
public interface IScanCallback {
    void onScanResult(ExtendedBluetoothDevice bluetoothDevice);
}