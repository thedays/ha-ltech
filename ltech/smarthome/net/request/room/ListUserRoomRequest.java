package com.ltech.smarthome.net.request.room;

/* loaded from: classes4.dex */
public class ListUserRoomRequest {
    private String floorid;
    private int pagenum;
    private int pagesize;
    private long placeid;
    private long roomid;
    private String roomname;
    private String userid;

    public ListUserRoomRequest(long placeid) {
        this.placeid = placeid;
    }
}