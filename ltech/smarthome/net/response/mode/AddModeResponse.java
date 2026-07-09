package com.ltech.smarthome.net.response.mode;

/* loaded from: classes4.dex */
public class AddModeResponse {
    private Object begincreatetime;
    private String content;
    private String createtime;
    private int devicetype;
    private Object endcreatetime;
    private int modeindex;
    private String modename;
    private int modetype;
    private int pagenum;
    private int pagesize;
    private String param;
    private int picindex;
    private long placeid;
    private int playtimes;

    public String getModename() {
        return this.modename;
    }

    public void setModename(String modename) {
        this.modename = modename;
    }

    public long getFloorid() {
        return this.placeid;
    }

    public void setFloorid(long placeid) {
        this.placeid = placeid;
    }

    public long getPlaceid() {
        return this.placeid;
    }

    public void setPlaceid(long placeid) {
        this.placeid = placeid;
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

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Object getBegincreatetime() {
        return this.begincreatetime;
    }

    public void setBegincreatetime(Object begincreatetime) {
        this.begincreatetime = begincreatetime;
    }

    public Object getEndcreatetime() {
        return this.endcreatetime;
    }

    public void setEndcreatetime(Object endcreatetime) {
        this.endcreatetime = endcreatetime;
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