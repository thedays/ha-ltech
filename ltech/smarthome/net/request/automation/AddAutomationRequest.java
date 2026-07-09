package com.ltech.smarthome.net.request.automation;

import com.ltech.smarthome.model.bean.Automation;
import java.util.List;

/* loaded from: classes4.dex */
public class AddAutomationRequest {
    private List<Automation.Action> actions;
    private String automationname;
    private int automationtype;
    private List<Automation.Condition> conditions;
    private int conditiontype;
    private int cycleindex;
    private int enable;
    private String endtime;
    private long gatewaydeviceid;
    private int intervaltime;
    private String params;
    private int picindex;
    private long placeid;
    private String starttime;
    private String timezone;
    private String weeks;

    public AddAutomationRequest(long placeid) {
        this.placeid = placeid;
    }

    public String getAutomationname() {
        return this.automationname;
    }

    public void setAutomationname(String automationname) {
        this.automationname = automationname;
    }

    public String getStarttime() {
        return this.starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return this.endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
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

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
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

    public List<Automation.Condition> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<Automation.Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Automation.Action> getActions() {
        return this.actions;
    }

    public void setActions(List<Automation.Action> actions) {
        this.actions = actions;
    }

    public int getConditiontype() {
        return this.conditiontype;
    }

    public void setConditiontype(int conditiontype) {
        this.conditiontype = conditiontype;
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

    public long getPlaceid() {
        return this.placeid;
    }

    public void setPlaceid(long placeid) {
        this.placeid = placeid;
    }
}