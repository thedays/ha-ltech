package com.ltech.smarthome.model.device_param;

/* loaded from: classes4.dex */
public class CgdProParam {
    protected int bleType;
    protected int deviceType;
    protected long groupId;
    protected int publicationAddress;
    protected int unicastAddress;

    public int getUnicastAddress() {
        return this.unicastAddress;
    }

    public void setUnicastAddress(int unicastAddress) {
        this.unicastAddress = unicastAddress;
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

    public String toString() {
        return "BleParam{unicastAddress=" + this.unicastAddress + ", deviceType='" + this.deviceType + "', publicationAddress=" + this.publicationAddress + ", groupId=" + this.groupId + ", bleType=" + this.bleType + '}';
    }
}