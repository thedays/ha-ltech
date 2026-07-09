package com.ltech.smarthome.net.request.device;

/* loaded from: classes4.dex */
public class ListDeviceRequest {
    private int pagenum;
    private int pagesize;
    private long placeid;
    private long userid;

    public ListDeviceRequest(long placeid) {
        this.placeid = placeid;
    }

    public ListDeviceRequest(long placeid, long userid) {
        this.placeid = placeid;
        this.userid = userid;
    }

    public String toString() {
        return "ListDeviceRequest{placeid=" + this.placeid + ", pagesize=" + this.pagesize + ", pagenum=" + this.pagenum + ", userid=" + this.userid + '}';
    }
}