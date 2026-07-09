package com.ltech.smarthome.blemesh.bean;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.feasycom.feasymesh.library.MeshBeacon;
import com.feasycom.feasymesh.library.UnprovisionedBeacon;
import java.util.UUID;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

/* loaded from: classes3.dex */
public class ExtendedBluetoothDevice implements Parcelable {
    public static final Parcelable.Creator<ExtendedBluetoothDevice> CREATOR = new Parcelable.Creator<ExtendedBluetoothDevice>() { // from class: com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExtendedBluetoothDevice createFromParcel(Parcel in) {
            return new ExtendedBluetoothDevice(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExtendedBluetoothDevice[] newArray(int size) {
            return new ExtendedBluetoothDevice[size];
        }
    };
    private MeshBeacon beacon;
    private int changeType;
    private int controlType;
    private BluetoothDevice device;
    private String productType;
    private int rssi;
    private ScanResult scanResult;
    private String subProductType;
    private UUID uuid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ExtendedBluetoothDevice(final ScanResult scanResult, final MeshBeacon beacon) {
        this.scanResult = scanResult;
        this.device = scanResult.getDevice();
        UUID uuid = ((UnprovisionedBeacon) beacon).getUuid();
        this.uuid = uuid;
        this.changeType = Integer.parseInt(uuid.toString().substring(19, 21), 16);
        this.productType = this.uuid.toString().substring(21, 23);
        this.subProductType = this.uuid.toString().substring(24, 26);
        this.rssi = scanResult.getRssi();
        this.beacon = beacon;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public ExtendedBluetoothDevice(final ScanResult scanResult) {
        this.scanResult = scanResult;
        this.device = scanResult.getDevice();
        this.rssi = scanResult.getRssi();
    }

    public ExtendedBluetoothDevice() {
    }

    protected ExtendedBluetoothDevice(Parcel in) {
        this.device = (BluetoothDevice) in.readParcelable(BluetoothDevice.class.getClassLoader());
        this.scanResult = (ScanResult) in.readParcelable(ScanResult.class.getClassLoader());
        this.changeType = in.readInt();
        this.controlType = in.readInt();
        this.productType = in.readString();
        this.subProductType = in.readString();
        this.rssi = in.readInt();
        this.beacon = (MeshBeacon) in.readParcelable(MeshBeacon.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.device, flags);
        dest.writeParcelable(this.scanResult, flags);
        dest.writeInt(this.changeType);
        dest.writeInt(this.controlType);
        dest.writeString(this.productType);
        dest.writeString(this.subProductType);
        dest.writeInt(this.rssi);
        dest.writeParcelable(this.beacon, flags);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }

    public MeshBeacon getBeacon() {
        return this.beacon;
    }

    public String getAddress() {
        return this.device.getAddress();
    }

    public String getProductType() {
        return this.productType;
    }

    public int getChangeType() {
        return this.changeType;
    }

    public String getSubProductType() {
        return this.subProductType;
    }

    public int getControlType() {
        return this.controlType;
    }

    public void setControlType(int controlType) {
        this.controlType = controlType;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(final int rssi) {
        this.rssi = rssi;
    }

    public ScanResult getScanResult() {
        return this.scanResult;
    }

    public boolean matches(final ScanResult scanResult) {
        return this.device.getAddress().equals(scanResult.getDevice().getAddress());
    }

    public boolean equals(final Object o) {
        if (o instanceof ExtendedBluetoothDevice) {
            return this.device.getAddress().equals(((ExtendedBluetoothDevice) o).device.getAddress());
        }
        return super.equals(o);
    }

    public String toString() {
        return "ExtendedBluetoothDevice{device=" + this.device + ", scanResult=" + this.scanResult + ", productType='" + this.productType + "', subProductType='" + this.subProductType + "', rssi=" + this.rssi + ", uuid=" + this.uuid + ", changeType=" + this.changeType + ", controlType=" + this.controlType + ", beacon=" + this.beacon + '}';
    }
}