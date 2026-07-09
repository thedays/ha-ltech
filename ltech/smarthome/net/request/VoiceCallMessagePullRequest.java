package com.ltech.smarthome.net.request;

import java.util.List;

/* loaded from: classes4.dex */
public class VoiceCallMessagePullRequest {
    private long panelvoicegroupid;
    private int statusType;
    private long userid;
    private List<Long> userids;

    public int getStatusType() {
        return this.statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    public long getPanelvoicegroupid() {
        return this.panelvoicegroupid;
    }

    public void setPanelvoicegroupid(long panelvoicegroupid) {
        this.panelvoicegroupid = panelvoicegroupid;
    }

    public long getUserid() {
        return this.userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public List<Long> getUserids() {
        return this.userids;
    }

    public void setUserids(List<Long> userids) {
        this.userids = userids;
    }
}