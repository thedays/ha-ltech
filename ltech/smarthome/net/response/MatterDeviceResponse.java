package com.ltech.smarthome.net.response;

import java.util.List;

/* loaded from: classes4.dex */
public class MatterDeviceResponse {
    private List<MatterDevice> devices;
    private List<MatterDevice> groups;
    private List<MatterDevice> scenes;

    public List<MatterDevice> getDevices() {
        return this.devices;
    }

    public List<MatterDevice> getScenes() {
        return this.scenes;
    }

    public List<MatterDevice> getGroups() {
        return this.groups;
    }

    public static class MatterDevice {
        private int icon;
        private String name;
        private int num;
        private long objectid;
        private int objecttype;
        private int platform;
        private int sorting;
        private String sub;

        public long getObjectid() {
            return this.objectid;
        }

        public void setObjectid(long objectid) {
            this.objectid = objectid;
        }

        public int getObjecttype() {
            return this.objecttype;
        }

        public void setObjecttype(int objecttype) {
            this.objecttype = objecttype;
        }

        public int getSorting() {
            return this.sorting;
        }

        public void setSorting(int sorting) {
            this.sorting = sorting;
        }

        public int getPlatform() {
            return this.platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSub() {
            return this.sub;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }

        public int getIcon() {
            return this.icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return this.num;
        }
    }
}