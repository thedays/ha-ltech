package com.ltech.smarthome.net.request.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class SetVoiceControlRangeRequest {
    private List<String> devices;
    private List<String> groups;
    private long panelid;
    private long placeid;
    private List<String> scenes;
    private int type;

    public SetVoiceControlRangeRequest(long panelid, int type, long placeid, List<String> devices, List<String> groups, List<String> scenes) {
        this.panelid = panelid;
        this.placeid = placeid;
        this.type = type;
        this.devices = devices;
        this.groups = groups;
        this.scenes = scenes;
    }
}