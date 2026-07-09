package com.ltech.smarthome.net.request.device;

import java.util.List;

/* loaded from: classes4.dex */
public class SortDeviceAndGroupRequest {
    private List<DeviceSortBean> devices;
    private List<GroupSortBean> groups;

    public SortDeviceAndGroupRequest(List<DeviceSortBean> devices, List<GroupSortBean> groups) {
        this.devices = devices;
        this.groups = groups;
    }

    public static final class DeviceSortBean {
        public long deviceid;
        public int deviceindex;
        public int roomindex;

        public DeviceSortBean(long deviceid, int deviceindex) {
            this.deviceid = deviceid;
            this.deviceindex = deviceindex;
        }
    }

    public static class GroupSortBean {
        public long groupid;
        public int groupindex;
        public int roomindex;

        public GroupSortBean(long groupid, int groupindex) {
            this.groupid = groupid;
            this.groupindex = groupindex;
        }
    }
}