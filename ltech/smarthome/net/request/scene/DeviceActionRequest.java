package com.ltech.smarthome.net.request.scene;

/* loaded from: classes4.dex */
public class DeviceActionRequest {
    private long deviceid;
    private long scenetype;

    public DeviceActionRequest(long deviceid, long scenetype) {
        this.deviceid = deviceid;
        this.scenetype = scenetype;
    }
}