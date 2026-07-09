package com.ltech.smarthome.net.request.device;

/* loaded from: classes4.dex */
public class GetDeviceOnlineRequest {
    private String mid;
    private String productid;

    public GetDeviceOnlineRequest(String productid, String platformdeviceid) {
        this.productid = productid;
        this.mid = platformdeviceid;
    }
}