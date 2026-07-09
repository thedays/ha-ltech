package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class ModeContent {
    public static final int TYPE_DUV = 4;
    private String Content;
    private String controlType;
    private int deviceType;
    private long lightModeId;
    private int modeIndex;
    private String modeName;
    private int modeType;
    private String moduleType;
    private int picIndex;
    private long placeId;
    private int playTimes;
    private long userId;

    public long getLightModeId() {
        return this.lightModeId;
    }

    public void setLightModeId(long lightModeId) {
        this.lightModeId = lightModeId;
    }

    public long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getModeName() {
        return this.modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getContent() {
        return this.Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

    public int getModeType() {
        return this.modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public int getModeIndex() {
        return this.modeIndex;
    }

    public void setModeIndex(int modeIndex) {
        this.modeIndex = modeIndex;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getControlType() {
        return this.controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public int getPicIndex() {
        return this.picIndex;
    }

    public void setPicIndex(int picIndex) {
        this.picIndex = picIndex;
    }

    public int getPlayTimes() {
        return this.playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }
}