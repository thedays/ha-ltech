package com.ltech.smarthome.net.request.placeuser;

import java.util.List;

/* loaded from: classes4.dex */
public class AddPlaceUserRequest {
    private long placeid;
    private int roletype;
    private String roletypename;
    private List<RoomID> rooms;
    private long userid;

    public AddPlaceUserRequest(long placeid, long userid, int roletype) {
        this.placeid = placeid;
        this.userid = userid;
        this.roletype = roletype;
        this.roletypename = getRoleName(roletype);
    }

    public static class RoomID {
        private long roomid;

        public RoomID(long roomid) {
            this.roomid = roomid;
        }
    }

    private String getRoleName(int role) {
        if (role == 1) {
            return "所有者";
        }
        if (role == 2) {
            return "管理员";
        }
        return "成员";
    }
}