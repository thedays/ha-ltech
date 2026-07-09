package com.ltech.smarthome.model.bean;

import java.util.Objects;

/* loaded from: classes4.dex */
public class Place {
    private String appKey;
    private String appToken;
    private int createdAt;
    private long currentUserId;
    private int devicetotal;
    private int expiresIn;
    private int floortotal;
    private long id;
    private String imageUrl;
    private int ivindex;
    private double latitude;
    private String location;
    private double longitude;
    private String meshUUID;
    private String netKey;
    private long placeId;
    private String placeName;
    private String provisionerAddress;
    private String qrCode;
    private int roleType;
    private int roomtotal;
    private long userId;

    public int getFloortotal() {
        return this.floortotal;
    }

    public void setFloortotal(int floortotal) {
        this.floortotal = floortotal;
    }

    public int getRoomtotal() {
        return this.roomtotal;
    }

    public void setRoomtotal(int roomtotal) {
        this.roomtotal = roomtotal;
    }

    public int getDevicetotal() {
        return this.devicetotal;
    }

    public void setDevicetotal(int devicetotal) {
        this.devicetotal = devicetotal;
    }

    public String getProvisionerAddress() {
        return this.provisionerAddress;
    }

    public void setProvisionerAddress(String provisionerAddress) {
        this.provisionerAddress = provisionerAddress;
    }

    public int getIvindex() {
        return this.ivindex;
    }

    public void setIvindex(int ivindex) {
        this.ivindex = ivindex;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return this.placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getRoleType() {
        return this.roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNetKey() {
        return this.netKey;
    }

    public void setNetKey(String netKey) {
        this.netKey = netKey;
    }

    public String getAppKey() {
        return "63964771734FBD76E3B40519D1D94A48";
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMeshUUID() {
        return this.meshUUID;
    }

    public void setMeshUUID(String meshUUID) {
        this.meshUUID = meshUUID;
    }

    public boolean isMember() {
        return 3 == this.roleType;
    }

    public boolean isOwner() {
        return 1 == this.roleType;
    }

    public boolean isManager() {
        return 2 == this.roleType;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setOwner() {
        this.roleType = 1;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAppToken() {
        return this.appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && getClass() == o.getClass()) {
            Place place = (Place) o;
            if (this.id == place.id && this.placeId == place.placeId && this.userId == place.userId && this.currentUserId == place.currentUserId && this.roleType == place.roleType && Double.compare(this.latitude, place.latitude) == 0 && Double.compare(this.longitude, place.longitude) == 0 && this.expiresIn == place.expiresIn && this.createdAt == place.createdAt && Objects.equals(this.placeName, place.placeName) && Objects.equals(this.qrCode, place.qrCode) && Objects.equals(this.location, place.location) && Objects.equals(this.imageUrl, place.imageUrl) && Objects.equals(this.netKey, place.netKey) && Objects.equals(this.appKey, place.appKey) && Objects.equals(this.meshUUID, place.meshUUID) && Objects.equals(this.provisionerAddress, place.provisionerAddress) && Objects.equals(this.appToken, place.appToken) && Objects.equals(Integer.valueOf(this.ivindex), Integer.valueOf(place.ivindex))) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public long getCurrentUserId() {
        return this.currentUserId;
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String toString() {
        return "Place{id=" + this.id + ", placeId=" + this.placeId + ", userId=" + this.userId + ", currentUserId=" + this.currentUserId + ", placeName='" + this.placeName + "', roleType=" + this.roleType + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", qrCode='" + this.qrCode + "', location='" + this.location + "', imageUrl='" + this.imageUrl + "', netKey='" + this.netKey + "', appKey='" + this.appKey + "', meshUUID='" + this.meshUUID + "', provisionerAddress='" + this.provisionerAddress + "', expiresIn=" + this.expiresIn + ", createdAt=" + this.createdAt + ", appToken='" + this.appToken + "', ivindex=" + this.ivindex + '}';
    }
}