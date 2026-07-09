package com.ltech.smarthome.net.request.user;

/* loaded from: classes4.dex */
public class OauthBindRequest {
    private String code;
    private String devicetype;
    private String email;
    private String memberid;
    private String mobilephone;
    private String openid;
    private String password;
    private String source;
    private String type;

    public OauthBindRequest(String openid, String memberId, String code, String deviceType, String type, String source, String account, String password) {
        this.openid = openid;
        this.memberid = memberId;
        this.code = code;
        this.devicetype = deviceType;
        this.type = type;
        this.source = source;
        this.password = password;
        if ("1".equals(type)) {
            this.mobilephone = account;
        } else if ("2".equals(type)) {
            this.email = account;
        }
    }
}