package com.ltech.smarthome.model.device_param;

/* loaded from: classes4.dex */
public class DryContactBleParam extends BleParam {
    private int subType;

    public int getSubType() {
        return this.subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    @Override // com.ltech.smarthome.model.device_param.BleParam
    public String toString() {
        return "DryContactBleParam{unicastAddress=" + this.unicastAddress + ", deviceKey='" + this.deviceKey + "', deviceUUID='" + this.deviceUUID + "', deviceType='" + this.deviceType + "', publicationAddress=" + this.publicationAddress + ", groupId=" + this.groupId + ",subType=" + this.subType + '}';
    }
}