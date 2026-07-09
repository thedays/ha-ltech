package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class OauthLoginRequest {
    private String accesstoken;
    private String devicetype;
    private String name;
    private String openid;
    private String source;

    public OauthLoginRequest(String openid, String source, String accessToken, String deviceType, String name) {
        this.openid = openid;
        this.source = source;
        this.accesstoken = accessToken;
        this.devicetype = deviceType;
        this.name = name;
    }
}