package com.ltech.smarthome.blemesh;

import android.bluetooth.BluetoothDevice;

/* loaded from: classes3.dex */
public interface IConnectBleCallback {
    void onConnectFail();

    void onConnectSuccess(String btModule, BluetoothDevice connectedDevice);
}