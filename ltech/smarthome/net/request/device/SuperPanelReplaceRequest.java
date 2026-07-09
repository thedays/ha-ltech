package com.ltech.smarthome.net.request.device;

/* loaded from: classes4.dex */
public class SuperPanelReplaceRequest {
    private long newdeviceid;
    private long olddeviceid;

    public SuperPanelReplaceRequest(long olddeviceid, long newdeviceid) {
        this.olddeviceid = olddeviceid;
        this.newdeviceid = newdeviceid;
    }
}