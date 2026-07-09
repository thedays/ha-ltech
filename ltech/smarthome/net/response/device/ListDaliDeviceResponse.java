package com.ltech.smarthome.net.response.device;

import java.util.List;

/* loaded from: classes4.dex */
public class ListDaliDeviceResponse {
    private List<DeviceInfo> devices;

    public List<DeviceInfo> getDevices() {
        return this.devices;
    }

    public void setDevices(List<DeviceInfo> devices) {
        this.devices = devices;
    }

    public static class DeviceInfo {
        private long deviceid;
        private String devicename;
        private String mac;

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

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String toString() {
            return "deviceInfo{deviceid='" + this.deviceid + "', devicename='" + this.devicename + "', mac='" + this.mac + "'}";
        }
    }
}