package com.ltech.smarthome.net.request.intercom;

/* loaded from: classes4.dex */
public class ImportCommunityRequest {
    private long communityID;
    private long placeid;

    public ImportCommunityRequest(long communityID, long placeid) {
        this.communityID = communityID;
        this.placeid = placeid;
    }

    public long getCommunityID() {
        return this.communityID;
    }

    public void setCommunityID(long communityID) {
        this.communityID = communityID;
    }

    public long getPlaceid() {
        return this.placeid;
    }

    public void setPlaceid(long placeid) {
        this.placeid = placeid;
    }
}