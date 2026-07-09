package com.ltech.smarthome.net.request.device;

import java.util.List;

/* loaded from: classes4.dex */
public class PanelSortInfoRequest {
    private List<DeviceSortBean> devices;
    private List<GroupSortBean> groups;
    private long panelid;
    private List<SceneSortBean> scenes;

    public PanelSortInfoRequest(long panelid, List<DeviceSortBean> devices, List<GroupSortBean> groups) {
        this.panelid = panelid;
        this.devices = devices;
        this.groups = groups;
    }

    public PanelSortInfoRequest(long panelid, List<SceneSortBean> scenes) {
        this.panelid = panelid;
        this.scenes = scenes;
    }

    public PanelSortInfoRequest(long panelid, List<DeviceSortBean> devices, List<GroupSortBean> groups, List<SceneSortBean> scenes) {
        this.panelid = panelid;
        this.devices = devices;
        this.groups = groups;
        this.scenes = scenes;
    }

    public long getPanelid() {
        return this.panelid;
    }

    public void setPanelid(long panelid) {
        this.panelid = panelid;
    }

    public List<DeviceSortBean> getDevices() {
        return this.devices;
    }

    public void setDevices(List<DeviceSortBean> devices) {
        this.devices = devices;
    }

    public List<GroupSortBean> getGroups() {
        return this.groups;
    }

    public void setGroups(List<GroupSortBean> groups) {
        this.groups = groups;
    }

    public List<SceneSortBean> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<SceneSortBean> scenes) {
        this.scenes = scenes;
    }

    public static final class DeviceSortBean {
        public long deviceid;
        public int sorting;

        public long getDeviceid() {
            return this.deviceid;
        }

        public void setDeviceid(long deviceid) {
            this.deviceid = deviceid;
        }

        public int getSorting() {
            return this.sorting;
        }

        public void setSorting(int sorting) {
            this.sorting = sorting;
        }
    }

    public static final class GroupSortBean {
        public long groupid;
        public int sorting;

        public long getGroupid() {
            return this.groupid;
        }

        public void setGroupid(long groupid) {
            this.groupid = groupid;
        }

        public int getSorting() {
            return this.sorting;
        }

        public void setSorting(int sorting) {
            this.sorting = sorting;
        }
    }

    public static final class SceneSortBean {
        public long sceneid;
        public int sorting;

        public long getSceneid() {
            return this.sceneid;
        }

        public void setSceneid(long sceneid) {
            this.sceneid = sceneid;
        }

        public int getSorting() {
            return this.sorting;
        }

        public void setSorting(int sorting) {
            this.sorting = sorting;
        }
    }
}