package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class QuerySpeakerPlaceRequest {
    private long placeid;
    private String platform;

    public QuerySpeakerPlaceRequest(String platform, long placeid) {
        this.platform = platform;
        this.placeid = placeid;
    }
}