package com.ltech.smarthome.iot;

/* loaded from: classes3.dex */
public class RecDataBean {
    private String devicename;
    private String payload;
    private String productkey;

    public String getProductkey() {
        return this.productkey;
    }

    public void setProductkey(String productkey) {
        this.productkey = productkey;
    }

    public String getDevicename() {
        return this.devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getPayload() {
        return this.payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String toString() {
        return "RecDataBean{productkey='" + this.productkey + "', devicename='" + this.devicename + "', payload='" + this.payload + "'}";
    }
}