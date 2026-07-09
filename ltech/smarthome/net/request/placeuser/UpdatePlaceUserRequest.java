package com.ltech.smarthome.net.request.placeuser;

/* loaded from: classes4.dex */
public class UpdatePlaceUserRequest {
    private long placeuserid;
    private String remark;
    private int roletype;

    public UpdatePlaceUserRequest(long placeuserid, int roletype, String remark) {
        this.placeuserid = placeuserid;
        this.roletype = roletype;
        this.remark = remark;
    }
}