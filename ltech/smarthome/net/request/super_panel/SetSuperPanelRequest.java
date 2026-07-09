package com.ltech.smarthome.net.request.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class SetSuperPanelRequest {
    private long deviceid;
    private List<DeviceContent> devices;
    private List<GroupContent> groups;
    private String mac;
    private long panelid;
    private int platform;
    private List<SceneContent> scenes;

    public SetSuperPanelRequest(long deviceid, String mac, int platform) {
        this.deviceid = deviceid;
        this.mac = mac;
        this.platform = platform;
    }

    public SetSuperPanelRequest(long panelid, String mac) {
        this.panelid = panelid;
        this.mac = mac;
    }

    public SetSuperPanelRequest(long panelid) {
        this.panelid = panelid;
    }

    public SetSuperPanelRequest setDevices(List<DeviceContent> devices) {
        this.devices = devices;
        return this;
    }

    public SetSuperPanelRequest setGroups(List<GroupContent> groups) {
        this.groups = groups;
        return this;
    }

    public SetSuperPanelRequest setScenes(List<SceneContent> scenes) {
        this.scenes = scenes;
        return this;
    }

    public static class DeviceContent {
        public long deviceid;
        public int sorting;

        public DeviceContent(long deviceid) {
            this.deviceid = deviceid;
        }

        public DeviceContent(long deviceid, int sorting) {
            this.deviceid = deviceid;
            this.sorting = sorting;
        }
    }

    public static class GroupContent {
        public long groupid;
        public int sorting;

        public GroupContent(long groupid) {
            this.groupid = groupid;
        }

        public GroupContent(long groupid, int sorting) {
            this.groupid = groupid;
            this.sorting = sorting;
        }
    }

    public static class SceneContent {
        public long sceneid;
        public int sorting;

        public SceneContent(long sceneid) {
            this.sceneid = sceneid;
        }

        public SceneContent(long sceneid, int sorting) {
            this.sceneid = sceneid;
            this.sorting = sorting;
        }
    }
}