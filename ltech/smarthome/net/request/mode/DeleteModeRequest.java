package com.ltech.smarthome.net.request.mode;

/* loaded from: classes4.dex */
public class DeleteModeRequest {
    private int devicetype;
    private int modeindex;
    private int modetype;
    private long placeid;
    private long userid;

    public DeleteModeRequest(long placeid, long userid, int devicetype, int modetype, int modeindex) {
        this.userid = userid;
        this.devicetype = devicetype;
        this.modetype = modetype;
        this.modeindex = modeindex;
    }
}