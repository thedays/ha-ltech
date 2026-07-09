package com.ltech.smarthome.net.request.place;

import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.net.request.room.RoomName;
import java.util.List;

/* loaded from: classes4.dex */
public class AddPlaceRequest {
    private String applicationkey;
    private String floorname = ActivityUtils.getTopActivity().getString(R.string.app_str_first_floor);
    private String imgurl;
    private double latitude;
    private String location;
    private double longitude;
    private String meshuuid;
    private String netkey;
    private String placename;
    private List<RoomName> rooms;

    public AddPlaceRequest(String placename, String location, double latitude, double longitude, List<RoomName> rooms, String netKey, String appKey, String meshuuid) {
        this.placename = placename;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rooms = rooms;
        this.netkey = netKey;
        this.applicationkey = appKey;
        this.meshuuid = meshuuid;
    }
}