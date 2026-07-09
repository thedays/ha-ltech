package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class UpdateUserInfoRequest {
    private String headurl;
    private long userid;
    private String username;

    public UpdateUserInfoRequest(long userid) {
        this.userid = userid;
    }

    public UpdateUserInfoRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public UpdateUserInfoRequest setHeadUrl(String headUrl) {
        this.headurl = headUrl;
        return this;
    }
}