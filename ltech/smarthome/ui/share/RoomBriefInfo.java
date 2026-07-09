package com.ltech.smarthome.ui.share;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/* loaded from: classes4.dex */
public class RoomBriefInfo implements MultiItemEntity {
    public static final int TYPE = 1;
    private long roomId;
    private String roomName;

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return 1;
    }

    public RoomBriefInfo(String roomName, long roomId) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}