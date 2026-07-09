package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class EmailCodeRequest {
    private int actiontype;
    private String emails;

    public EmailCodeRequest(String emails, int actiontype) {
        this.emails = emails;
        this.actiontype = actiontype;
    }
}