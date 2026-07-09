package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class AppToken {
    private String access_token;
    private int created_at;
    private int expires_in;
    private long placeId;
    private long userId;

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getplaceId() {
        return this.placeId;
    }

    public void setplaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public int getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }
}