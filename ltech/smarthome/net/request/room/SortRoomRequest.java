package com.ltech.smarthome.net.request.room;

import java.util.List;

/* loaded from: classes4.dex */
public class SortRoomRequest {
    private List<RoomSortBean> rooms;

    public static final class RoomSortBean {
        public long roomid;
    }

    public SortRoomRequest(List<RoomSortBean> rooms) {
        this.rooms = rooms;
    }
}