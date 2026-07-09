package com.ltech.smarthome.net.request.place;

import com.alibaba.fastjson.JSONObject;

/* loaded from: classes4.dex */
public class UpdatePlaceDataRequest {
    private long placeid;
    private JSONObject propertys;

    public UpdatePlaceDataRequest(long placeId, JSONObject data) {
        this.placeid = placeId;
        this.propertys = data;
    }
}