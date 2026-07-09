package com.ltech.smarthome.net.request.push;

/* loaded from: classes4.dex */
public class PushMsgAppNoticeListRequest {
    private int pagenum;
    private int pagesize;

    public PushMsgAppNoticeListRequest() {
        this.pagesize = 20;
    }

    public PushMsgAppNoticeListRequest(int pagenum) {
        this.pagesize = 20;
        this.pagenum = pagenum;
    }

    public PushMsgAppNoticeListRequest(int pagesize, int pagenum) {
        this.pagesize = pagesize;
        this.pagenum = pagenum;
    }
}