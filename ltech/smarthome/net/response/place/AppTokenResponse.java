package com.ltech.smarthome.net.response.place;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import com.videogo.util.LocalInfo;
import no.nordicsemi.android.log.LogContract;

/* loaded from: classes4.dex */
public class AppTokenResponse {

    @SerializedName(LocalInfo.ACCESS_TOKEN)
    private String access_token;

    @SerializedName(LogContract.SessionColumns.CREATED_AT)
    private int created_at;

    @SerializedName("expires_in")
    private int expires_in;

    @SerializedName("refresh_token")
    private String refresh_token;

    @SerializedName("token_type")
    @JsonInclude
    private String token_type;

    public String getToken_type() {
        return this.token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return this.refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
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

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String toString() {
        return "AppTokenResponse{token_type='" + this.token_type + "', refresh_token='" + this.refresh_token + "', expires_in=" + this.expires_in + ", created_at=" + this.created_at + ", access_token='" + this.access_token + "'}";
    }
}