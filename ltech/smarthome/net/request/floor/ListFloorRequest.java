package com.ltech.smarthome.net.request.floor;

/* loaded from: classes4.dex */
public class ListFloorRequest {
    private String floorname;
    private int pagenum;
    private int pagesize;
    private long placeid;

    public ListFloorRequest(long placeid) {
        this.placeid = placeid;
    }
}