package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class RegisterRequest {
    private String code;
    private String email;
    private long memberid;
    private String mobilephone;
    private String pwd;
    private String sysnode;
    private String type;

    public RegisterRequest(long memberid, String type, String account, String code, String pwd, String sysnode) {
        this.memberid = memberid;
        this.type = type;
        this.code = code;
        this.pwd = pwd;
        this.sysnode = sysnode;
        if ("1".equals(type)) {
            this.mobilephone = account;
        } else if ("2".equals(type)) {
            this.email = account;
        }
    }
}