package com.ltech.smarthome.ui.device.central.airpro;

/* loaded from: classes4.dex */
public class FreshAirInfoItem {
    private Boolean enable;
    private int iconRes;
    private String name;
    private String value;

    public FreshAirInfoItem(int iconRes, String name, String value, Boolean enable) {
        this.iconRes = iconRes;
        this.name = name;
        this.value = value;
        this.enable = enable;
    }

    public int getIconRes() {
        return this.iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getEnable() {
        return this.enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}