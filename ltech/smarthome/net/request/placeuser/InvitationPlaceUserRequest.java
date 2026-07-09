package com.ltech.smarthome.net.request.placeuser;

import java.util.List;

/* loaded from: classes4.dex */
public class InvitationPlaceUserRequest {
    private List<DeviceId> devices;
    private String email;
    private List<GroupId> groups;
    private String mobilephone;
    private long placeid;
    private int roletype;
    private String roletypename;
    private List<RoomId> rooms;
    private List<SceneId> scenes;
    private long time;
    private long userid;

    public InvitationPlaceUserRequest(long placeid, long userid, int roletype) {
        this(placeid, userid, "", "", roletype, null, null, null, null, 0L);
    }

    public InvitationPlaceUserRequest(long placeid, String mobilephone, int roletype) {
        this(placeid, 0L, mobilephone, "", roletype, null, null, null, null, 0L);
    }

    public InvitationPlaceUserRequest(long placeid, long userid, String mobilephone, String email, int roletype, List scenes, List rooms, List devices, List groups, long time) {
        this.placeid = placeid;
        this.userid = userid;
        this.mobilephone = mobilephone;
        this.email = email;
        this.roletype = roletype;
        this.roletypename = getRoleName(roletype);
        this.scenes = scenes;
        this.rooms = rooms;
        this.devices = devices;
        this.groups = groups;
        this.time = time;
    }

    public static class SceneId {
        private long sceneid;

        public SceneId(long sceneid) {
            this.sceneid = sceneid;
        }

        public long getSceneid() {
            return this.sceneid;
        }
    }

    public static class RoomId {
        private long roomid;

        public RoomId(long roomid) {
            this.roomid = roomid;
        }

        public long getRoomid() {
            return this.roomid;
        }
    }

    public static class DeviceId {
        private long deviceid;

        public DeviceId(long deviceid) {
            this.deviceid = deviceid;
        }

        public long getDeviceid() {
            return this.deviceid;
        }
    }

    public static class GroupId {
        private long groupid;

        public GroupId(long groupid) {
            this.groupid = groupid;
        }

        public long getGroupid() {
            return this.groupid;
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