package com.ltech.smarthome.net.request.floor;

import java.util.List;

/* loaded from: classes4.dex */
public class AddFloorRequest {
    private String floorname;
    private long placeid;
    private List<String> rooms;

    public AddFloorRequest(long placeid, String floorname, List<String> rooms) {
        this.placeid = placeid;
        this.floorname = floorname;
        this.rooms = rooms;
    }
}