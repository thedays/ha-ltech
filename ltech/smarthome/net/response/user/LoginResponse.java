package com.ltech.smarthome.net.response.user;

/* loaded from: classes4.dex */
public class LoginResponse {
    private long appid;
    private String appname;
    private int devicetype;
    private String lastusetime;
    private String logintime;
    private int persisted;
    private String productid;
    private String roletype;
    private String secretkey;
    private String session;
    private long userid;
    private String username;

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public long getAppid() {
        return this.appid;
    }

    public void setAppid(long appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return this.appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserid() {
        return this.userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getRoletype() {
        return this.roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

    public String getLogintime() {
        return this.logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public String getLastusetime() {
        return this.lastusetime;
    }

    public void setLastusetime(String lastusetime) {
        this.lastusetime = lastusetime;
    }

    public int getDevicetype() {
        return this.devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }

    public String getSession() {
        return this.session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSecretkey() {
        return this.secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public int getPersisted() {
        return this.persisted;
    }

    public void setPersisted(int persisted) {
        this.persisted = persisted;
    }
}