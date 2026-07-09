package com.ltech.smarthome.net.request.group;

import java.util.List;

/* loaded from: classes4.dex */
public class AddGroupRequest {
    private String colortype;
    private List<AddGroupContent> devices;
    private long floorid;
    private String groupname;
    private String param;
    private String paramext;
    private long placeid;
    private long roomid;
    private String type;

    public static final class AddGroupContent {
        public long deviceid;
    }

    public AddGroupRequest(long placeid, long floorid, long roomid, String groupname, String grouptype, String colortype, List<AddGroupContent> devices) {
        this.placeid = placeid;
        this.floorid = floorid;
        this.roomid = roomid;
        this.groupname = groupname;
        this.type = grouptype;
        this.colortype = colortype;
        this.devices = devices;
    }

    public AddGroupRequest(long placeid, long floorid, long roomid, String groupname, String grouptype, String colortype, List<AddGroupContent> devices, String paramext) {
        this.placeid = placeid;
        this.floorid = floorid;
        this.roomid = roomid;
        this.groupname = groupname;
        this.type = grouptype;
        this.colortype = colortype;
        this.devices = devices;
        this.paramext = paramext;
    }

    public AddGroupRequest(long placeid, long floorid, long roomid, String groupname, String type, String colortype, List<AddGroupContent> devices, String paramext, String param) {
        this.placeid = placeid;
        this.floorid = floorid;
        this.roomid = roomid;
        this.groupname = groupname;
        this.type = type;
        this.colortype = colortype;
        this.devices = devices;
        this.paramext = paramext;
        this.param = param;
    }
}