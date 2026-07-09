package com.ltech.smarthome.net.response.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class ListNeedUpgradeResponse {
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
        private String createtime;
        private long deviceid;
        private String lastversion;
        private String mcuversion;
        private int needUpgrade;
        private int pagenum;
        private int pagesize;
        private long panelid;
        private String panelmac;
        private long upgradeid;
        private long userid;

        public String getLastversion() {
            return this.lastversion;
        }

        public void setLastversion(String lastversion) {
            this.lastversion = lastversion;
        }

        public String getMcuversion() {
            return this.mcuversion;
        }

        public void setMcuversion(String mcuversion) {
            this.mcuversion = mcuversion;
        }

        public long getUpgradeid() {
            return this.upgradeid;
        }

        public void setUpgradeid(long upgradeid) {
            this.upgradeid = upgradeid;
        }

        public long getUserid() {
            return this.userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public long getPanelid() {
            return this.panelid;
        }

        public void setPanelid(long panelid) {
            this.panelid = panelid;
        }

        public String getPanelmac() {
            return this.panelmac;
        }

        public void setPanelmac(String panelmac) {
            this.panelmac = panelmac;
        }

        public long getDeviceid() {
            return this.deviceid;
        }

        public void setDeviceid(long deviceid) {
            this.deviceid = deviceid;
        }

        public int getNeedUpgrade() {
            return this.needUpgrade;
        }

        public void setNeedUpgrade(int needUpgrade) {
            this.needUpgrade = needUpgrade;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
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
}