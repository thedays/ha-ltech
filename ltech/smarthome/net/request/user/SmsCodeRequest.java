package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class SmsCodeRequest {
    private int actiontype;
    private String mobilephone;

    public SmsCodeRequest(String mobilephone, int actiontype) {
        this.mobilephone = mobilephone;
        this.actiontype = actiontype;
    }

    public String toString() {
        return "SmsCodeRequest{mobilephone='" + this.mobilephone + "', actiontype=" + this.actiontype + '}';
    }
}