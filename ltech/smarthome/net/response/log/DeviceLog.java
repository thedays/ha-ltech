package com.ltech.smarthome.net.response.log;

/* loaded from: classes4.dex */
public class DeviceLog {
    private String cnmessage;
    private String createtime;
    private long deviceid;
    private String enmessage;
    private long logid;
    private String propertycode;
    private String propertyvalue;
    private int status;

    public long getLogid() {
        return this.logid;
    }

    public void setLogid(long logid) {
        this.logid = logid;
    }

    public long getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(long deviceid) {
        this.deviceid = deviceid;
    }

    public String getCnmessage() {
        return this.cnmessage;
    }

    public void setCnmessage(String cnmessage) {
        this.cnmessage = cnmessage;
    }

    public String getEnmessage() {
        return this.enmessage;
    }

    public void setEnmessage(String enmessage) {
        this.enmessage = enmessage;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getShortCreatetime() {
        return this.createtime.split(" ")[1].substring(0, 8);
    }

    public String getPropertycode() {
        return this.propertycode;
    }

    public void setPropertycode(String propertycode) {
        this.propertycode = propertycode;
    }

    public String getPropertyvalue() {
        return this.propertyvalue;
    }

    public void setPropertyvalue(String propertyvalue) {
        this.propertyvalue = propertyvalue;
    }
}