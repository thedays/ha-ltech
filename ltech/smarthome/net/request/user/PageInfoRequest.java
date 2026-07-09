package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class PageInfoRequest {
    private int pagenum;
    private int pagesize;

    public PageInfoRequest(int pagesize, int pagenum) {
        this.pagesize = pagesize;
        this.pagenum = pagenum;
    }

    public int getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPagenum() {
        return this.pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }
}