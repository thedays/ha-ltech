package com.ltech.smarthome.model.bean;

import com.amap.api.maps2d.model.LatLng;

/* loaded from: classes4.dex */
public class LocationInfo {
    private long currentTime;
    private LatLng location;

    public LocationInfo(long currentTime, LatLng location) {
        this.currentTime = currentTime;
        this.location = location;
    }

    public long getCurrentTime() {
        return this.currentTime;
    }

    public LatLng getLocation() {
        return this.location;
    }
}