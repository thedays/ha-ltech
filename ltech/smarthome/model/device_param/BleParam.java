package com.ltech.smarthome.model.device_param;

/* loaded from: classes4.dex */
public class BleParam {
    protected int bleType;
    protected String deviceKey;
    private boolean deviceKeyStatus;
    protected int deviceType;
    protected String deviceUUID;
    protected long groupId;
    protected int publicationAddress;
    protected long publicationid;
    protected int unicastAddress;

    public boolean isDeviceKeyStatus() {
        return this.deviceKeyStatus;
    }

    public void setDeviceKeyStatus(boolean deviceKeyStatus) {
        this.deviceKeyStatus = deviceKeyStatus;
    }

    public int getUnicastAddress() {
        return this.unicastAddress;
    }

    public void setUnicastAddress(int unicastAddress) {
        this.unicastAddress = unicastAddress;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceUUID() {
        return this.deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getPublicationAddress() {
        return this.publicationAddress;
    }

    public void setPublicationAddress(int publicationAddress) {
        this.publicationAddress = publicationAddress;
    }

    public long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getBleType() {
        return this.bleType;
    }

    public void setBleType(int bleType) {
        this.bleType = bleType;
    }

    public long getPublicationId() {
        return this.publicationid;
    }

    public void setPublicationId(long publicationid) {
        this.publicationid = publicationid;
    }

    public String toString() {
        return "BleParam{unicastAddress=" + this.unicastAddress + ", deviceKey='" + this.deviceKey + "', deviceUUID='" + this.deviceUUID + "', deviceType=" + this.deviceType + ", publicationAddress=" + this.publicationAddress + ", groupId=" + this.groupId + ", bleType=" + this.bleType + ", publicationid=" + this.publicationid + ", deviceKeyStatus=" + this.deviceKeyStatus + '}';
    }
}