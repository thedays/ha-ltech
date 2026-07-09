package com.ltech.smarthome.net.request.device;

/* loaded from: classes4.dex */
public class UpdateGroupIconRequest {
    private long groupid;
    private int imgindex;

    public UpdateGroupIconRequest(long groupid, int imgindex) {
        this.groupid = groupid;
        this.imgindex = imgindex;
    }
}