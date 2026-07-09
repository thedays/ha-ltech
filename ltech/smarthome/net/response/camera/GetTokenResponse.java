package com.ltech.smarthome.net.response.camera;

/* loaded from: classes4.dex */
public class GetTokenResponse {
    private String accesstoken;
    private long expiretime;

    public String getAccesstoken() {
        return this.accesstoken;
    }

    public void setAccesstoken(String accessToken) {
        this.accesstoken = accessToken;
    }

    public long getExpiretime() {
        return this.expiretime;
    }

    public void setExpiretime(long expiretime) {
        this.expiretime = expiretime;
    }
}