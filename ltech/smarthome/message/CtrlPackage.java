package com.ltech.smarthome.message;

import com.smart.message.base.BaseCtrlPackage;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class CtrlPackage extends BaseCtrlPackage {
    private int address;
    private int controlType;
    private long[] deviceIds;
    private String[] platformDeviceIds;
    private int unicastAddress;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ControlType {
        public static final int BLE_DEVICE = 2;
        public static final int BLE_GROUP = 3;
        public static final int WIFI_BLE_DEVICE = 4;
        public static final int WIFI_DEVICE = 0;
        public static final int WIFI_GROUP = 1;
    }

    public int getUnicastAddress() {
        return this.unicastAddress;
    }

    public void setUnicastAddress(int unicastAddress) {
        this.unicastAddress = unicastAddress;
    }

    public CtrlPackage(int agreementId) {
        super(agreementId);
    }

    public String[] getPlatformDeviceIds() {
        return this.platformDeviceIds;
    }

    public void setPlatformDeviceIds(String[] platformDeviceIds) {
        this.platformDeviceIds = platformDeviceIds;
    }

    public long[] getDeviceIds() {
        return this.deviceIds;
    }

    public void setDeviceIds(long[] deviceIds) {
        this.deviceIds = deviceIds;
    }

    public int getAddress() {
        return this.address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getControlType() {
        return this.controlType;
    }

    public void setControlType(int controlType) {
        this.controlType = controlType;
    }

    @Override // com.smart.message.base.BaseCtrlPackage
    public String toString() {
        return "CtrlPackage{platformDeviceIds=" + Arrays.toString(this.platformDeviceIds) + ", deviceIds=" + Arrays.toString(this.deviceIds) + ", address=" + this.address + ", controlType=" + this.controlType + '}';
    }
}