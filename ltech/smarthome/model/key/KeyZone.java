package com.ltech.smarthome.model.key;

/* loaded from: classes4.dex */
public class KeyZone {
    private int iconIndex;
    private long keyzoneid;
    private String screenStr;
    private int screenType;
    private int sensitivity;
    private int zoneNum;

    public long getKeyzoneid() {
        return this.keyzoneid;
    }

    public void setKeyzoneid(long keyzoneid) {
        this.keyzoneid = keyzoneid;
    }

    public int getZoneNum() {
        return this.zoneNum;
    }

    public void setZoneNum(int zoneNum) {
        this.zoneNum = zoneNum;
    }

    public String getScreenStr() {
        return this.screenStr;
    }

    public void setScreenStr(String screenStr) {
        this.screenStr = screenStr;
    }

    public int getScreenType() {
        return this.screenType;
    }

    public void setScreenType(int screenType) {
        this.screenType = screenType;
    }

    public int getIconIndex() {
        return this.iconIndex;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
    }

    public int getSensitivity() {
        return this.sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }
}