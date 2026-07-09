package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class ChangeEmailRequest {
    private String code;
    private String email;
    private long userid;

    public ChangeEmailRequest(long userid, String email, String code) {
        this.userid = userid;
        this.email = email;
        this.code = code;
    }
}