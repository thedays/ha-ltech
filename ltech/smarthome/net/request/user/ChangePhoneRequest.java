package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class ChangePhoneRequest {
    private String code;
    private String mobilephone;
    private long userid;

    public ChangePhoneRequest(long userid, String mobilephone, String code) {
        this.userid = userid;
        this.mobilephone = mobilephone;
        this.code = code;
    }
}