package com.ltech.smarthome.net.request.room;

/* loaded from: classes4.dex */
public class UpdateRoomRequest {
    private long roomid;
    private String roomname;

    public UpdateRoomRequest(long roomid, String roomname) {
        this.roomid = roomid;
        this.roomname = roomname;
    }
}