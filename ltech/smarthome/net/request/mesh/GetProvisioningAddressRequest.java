package com.ltech.smarthome.net.request.mesh;

/* loaded from: classes4.dex */
public class GetProvisioningAddressRequest {
    private long placeid;
    private long userid;

    public GetProvisioningAddressRequest(long userid, long placeid) {
        this.userid = userid;
        this.placeid = placeid;
    }
}