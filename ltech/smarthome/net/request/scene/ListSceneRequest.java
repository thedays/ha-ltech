package com.ltech.smarthome.net.request.scene;

/* loaded from: classes4.dex */
public class ListSceneRequest {
    private long floorid;
    private int pagenum;
    private int pagesize;
    private long placeid;
    private long roomid;
    private String scenename;
    private int scenetype;
    private long userid;

    public ListSceneRequest(long placeid, int scenetype, long userid) {
        this.placeid = placeid;
        this.userid = userid;
        this.scenetype = scenetype;
    }

    public ListSceneRequest(long placeid, int scenetype, long userid, long floorid, long roomid) {
        this.placeid = placeid;
        this.userid = userid;
        this.scenetype = scenetype;
        this.floorid = floorid;
        this.roomid = roomid;
    }
}