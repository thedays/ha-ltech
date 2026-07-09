package com.ltech.smarthome.ui.device.camera;

/* loaded from: classes4.dex */
public class EzDevice {
    private String deviceType;
    private String serialNo;
    private String verifyCode;

    public EzDevice(String serialNo, String verifyCode, String deviceType) {
        this.serialNo = serialNo;
        this.verifyCode = verifyCode;
        this.deviceType = deviceType;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }

    public String getDeviceType() {
        return this.deviceType;
    }
}