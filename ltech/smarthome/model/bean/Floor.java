package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class Floor {
    private long floorId;
    private String floorName;
    private long id;
    private int index;
    private long placeId;
    private long userId;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFloorId() {
        return this.floorId;
    }

    public void setFloorId(long floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return this.floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean equals(Object var1) {
        if (var1 instanceof Floor) {
            Floor floor = (Floor) var1;
            return floor.getFloorId() == getFloorId() && floor.getPlaceId() == getPlaceId() && floor.getUserId() == getUserId();
        }
        return super.equals(var1);
    }

    public int hashCode() {
        return super.hashCode();
    }
}