package com.ltech.smarthome.net.request.group;

import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class UpdateGroupRequest {
    private List<UpdateGroupContent> devices;
    private long floorid;
    private long groupid;
    private String groupname;
    private String instruct;
    private HashMap<String, Object> paramMap = new HashMap<>();
    private long roomid;

    public static final class UpdateGroupContent {
        public long deviceid;
    }

    public UpdateGroupRequest() {
    }

    public UpdateGroupRequest(long groupid, String groupname) {
        this.groupid = groupid;
        this.groupname = groupname;
    }

    public UpdateGroupRequest(long groupid, long floorid, long roomid) {
        this.groupid = groupid;
        this.floorid = floorid;
        this.roomid = roomid;
    }

    public UpdateGroupRequest(long groupid, List<UpdateGroupContent> devices) {
        this.groupid = groupid;
        this.devices = devices;
    }

    public void setParamExt(String paramExt) {
        if (paramExt == null || paramExt.isEmpty()) {
            paramExt = "{}";
        }
        this.paramMap.put("paramext", paramExt);
    }

    public long getGroupid() {
        return this.groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public long getFloorid() {
        return this.floorid;
    }

    public void setFloorid(long floorid) {
        this.floorid = floorid;
    }

    public long getRoomid() {
        return this.roomid;
    }

    public void setRoomid(long roomid) {
        this.roomid = roomid;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<UpdateGroupContent> getDevices() {
        return this.devices;
    }

    public void setDevices(List<UpdateGroupContent> devices) {
        this.devices = devices;
    }

    public String getInstruct() {
        return this.instruct;
    }

    public void setInstruct(String instruct) {
        this.instruct = instruct;
    }
}