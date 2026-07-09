package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class LoginRequest {
    private String devicesn;
    private String devicetype;
    private String loginname;
    private long memberid;
    private String pwd;

    public LoginRequest(long memberId, String loginName, String pwd, String deviceType, String devicesn) {
        this.memberid = memberId;
        this.loginname = loginName;
        this.pwd = pwd;
        this.devicetype = deviceType;
        this.devicesn = devicesn;
    }

    public LoginRequest(long memberId, String loginName, String deviceType, String devicesn) {
        this.memberid = memberId;
        this.loginname = loginName;
        this.devicetype = deviceType;
        this.devicesn = devicesn;
    }
}