package com.ltech.smarthome.net.request.device;

import java.util.List;

/* loaded from: classes4.dex */
public class DeviceControlRequest {
    private List<DeviceId> deviceids;
    private List<PlatformDeviceId> platformdeviceids;

    public DeviceControlRequest setPlatformdeviceids(List<PlatformDeviceId> platformdeviceids) {
        this.platformdeviceids = platformdeviceids;
        return this;
    }

    public DeviceControlRequest setDeviceIds(List<DeviceId> deviceIds) {
        this.deviceids = deviceIds;
        return this;
    }

    public static class PlatformDeviceId {
        private String platformdeviceid;

        public PlatformDeviceId(String platformdeviceid) {
            this.platformdeviceid = platformdeviceid;
        }
    }

    public static class DeviceId {
        private long deviceid;

        public DeviceId(long deviceId) {
            this.deviceid = deviceId;
        }
    }
}