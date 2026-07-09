package com.ltech.smarthome.net.request.device;

import java.util.List;

/* loaded from: classes4.dex */
public class SortDeviceRequest {
    private List<DeviceSortBean> devices;

    public static final class DeviceSortBean {
        public long deviceid;
        public int index;
    }

    public SortDeviceRequest(List<DeviceSortBean> devices) {
        this.devices = devices;
    }
}