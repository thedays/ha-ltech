package com.ltech.smarthome.net.response.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class SetSuperPanelResponse {
    private List<DevicesBean> devices;
    private List<GroupsBean> groups;
    private InfoBean info;
    private List<ScenesBean> scenes;

    public List<ScenesBean> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<ScenesBean> scenes) {
        this.scenes = scenes;
    }

    public List<GroupsBean> getGroups() {
        return this.groups;
    }

    public void setGroups(List<GroupsBean> groups) {
        this.groups = groups;
    }

    public InfoBean getInfo() {
        return this.info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<DevicesBean> getDevices() {
        return this.devices;
    }

    public void setDevices(List<DevicesBean> devices) {
        this.devices = devices;
    }

    public static class InfoBean {
        private String apppackage;
        private Object begincreatetime;
        private String createtime;
        private Object endcreatetime;
        private int pagenum;
        private int pagesize;
        private long panelid;
        private String panelname;
        private String provisioner;
        private int version;
        private String versionname;

        public long getPanelid() {
            return this.panelid;
        }

        public void setPanelid(long panelid) {
            this.panelid = panelid;
        }

        public String getPanelname() {
            return this.panelname;
        }

        public void setPanelname(String panelname) {
            this.panelname = panelname;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Object getBegincreatetime() {
            return this.begincreatetime;
        }

        public void setBegincreatetime(Object begincreatetime) {
            this.begincreatetime = begincreatetime;
        }

        public Object getEndcreatetime() {
            return this.endcreatetime;
        }

        public void setEndcreatetime(Object endcreatetime) {
            this.endcreatetime = endcreatetime;
        }

        public String getApppackage() {
            return this.apppackage;
        }

        public void setApppackage(String apppackage) {
            this.apppackage = apppackage;
        }

        public int getVersion() {
            return this.version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getVersionname() {
            return this.versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }

        public String getProvisioner() {
            return this.provisioner;
        }

        public void setProvisioner(String provisioner) {
            this.provisioner = provisioner;
        }

        public int getPagesize() {
            return this.pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public int getPagenum() {
            return this.pagenum;
        }

        public void setPagenum(int pagenum) {
            this.pagenum = pagenum;
        }
    }

    public static class DevicesBean {
        private long deviceid;

        public long getDeviceid() {
            return this.deviceid;
        }

        public void setDeviceid(long deviceid) {
            this.deviceid = deviceid;
        }
    }

    public static class ScenesBean {
        private long sceneid;

        public long getSceneid() {
            return this.sceneid;
        }

        public void setSceneid(long sceneid) {
            this.sceneid = sceneid;
        }
    }

    public static class GroupsBean {
        private long groupid;

        public long getGroupid() {
            return this.groupid;
        }

        public void setGroupid(long groupid) {
            this.groupid = groupid;
        }
    }
}