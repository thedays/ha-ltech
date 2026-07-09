package com.ltech.smarthome.ui.item;

import com.ltech.smarthome.net.response.intercom.GetOpenDoorLogResponse;

/* loaded from: classes4.dex */
public class OpenDoorLogItem {
    private GetOpenDoorLogResponse.OpenDoorBean item;
    private int positionType;
    private String title;
    private int type;

    public OpenDoorLogItem(int type) {
        this.type = type;
    }

    public OpenDoorLogItem(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public OpenDoorLogItem(int type, GetOpenDoorLogResponse.OpenDoorBean item, int positionType) {
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

    public GetOpenDoorLogResponse.OpenDoorBean getItem() {
        return this.item;
    }

    public int getPositionType() {
        return this.positionType;
    }

    public void setPositionType(int positionType) {
        this.positionType = positionType;
    }
}