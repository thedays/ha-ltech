package com.ltech.smarthome.net.request.intercom;

/* loaded from: classes4.dex */
public class IntercomSetKeyRequest {
    private String key;
    private long placeid;

    public IntercomSetKeyRequest(String key, long placeId) {
        this.key = key;
        this.placeid = placeId;
    }
}