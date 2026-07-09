package com.ltech.smarthome.net.response.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class QuerySuperPanelVoiceControlRangeResponse {
    private List<Long> devices;
    private List<Long> groups;
    private long panelid;
    private long placeid;
    private List<Long> scenes;
    private int type;

    public long getPanelid() {
        return this.panelid;
    }

    public void setPanelid(long panelid) {
        this.panelid = panelid;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getPlaceid() {
        return this.placeid;
    }

    public void setPlaceid(long placeid) {
        this.placeid = placeid;
    }

    public List<Long> getDevices() {
        return this.devices;
    }

    public void setDevices(List<Long> devices) {
        this.devices = devices;
    }

    public List<Long> getGroups() {
        return this.groups;
    }

    public void setGroups(List<Long> groups) {
        this.groups = groups;
    }

    public List<Long> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<Long> scenes) {
        this.scenes = scenes;
    }

    public String toString() {
        return "QuerySuperPanelVoiceControlRangeResponse{panelid=" + this.panelid + ", type=" + this.type + ", placeid=" + this.placeid + ", devices=" + this.devices + ", groups=" + this.groups + ", scenes=" + this.scenes + '}';
    }
}