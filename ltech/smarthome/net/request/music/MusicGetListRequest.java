package com.ltech.smarthome.net.request.music;

/* loaded from: classes4.dex */
public class MusicGetListRequest {
    private String mac;

    public MusicGetListRequest(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}