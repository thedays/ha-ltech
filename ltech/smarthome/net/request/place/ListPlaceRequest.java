package com.ltech.smarthome.net.request.place;

/* loaded from: classes4.dex */
public class ListPlaceRequest {
    private int pagenum;
    private int pagesize;
    private long placeid;
    private String placename;
    private long userid;

    public ListPlaceRequest(long userid) {
        this.userid = userid;
    }

    public ListPlaceRequest(long userid, long placeid) {
        this.userid = userid;
        this.placeid = placeid;
    }
}