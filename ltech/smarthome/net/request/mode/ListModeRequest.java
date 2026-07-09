package com.ltech.smarthome.net.request.mode;

/* loaded from: classes4.dex */
public class ListModeRequest {
    private int devicetype;
    private int modetype;
    private long placeid;
    private long userid;

    public ListModeRequest(long placeid, long userid, int devicetype, int modetype) {
        this.userid = userid;
        this.devicetype = devicetype;
        this.modetype = modetype;
    }
}