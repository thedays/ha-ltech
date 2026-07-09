package com.ltech.smarthome.net.response.intercom;

import com.ltech.smarthome.net.response.intercom.KeyListResponse;

/* loaded from: classes4.dex */
public class CreateKeyResponse {
    private KeyListResponse.OpenDoorTempKey data;
    private String message;
    private int ret;

    public int getRet() {
        return this.ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public KeyListResponse.OpenDoorTempKey getData() {
        return this.data;
    }

    public void setData(KeyListResponse.OpenDoorTempKey data) {
        this.data = data;
    }
}