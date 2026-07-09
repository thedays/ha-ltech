package com.ltech.smarthome.net.response;

/* loaded from: classes4.dex */
public class ResultBean<T> {
    private T data;
    private String message;
    private int ret;

    public int getRet() {
        return this.ret;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }
}