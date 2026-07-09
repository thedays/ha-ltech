package com.ltech.smarthome.net.request.floor;

/* loaded from: classes4.dex */
public class UpdateFloorRequest {
    private long floorid;
    private String floorname;

    public UpdateFloorRequest(long floorId, String floorname) {
        this.floorid = floorId;
        this.floorname = floorname;
    }
}