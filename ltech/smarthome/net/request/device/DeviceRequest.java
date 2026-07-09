package com.ltech.smarthome.net.request.device;

import java.util.List;

/* loaded from: classes4.dex */
public class DeviceRequest {
    private long deviceid;
    private List<Devices> devices;
    private List<Groups> groups;
    private String mac;
    private int platform;
    private List<Scenes> scenes;

    public static class Devices {
        private long deviceid;
        private int sorting;

        public Devices(long deviceid, int sorting) {
            this.deviceid = deviceid;
            this.sorting = sorting;
        }
    }

    public static class Groups {
        private long groupid;
        private int sorting;

        public Groups(long groupid, int sorting) {
            this.groupid = groupid;
            this.sorting = sorting;
        }
    }

    public static class Scenes {
        private long sceneid;
        private int sorting;

        public Scenes(long sceneid, int sorting) {
            this.sceneid = sceneid;
            this.sorting = sorting;
        }
    }

    public int getPlatform() {
        return this.platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public long getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(long deviceid) {
        this.deviceid = deviceid;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<Devices> getDevices() {
        return this.devices;
    }

    public void setDevices(List<Devices> devices) {
        this.devices = devices;
    }

    public List<Groups> getGroups() {
        return this.groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    public List<Scenes> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<Scenes> scenes) {
        this.scenes = scenes;
    }
}