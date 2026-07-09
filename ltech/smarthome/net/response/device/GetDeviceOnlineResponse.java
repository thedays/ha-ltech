package com.ltech.smarthome.net.response.device;

/* loaded from: classes4.dex */
public class GetDeviceOnlineResponse {
    private String deviceName;
    private String deviceSecret;
    private Object firmwareVersion;
    private String gmtActive;
    private String gmtCreate;
    private String gmtOnline;
    private String iotId;
    private String ipAddress;
    private int nodeType;
    private String productKey;
    private String productName;
    private String region;
    private String status;
    private String utcActive;
    private String utcCreate;
    private String utcOnline;

    public String getIotId() {
        return this.iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceSecret() {
        return this.deviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret;
    }

    public Object getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(Object firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getUtcCreate() {
        return this.utcCreate;
    }

    public void setUtcCreate(String utcCreate) {
        this.utcCreate = utcCreate;
    }

    public String getGmtActive() {
        return this.gmtActive;
    }

    public void setGmtActive(String gmtActive) {
        this.gmtActive = gmtActive;
    }

    public String getUtcActive() {
        return this.utcActive;
    }

    public void setUtcActive(String utcActive) {
        this.utcActive = utcActive;
    }

    public String getGmtOnline() {
        return this.gmtOnline;
    }

    public void setGmtOnline(String gmtOnline) {
        this.gmtOnline = gmtOnline;
    }

    public String getUtcOnline() {
        return this.utcOnline;
    }

    public void setUtcOnline(String utcOnline) {
        this.utcOnline = utcOnline;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}