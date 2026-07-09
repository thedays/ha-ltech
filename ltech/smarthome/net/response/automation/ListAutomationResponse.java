package com.ltech.smarthome.net.response.automation;

import java.util.List;

/* loaded from: classes4.dex */
public class ListAutomationResponse {
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
        private long automationid;
        private String automationname;
        private int automationtype;
        private int conditiontype;
        private String createtime;
        private int cycleindex;
        private int enable;
        private String endtime;
        private long gatewaydeviceid;
        private int intervaltime;
        private int picindex;
        private long placeid;
        private int sort;
        private String starttime;
        private String timezone;
        private long userid;
        private String weeks;

        public int getCycleindex() {
            return this.cycleindex;
        }

        public void setCycleindex(int cycleindex) {
            this.cycleindex = cycleindex;
        }

        public int getIntervaltime() {
            return this.intervaltime;
        }

        public void setIntervaltime(int intervaltime) {
            this.intervaltime = intervaltime;
        }

        public long getAutomationid() {
            return this.automationid;
        }

        public void setAutomationid(long automationid) {
            this.automationid = automationid;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getWeeks() {
            return this.weeks;
        }

        public void setWeeks(String weeks) {
            this.weeks = weeks;
        }

        public String getTimezone() {
            return this.timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public int getConditiontype() {
            return this.conditiontype;
        }

        public void setConditiontype(int conditiontype) {
            this.conditiontype = conditiontype;
        }

        public long getPlaceid() {
            return this.placeid;
        }

        public void setPlaceid(long placeid) {
            this.placeid = placeid;
        }

        public String getEndtime() {
            return this.endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getAutomationname() {
            return this.automationname;
        }

        public void setAutomationname(String automationname) {
            this.automationname = automationname;
        }

        public int getSort() {
            return this.sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getStarttime() {
            return this.starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public long getUserid() {
            return this.userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public int getPicindex() {
            return this.picindex;
        }

        public void setPicindex(int picindex) {
            this.picindex = picindex;
        }

        public int getEnable() {
            return this.enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getAutomationtype() {
            return this.automationtype;
        }

        public void setAutomationtype(int automationtype) {
            this.automationtype = automationtype;
        }

        public long getGatewaydeviceid() {
            return this.gatewaydeviceid;
        }

        public void setGatewaydeviceid(long gatewaydeviceid) {
            this.gatewaydeviceid = gatewaydeviceid;
        }
    }
}