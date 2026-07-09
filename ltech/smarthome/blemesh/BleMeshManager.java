package com.ltech.smarthome.blemesh;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.os.ParcelUuid;
import com.airoha.libfota.constant.AirohaOtaUUID;
import com.feasycom.feasymesh.library.ble.BleManager;
import com.feasycom.feasymesh.library.ble.callback.DataReceivedCallback;
import com.feasycom.feasymesh.library.ble.callback.DataSentCallback;
import com.feasycom.feasymesh.library.ble.data.Data;
import com.ltech.smarthome.blemesh.BleMeshManager;
import com.ltech.smarthome.utils.Constants;
import com.smart.message.utils.LHomeLog;
import java.util.UUID;
import no.nordicsemi.android.support.v18.scanner.ScanRecord;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

/* loaded from: classes3.dex */
public class BleMeshManager extends LoggableBleManager<BleMeshManagerCallbacks> {
    private static final int MTU_SIZE_DEFAULT = 23;
    private static final int MTU_SIZE_MAX = 517;
    public boolean isProvisioning;
    private boolean isProvisioningComplete;
    private BluetoothGattCharacteristic mFeasyCharacteristic;
    private boolean mIsDeviceReady;
    private BluetoothGattCharacteristic mMeshProvisioningDataInCharacteristic;
    private BluetoothGattCharacteristic mMeshProvisioningDataOutCharacteristic;
    public BluetoothGattCharacteristic mMeshProxyDataInCharacteristic;
    private BluetoothGattCharacteristic mMeshProxyDataOutCharacteristic;
    private boolean mNodeReset;
    public static final UUID MESH_PROVISIONING_UUID = UUID.fromString("00001827-0000-1000-8000-00805F9B34FB");
    private static final UUID MESH_PROVISIONING_DATA_IN = UUID.fromString("00002ADB-0000-1000-8000-00805F9B34FB");
    private static final UUID MESH_PROVISIONING_DATA_OUT = UUID.fromString("00002ADC-0000-1000-8000-00805F9B34FB");
    private static final UUID FEASY_SERVICE = UUID.fromString(AirohaOtaUUID.AT_SERVICE_UUID);
    private static final UUID FSC_NOTIFICATION = UUID.fromString(AirohaOtaUUID.AT_NOTIFY_CHARC_UUID);
    public static final UUID MESH_PROXY_UUID = UUID.fromString("00001828-0000-1000-8000-00805F9B34FB");
    private static final UUID MESH_PROXY_DATA_IN = UUID.fromString("00002ADD-0000-1000-8000-00805F9B34FB");
    private static final UUID MESH_PROXY_DATA_OUT = UUID.fromString("00002ADE-0000-1000-8000-00805F9B34FB");

    /* JADX INFO: Access modifiers changed from: private */
    class BleManagerGattCallbacks extends BleManager.BleManagerGattCallback {
        private BleManagerGattCallbacks() {
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0074  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00c0 A[ADDED_TO_REGION] */
        @Override // com.feasycom.feasymesh.library.ble.BleManagerHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean isRequiredServiceSupported(final android.bluetooth.BluetoothGatt r6) {
            /*
                Method dump skipped, instructions count: 267
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.blemesh.BleMeshManager.BleManagerGattCallbacks.isRequiredServiceSupported(android.bluetooth.BluetoothGatt):boolean");
        }

        @Override // com.feasycom.feasymesh.library.ble.BleManagerHandler
        protected void initialize() {
            BleMeshManager.this.requestMtu(517).enqueue();
            DataReceivedCallback dataReceivedCallback = new DataReceivedCallback() { // from class: com.ltech.smarthome.blemesh.BleMeshManager$BleManagerGattCallbacks$$ExternalSyntheticLambda0
                @Override // com.feasycom.feasymesh.library.ble.callback.DataReceivedCallback
                public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                    BleMeshManager.BleManagerGattCallbacks.this.lambda$initialize$0(bluetoothDevice, data);
                }
            };
            BluetoothGattCharacteristic bluetoothGattCharacteristic = BleMeshManager.this.isProvisioningComplete ? BleMeshManager.this.mMeshProxyDataOutCharacteristic : BleMeshManager.this.mMeshProvisioningDataOutCharacteristic;
            BleMeshManager.this.setNotificationCallback(bluetoothGattCharacteristic).with(dataReceivedCallback);
            BleMeshManager.this.enableNotifications(bluetoothGattCharacteristic).enqueue();
            if (BleMeshManager.this.mFeasyCharacteristic != null) {
                BleMeshManager bleMeshManager = BleMeshManager.this;
                bleMeshManager.enableNotifications(bleMeshManager.mFeasyCharacteristic).enqueue();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$initialize$0(BluetoothDevice bluetoothDevice, Data data) {
            ((BleMeshManagerCallbacks) BleMeshManager.this.mCallbacks).onDataReceived(bluetoothDevice, BleMeshManager.this.getMaximumPacketSize(), data.getValue());
        }

        @Override // com.feasycom.feasymesh.library.ble.BleManagerHandler
        protected void onDeviceDisconnected() {
            BleMeshManager.this.overrideMtu(23);
            BleMeshManager.this.mIsDeviceReady = false;
            BleMeshManager.this.isProvisioningComplete = false;
            BleMeshManager.this.mMeshProvisioningDataInCharacteristic = null;
            BleMeshManager.this.mMeshProvisioningDataOutCharacteristic = null;
            BleMeshManager.this.mMeshProxyDataInCharacteristic = null;
            BleMeshManager.this.mMeshProxyDataOutCharacteristic = null;
            BleMeshManager.this.isProvisioning = false;
        }

        @Override // com.feasycom.feasymesh.library.ble.BleManagerHandler
        protected void onServicesInvalidated() {
            BleMeshManager.this.overrideMtu(23);
            BleMeshManager.this.mIsDeviceReady = false;
            BleMeshManager.this.isProvisioningComplete = false;
            BleMeshManager.this.mMeshProvisioningDataInCharacteristic = null;
            BleMeshManager.this.mMeshProvisioningDataOutCharacteristic = null;
            BleMeshManager.this.mMeshProxyDataInCharacteristic = null;
            BleMeshManager.this.mMeshProxyDataOutCharacteristic = null;
        }

        @Override // com.feasycom.feasymesh.library.ble.BleManagerHandler
        protected void onDeviceReady() {
            BleMeshManager.this.mIsDeviceReady = true;
            super.onDeviceReady();
        }
    }

    public BleMeshManager(final Context context) {
        super(context);
        this.isProvisioning = false;
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManager
    protected BleManager.BleManagerGattCallback getGattCallback() {
        return new BleManagerGattCallbacks();
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManager
    protected boolean shouldClearCacheWhenDisconnected() {
        boolean z = !this.isProvisioningComplete || this.mNodeReset;
        this.mNodeReset = false;
        return z;
    }

    public void setClearCacheRequired() {
        this.mNodeReset = true;
    }

    public void sendPdu(final byte[] pdu) {
        if (this.mIsDeviceReady) {
            writeCharacteristic(this.isProvisioningComplete ? this.mMeshProxyDataInCharacteristic : this.mMeshProvisioningDataInCharacteristic, pdu, 1).split().with(new DataSentCallback() { // from class: com.ltech.smarthome.blemesh.BleMeshManager$$ExternalSyntheticLambda1
                @Override // com.feasycom.feasymesh.library.ble.callback.DataSentCallback
                public final void onDataSent(BluetoothDevice bluetoothDevice, Data data) {
                    BleMeshManager.this.lambda$sendPdu$0(bluetoothDevice, data);
                }
            }).enqueue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPdu$0(BluetoothDevice bluetoothDevice, Data data) {
        ((BleMeshManagerCallbacks) this.mCallbacks).onDataSent(bluetoothDevice, getMaximumPacketSize(), data.getValue());
    }

    public int getMaximumPacketSize() {
        return super.getMtu() - 3;
    }

    public boolean isProvisioningComplete() {
        return this.isProvisioningComplete;
    }

    public boolean isDeviceReady() {
        return this.mIsDeviceReady;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasWriteNoResponseProperty(final BluetoothGattCharacteristic characteristic) {
        return (characteristic.getProperties() & 4) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasNotifyProperty(final BluetoothGattCharacteristic characteristic) {
        return (characteristic.getProperties() & 16) != 0;
    }

    public static byte[] getServiceData(final ScanResult result, final UUID serviceUuid) {
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord != null) {
            return scanRecord.getServiceData(new ParcelUuid(serviceUuid));
        }
        return null;
    }

    public boolean refresh() {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "refresh: " + this.isProvisioningComplete);
        Class<?> cls = getClass();
        StringBuilder sb = new StringBuilder("refresh: ");
        sb.append(this.mMeshProxyDataInCharacteristic == null);
        LHomeLog.i(Constants.MESH_LOG, cls, sb.toString());
        if (this.mMeshProxyDataInCharacteristic == null) {
            disconnect().enqueue();
            return false;
        }
        this.isProvisioningComplete = true;
        DataReceivedCallback dataReceivedCallback = new DataReceivedCallback() { // from class: com.ltech.smarthome.blemesh.BleMeshManager$$ExternalSyntheticLambda0
            @Override // com.feasycom.feasymesh.library.ble.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                BleMeshManager.this.lambda$refresh$1(bluetoothDevice, data);
            }
        };
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.isProvisioningComplete ? this.mMeshProxyDataOutCharacteristic : this.mMeshProvisioningDataOutCharacteristic;
        setNotificationCallback(bluetoothGattCharacteristic).with(dataReceivedCallback);
        enableNotifications(bluetoothGattCharacteristic).enqueue();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refresh$1(BluetoothDevice bluetoothDevice, Data data) {
        ((BleMeshManagerCallbacks) this.mCallbacks).onDataReceived(bluetoothDevice, getMaximumPacketSize(), data.getValue());
    }
}