package com.ltech.smarthome.net.request.push;

/* loaded from: classes4.dex */
public class PushMsgPlaceListRequest {
    private int pagenum;
    private int pagesize;

    public PushMsgPlaceListRequest(int pagenum) {
        this.pagesize = 20;
        this.pagenum = pagenum;
    }

    public PushMsgPlaceListRequest(int pagesize, int pagenum) {
        this.pagesize = pagesize;
        this.pagenum = pagenum;
    }
}