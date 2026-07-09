package com.ltech.smarthome.net.request;

import java.util.List;

/* loaded from: classes4.dex */
public class VoiceCallRequest {
    private String busymodeend;
    private String busymodestart;
    private String busymodeweeks;
    private List<Long> groupList;
    private int isautoanswer;
    private int isbusymode;
    private String mac;
    private String name;
    private long panelid;
    private long panelvoicegroupid;
    private long panelvoicesettingid;
    private long panelvoicewhitelistid;
    private long placeid;
    private int type;
    private List<Long> useridList;
    private List<Long> whiteList;

    public List<Long> getUseridList() {
        return this.useridList;
    }

    public void setUseridList(List<Long> useridList) {
        this.useridList = useridList;
    }

    public long getPanelid() {
        return this.panelid;
    }

    public void setPanelid(long panelid) {
        this.panelid = panelid;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<Long> getWhiteList() {
        return this.whiteList;
    }

    public void setWhiteList(List<Long> whiteList) {
        this.whiteList = whiteList;
    }

    public long getPlaceid() {
        return this.placeid;
    }

    public void setPlaceid(long placeid) {
        this.placeid = placeid;
    }

    public long getPanelvoicegroupid() {
        return this.panelvoicegroupid;
    }

    public void setPanelvoicegroupid(long panelvoicegroupid) {
        this.panelvoicegroupid = panelvoicegroupid;
    }

    public List<Long> getGroupList() {
        return this.groupList;
    }

    public void setGroupList(List<Long> groupList) {
        this.groupList = groupList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getPanelvoicesettingid() {
        return this.panelvoicesettingid;
    }

    public void setPanelvoicesettingid(long panelvoicesettingid) {
        this.panelvoicesettingid = panelvoicesettingid;
    }

    public int getIsautoanswer() {
        return this.isautoanswer;
    }

    public void setIsautoanswer(int isautoanswer) {
        this.isautoanswer = isautoanswer;
    }

    public int getIsbusymode() {
        return this.isbusymode;
    }

    public void setIsbusymode(int isbusymode) {
        this.isbusymode = isbusymode;
    }

    public String getBusymodestart() {
        return this.busymodestart;
    }

    public void setBusymodestart(String busymodestart) {
        this.busymodestart = busymodestart;
    }

    public String getBusymodeend() {
        return this.busymodeend;
    }

    public void setBusymodeend(String busymodeend) {
        this.busymodeend = busymodeend;
    }

    public String getBusymodeweeks() {
        return this.busymodeweeks;
    }

    public void setBusymodeweeks(String busymodeweeks) {
        this.busymodeweeks = busymodeweeks;
    }

    public long getPanelvoicewhitelistid() {
        return this.panelvoicewhitelistid;
    }

    public void setPanelvoicewhitelistid(long panelvoicewhitelistid) {
        this.panelvoicewhitelistid = panelvoicewhitelistid;
    }

    public String toString() {
        return "VoiceCallRequest{panelid=" + this.panelid + ", mac='" + this.mac + "', whiteList=" + this.whiteList + ", placeid=" + this.placeid + ", panelvoicegroupid=" + this.panelvoicegroupid + ", panelvoicewhitelistid=" + this.panelvoicewhitelistid + ", groupList=" + this.groupList + ", name='" + this.name + "', type=" + this.type + ", panelvoicesettingid=" + this.panelvoicesettingid + ", isautoanswer=" + this.isautoanswer + ", isbusymode=" + this.isbusymode + ", busymodestart='" + this.busymodestart + "', busymodeend='" + this.busymodeend + "', busymodeweeks='" + this.busymodeweeks + "'}";
    }
}