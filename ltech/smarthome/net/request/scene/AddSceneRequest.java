package com.ltech.smarthome.net.request.scene;

import java.util.List;

/* loaded from: classes4.dex */
public class AddSceneRequest {
    private List<ContentInfo> content;
    private long floorid;
    private int imgindex;
    private long placeid;
    private long roomid;
    private int sceneindex;
    private String scenename;
    private int scenetype;

    public static final class ContentInfo {
        public String action;
        public int actiontype;
        public String actiontypename;
        public String executecommand;
        public int timespace;
    }

    public AddSceneRequest(int sceneindex, long placeid, String scenename, List<ContentInfo> content, int imgIndex, long floorid, long roomid) {
        this.sceneindex = sceneindex;
        this.placeid = placeid;
        this.scenename = scenename;
        this.content = content;
        this.imgindex = imgIndex;
        this.floorid = floorid;
        this.roomid = roomid;
    }

    public AddSceneRequest(int sceneindex, long placeid, String scenename, List<ContentInfo> content, int imgIndex, long floorid, long roomid, int scenetype) {
        this.sceneindex = sceneindex;
        this.placeid = placeid;
        this.scenename = scenename;
        this.content = content;
        this.imgindex = imgIndex;
        this.floorid = floorid;
        this.roomid = roomid;
        this.scenetype = scenetype;
    }
}