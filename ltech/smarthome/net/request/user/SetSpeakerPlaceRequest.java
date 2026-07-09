package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class SetSpeakerPlaceRequest {
    private long placeid;
    private String platform;

    public SetSpeakerPlaceRequest(long placeid, String platform) {
        this.placeid = placeid;
        this.platform = platform;
    }
}