package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class ChangePwdRequest {
    private String oldpwd;
    private String pwd;

    public ChangePwdRequest(String oldpwd, String pwd) {
        this.oldpwd = oldpwd;
        this.pwd = pwd;
    }
}