package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class GradientSceneAction {
    private long deviceId;
    private String instruct;

    public GradientSceneAction(long deviceId, String instruct) {
        this.deviceId = deviceId;
        this.instruct = instruct;
    }

    public long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getInstruct() {
        return this.instruct;
    }

    public void setInstruct(String instruct) {
        this.instruct = instruct;
    }
}