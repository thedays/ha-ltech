package com.ltech.smarthome.net.response.intercom;

/* loaded from: classes4.dex */
public class UploadFaceResponse {
    private String Account;
    private String message;
    private int result;

    public void setResult(int result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAccount(String account) {
        this.Account = account;
    }

    public int getResult() {
        return this.result;
    }

    public String getMessage() {
        return this.message;
    }

    public String getAccount() {
        return this.Account;
    }
}