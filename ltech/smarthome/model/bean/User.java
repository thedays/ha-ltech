package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class User {
    private long appId;
    private String deviceName;
    private String deviceSecret;
    private int devicetotal;
    private String email;
    private String headUrl;
    private String iotId;
    private String mobilePhone;
    private int placetotal;
    private String productKey;
    private String secretKey;
    private String session;
    private long userId;
    private String userName;

    public int getPlacetotal() {
        return this.placetotal;
    }

    public void setPlacetotal(int placetotal) {
        this.placetotal = placetotal;
    }

    public int getDevicetotal() {
        return this.devicetotal;
    }

    public void setDevicetotal(int devicetotal) {
        this.devicetotal = devicetotal;
    }

    public long getAppId() {
        return this.appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSession() {
        return this.session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceSecret() {
        return this.deviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret;
    }

    public String getIotId() {
        return this.iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHeadUrl() {
        return this.headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}