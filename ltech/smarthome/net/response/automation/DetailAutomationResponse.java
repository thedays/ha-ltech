package com.ltech.smarthome.net.response.automation;

import java.util.List;

/* loaded from: classes4.dex */
public class DetailAutomationResponse {
    private List<ActionsBean> actions;
    private AutomationinfoBean automationinfo;
    private List<ConditionsBean> conditions;

    public AutomationinfoBean getAutomationinfo() {
        return this.automationinfo;
    }

    public void setAutomationinfo(AutomationinfoBean automationinfo) {
        this.automationinfo = automationinfo;
    }

    public List<ConditionsBean> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<ConditionsBean> conditions) {
        this.conditions = conditions;
    }

    public List<ActionsBean> getActions() {
        return this.actions;
    }

    public void setActions(List<ActionsBean> actions) {
        this.actions = actions;
    }

    public static class AutomationinfoBean {
        private long automationid;
        private String automationname;
        private int conditiontype;
        private String createtime;
        private int enable;
        private String endtime;
        private int picindex;
        private long placeid;
        private int sort;
        private String starttime;
        private String timezone;
        private long userid;
        private String weeks;

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
    }

    public static class ConditionsBean {
        private long automationid;
        private String automationname;
        private long automationparamid;
        private int eventtype;
        private String params;
        private int paramtype;
        private long userid;

        public int getEventtype() {
            return this.eventtype;
        }

        public void setEventtype(int eventtype) {
            this.eventtype = eventtype;
        }

        public long getAutomationid() {
            return this.automationid;
        }

        public void setAutomationid(long automationid) {
            this.automationid = automationid;
        }

        public int getParamtype() {
            return this.paramtype;
        }

        public void setParamtype(int paramtype) {
            this.paramtype = paramtype;
        }

        public long getAutomationparamid() {
            return this.automationparamid;
        }

        public void setAutomationparamid(long automationparamid) {
            this.automationparamid = automationparamid;
        }

        public String getAutomationname() {
            return this.automationname;
        }

        public void setAutomationname(String automationname) {
            this.automationname = automationname;
        }

        public String getParams() {
            return this.params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public long getUserid() {
            return this.userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }
    }

    public static class ActionsBean {
        private int actiondelays;
        private int actiontype;
        private long automationactionid;
        private long automationid;
        private String automationname;
        private String params;

        public int getActiontype() {
            return this.actiontype;
        }

        public void setActiontype(int actiontype) {
            this.actiontype = actiontype;
        }

        public long getAutomationid() {
            return this.automationid;
        }

        public void setAutomationid(long automationid) {
            this.automationid = automationid;
        }

        public long getAutomationactionid() {
            return this.automationactionid;
        }

        public void setAutomationactionid(long automationactionid) {
            this.automationactionid = automationactionid;
        }

        public int getActiondelays() {
            return this.actiondelays;
        }

        public void setActiondelays(int actiondelays) {
            this.actiondelays = actiondelays;
        }

        public String getAutomationname() {
            return this.automationname;
        }

        public void setAutomationname(String automationname) {
            this.automationname = automationname;
        }

        public String getParams() {
            return this.params;
        }

        public void setParams(String params) {
            this.params = params;
        }
    }
}