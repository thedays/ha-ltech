package com.ltech.smarthome.net.request.room;

/* loaded from: classes4.dex */
public class ListRoomRequest {
    private long floorid;
    private int pagenum;
    private int pagesize;
    private long placeid;
    private String roomname;
    private long userid;

    public ListRoomRequest(long floorid) {
        this.floorid = floorid;
    }

    public ListRoomRequest(long placeid, long floorid, long userid) {
        this.placeid = placeid;
        this.floorid = floorid;
        this.userid = userid;
    }
}