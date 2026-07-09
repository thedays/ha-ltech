package com.ltech.smarthome.net.response.intercom;

import java.util.List;

/* loaded from: classes4.dex */
public class CommunityInfoResponse {
    private List<CommunityInfo> data;
    private String message;
    private int ret;

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

    public List<CommunityInfo> getData() {
        return this.data;
    }

    public void setData(List<CommunityInfo> data) {
        this.data = data;
    }

    public static class CommunityInfo {
        private long ID;
        private String Location;

        public CommunityInfo(long ID, String location) {
            this.ID = ID;
            this.Location = location;
        }

        public long getID() {
            return this.ID;
        }

        public void setID(long ID) {
            this.ID = ID;
        }

        public String getLocation() {
            return this.Location;
        }

        public void setLocation(String Location) {
            this.Location = Location;
        }
    }
}