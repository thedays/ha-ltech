package com.ltech.smarthome.net.request.place;

/* loaded from: classes4.dex */
public class UpdatePlaceRequest {
    private String appToken;
    private int createdAt;
    private int expiresIn;
    private String imgurl;
    private int ivindex;
    private double latitude;
    private String location;
    private double longitude;
    private long placeid;
    private String placename;
    private long userid;

    public UpdatePlaceRequest(long placeid, long userid) {
        this.placeid = placeid;
        this.userid = userid;
    }

    public UpdatePlaceRequest setPlacename(String placename) {
        this.placename = placename;
        return this;
    }

    public UpdatePlaceRequest setImgurl(String imgurl) {
        this.imgurl = imgurl;
        return this;
    }

    public UpdatePlaceRequest setLocation(String location) {
        this.location = location;
        return this;
    }

    public UpdatePlaceRequest setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public UpdatePlaceRequest setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public UpdatePlaceRequest setAppToken(String appToken) {
        this.appToken = appToken;
        return this;
    }

    public UpdatePlaceRequest setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public UpdatePlaceRequest setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UpdatePlaceRequest setIvIndex(int ivindex) {
        this.ivindex = ivindex;
        return this;
    }
}