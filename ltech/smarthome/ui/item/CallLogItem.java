package com.ltech.smarthome.ui.item;

import com.ltech.smarthome.net.response.intercom.GetCallLogResponse;

/* loaded from: classes4.dex */
public class CallLogItem {
    private GetCallLogResponse.LogBean item;
    private int positionType;
    private String title;
    private int type;

    public CallLogItem(int type) {
        this.type = type;
    }

    public CallLogItem(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public CallLogItem(int type, GetCallLogResponse.LogBean item, int positionType) {
        this.type = type;
        this.item = item;
        this.positionType = positionType;
    }

    public int getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public GetCallLogResponse.LogBean getItem() {
        return this.item;
    }

    public int getPositionType() {
        return this.positionType;
    }
}