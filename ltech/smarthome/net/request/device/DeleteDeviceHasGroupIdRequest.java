package com.ltech.smarthome.net.request.device;

/* loaded from: classes4.dex */
public class DeleteDeviceHasGroupIdRequest {
    private long deviceid;
    private long groupid;

    public DeleteDeviceHasGroupIdRequest(long deviceid, long groupId) {
        this.deviceid = deviceid;
        this.groupid = groupId;
    }
}