package com.ltech.smarthome.net.request.role;

import java.util.List;

/* loaded from: classes4.dex */
public class UpdateRoomRoleRequest {
    private long placeid;
    private List<RoomId> rooms;
    private long userid;

    public UpdateRoomRoleRequest(long placeid, long userid, List rooms) {
        this.placeid = placeid;
        this.userid = userid;
        this.rooms = rooms;
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
}