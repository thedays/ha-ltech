package com.ltech.smarthome.model.bean;

import android.content.Context;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class PlaceUser {
    private long currentUserId;
    private String email;
    private String headUrl;
    private String mobilephone;
    private long placeId;
    private long placeUserId;
    private String remark;
    private int roleType;
    private long userId;
    private String userName;

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPlaceUserId() {
        return this.placeUserId;
    }

    public void setPlaceUserId(long placeUserId) {
        this.placeUserId = placeUserId;
    }

    public long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public int getRoleType() {
        return this.roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobilephone() {
        return this.mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getRoleString(Context context, int roleType) {
        if (roleType == 1) {
            return context.getString(R.string.owner);
        }
        if (roleType == 2) {
            return context.getString(R.string.manager);
        }
        return context.getString(R.string.member);
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

    public long getCurrentUserId() {
        return this.currentUserId;
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getHeadUrl() {
        return this.headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}