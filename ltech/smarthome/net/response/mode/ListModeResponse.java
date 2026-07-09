package com.ltech.smarthome.net.response.mode;

import java.util.List;

/* loaded from: classes4.dex */
public class ListModeResponse {
    private List<RowsBean> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return this.rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String content;
        private int devicetype;
        private long lightmodeid;
        private int modeindex;
        private String modename;
        private int modetype;
        private String param;
        private int picindex;
        private long placeid;
        private int playtimes;
        private long userid;

        public long getLightmodeid() {
            return this.lightmodeid;
        }

        public void setLightmodeid(long lightmodeid) {
            this.lightmodeid = lightmodeid;
        }

        public String getModename() {
            return this.modename;
        }

        public void setModename(String modename) {
            this.modename = modename;
        }

        public long getPlaceid() {
            return this.placeid;
        }

        public void setPlaceid(long placeid) {
            this.placeid = placeid;
        }

        public long getUserid() {
            return this.userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public int getDevicetype() {
            return this.devicetype;
        }

        public void setDevicetype(int devicetype) {
            this.devicetype = devicetype;
        }

        public int getModetype() {
            return this.modetype;
        }

        public void setModetype(int modetype) {
            this.modetype = modetype;
        }

        public int getModeindex() {
            return this.modeindex;
        }

        public void setModeindex(int modeindex) {
            this.modeindex = modeindex;
        }

        public int getPicindex() {
            return this.picindex;
        }

        public void setPicindex(int picindex) {
            this.picindex = picindex;
        }

        public int getPlaytimes() {
            return this.playtimes;
        }

        public void setPlaytimes(int playtimes) {
            this.playtimes = playtimes;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getParam() {
            return this.param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }
}