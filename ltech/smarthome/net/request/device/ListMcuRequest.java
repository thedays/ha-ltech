package com.ltech.smarthome.net.request.device;

/* loaded from: classes4.dex */
public class ListMcuRequest {
    public String appversionnum;
    private String mac;

    public ListMcuRequest() {
    }

    public ListMcuRequest(String mac) {
        this.mac = mac;
    }

    public ListMcuRequest(int appversionnum) {
        this.appversionnum = appversionnum + "";
    }
}