package com.ltech.smarthome.net.response.group;

import java.util.List;

/* loaded from: classes4.dex */
public class ListDaliGroupResponse {
    private List<GroupInfo> data;
    private String message;
    private int ret;

    public static class GroupInfo {
        private List<DeviceInfo> devices;
        private long groupid;
        private String groupname;

        public long getGroupid() {
            return this.groupid;
        }

        public void setGroupid(long groupid) {
            this.groupid = groupid;
        }

        public String getGroupname() {
            return this.groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public List<DeviceInfo> getDevices() {
            return this.devices;
        }

        public void setDevices(List<DeviceInfo> devices) {
            this.devices = devices;
        }

        public String toString() {
            return "GroupInfo{groupid=" + this.groupid + ", groupname='" + this.groupname + "', devices=" + this.devices + '}';
        }
    }

    public static class DeviceInfo {
        private long deviceid;
        private String devicename;

        public Long getDeviceid() {
            return Long.valueOf(this.deviceid);
        }

        public void setDeviceid(long deviceid) {
            this.deviceid = deviceid;
        }

        public String getDevicename() {
            return this.devicename;
        }

        public void setDevicename(String devicename) {
            this.devicename = devicename;
        }

        public String toString() {
            return "DeviceInfo{deviceid=" + this.deviceid + ", devicename='" + this.devicename + "'}";
        }
    }

    public int getRet() {
        return this.ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GroupInfo> getData() {
        return this.data;
    }

    public void setData(List<GroupInfo> data) {
        this.data = data;
    }

    public String toString() {
        return "ListDaliGroupResponse{ret=" + this.ret + ", message='" + this.message + "', data=" + this.data + '}';
    }
}