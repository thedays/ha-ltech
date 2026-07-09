package com.ltech.smarthome.net.request.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class SuperPanelRequest {
    private List<Long> devices;
    private long panelid;

    public SuperPanelRequest(long panelid) {
        this.panelid = panelid;
    }

    public long getPanelid() {
        return this.panelid;
    }

    public void setPanelid(long panelid) {
        this.panelid = panelid;
    }

    public List<Long> getDevices() {
        return this.devices;
    }

    public void setDevices(List<Long> devices) {
        this.devices = devices;
    }
}