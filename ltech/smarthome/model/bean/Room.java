package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class Room {
    private long floorId;
    private long id;
    private int index;
    private long placeId;
    private long roomId;
    private String roomName;
    private long userId;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getFloorId() {
        return this.floorId;
    }

    public void setFloorId(long floorId) {
        this.floorId = floorId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean equals(Object var1) {
        if (var1 instanceof Room) {
            Room room = (Room) var1;
            return room.getFloorId() == getFloorId() && room.getPlaceId() == getPlaceId() && room.getUserId() == getUserId() && room.getRoomId() == getRoomId();
        }
        return super.equals(var1);
    }

    public int hashCode() {
        return super.hashCode();
    }
}