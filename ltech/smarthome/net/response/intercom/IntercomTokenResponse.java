package com.ltech.smarthome.net.response.intercom;

/* loaded from: classes4.dex */
public class IntercomTokenResponse {
    private String access_token;
    private int access_valid;
    private String refresh_token;
    private int refresh_valid;

    public String getAccess_token() {
        return this.access_token;
    }

    public String getRefresh_token() {
        return this.refresh_token;
    }

    public int getRefresh_valid() {
        return this.refresh_valid;
    }

    public int getAccess_valid() {
        return this.access_valid;
    }
}