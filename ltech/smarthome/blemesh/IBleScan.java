package com.ltech.smarthome.blemesh;

import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import java.util.UUID;

/* loaded from: classes3.dex */
public interface IBleScan {
    void clearConnectableDevice(ExtendedBluetoothDevice device);

    ExtendedBluetoothDevice getConnectableDevice();

    boolean isScanning();

    void startScan(UUID filterUuid, IScanCallback callback);

    void stopScan();
}