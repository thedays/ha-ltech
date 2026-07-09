package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class SetPasswordRequest {
    private String code;
    private String email;
    private String memberid;
    private String mobilephone;
    private String pwd;
    private String type;

    public SetPasswordRequest(String memberid, String account, String pwd, String code, String type) {
        this.memberid = memberid;
        this.pwd = pwd;
        this.code = code;
        this.type = type;
        if ("1".equals(type)) {
            this.mobilephone = account;
        } else if ("2".equals(type)) {
            this.email = account;
        }
    }
}