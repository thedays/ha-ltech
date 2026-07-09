package com.ltech.smarthome.net.request.role;

import java.util.List;

/* loaded from: classes4.dex */
public class UpdateDeviceRoleRequest {
    private List<DeviceId> deviceroles;
    private long placeid;
    private long userid;

    public UpdateDeviceRoleRequest(long placeid, long userid, List deviceroles) {
        this.placeid = placeid;
        this.userid = userid;
        this.deviceroles = deviceroles;
    }

    public static class DeviceId {
        private long deviceid;

        public DeviceId(long deviceid) {
            this.deviceid = deviceid;
        }

        public long getDeviceid() {
            return this.deviceid;
        }
    }
}