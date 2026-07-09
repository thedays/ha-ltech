package com.ltech.smarthome.net.request.photo;

/* loaded from: classes4.dex */
public class ListPhotoRequest {
    private String mac;
    private long panelid;

    public ListPhotoRequest(long panelid, String mac) {
        this.panelid = panelid;
        this.mac = mac;
    }
}