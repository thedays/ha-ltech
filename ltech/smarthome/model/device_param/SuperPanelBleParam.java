package com.ltech.smarthome.model.device_param;

import com.ltech.smarthome.utils.HelpUtils;

/* loaded from: classes4.dex */
public class SuperPanelBleParam extends BleParam {
    private String musicDeadline;
    private String timeZone;

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone() {
        this.timeZone = HelpUtils.getTimeZoneId();
    }

    public String getCurrentZone() {
        return HelpUtils.getTimeZoneId();
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getMusicDeadline() {
        return this.musicDeadline;
    }

    public void setMusicDeadline(String musicDeadline) {
        this.musicDeadline = musicDeadline;
    }

    @Override // com.ltech.smarthome.model.device_param.BleParam
    public String toString() {
        return "SuperPanelBleParam{unicastAddress=" + this.unicastAddress + ", deviceKey='" + this.deviceKey + "', deviceUUID='" + this.deviceUUID + "', deviceType='" + this.deviceType + "', publicationAddress=" + this.publicationAddress + ", groupId=" + this.groupId + ", timeZone='" + this.timeZone + "'}";
    }
}