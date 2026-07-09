package com.ltech.smarthome.net.request;

/* loaded from: classes4.dex */
public class CheckVersionRequest {
    public String apppackage;
    public String apptypecode;
    public String appversionnum;

    public CheckVersionRequest(String apptypecode, String appversionnum, String apppackage) {
        this.apptypecode = apptypecode;
        this.appversionnum = appversionnum;
        this.apppackage = apppackage;
    }

    public String getAppversionnum() {
        return this.appversionnum;
    }

    public void setAppversionnum(String appversionnum) {
        this.appversionnum = appversionnum;
    }

    public String getApppackage() {
        return this.apppackage;
    }

    public void setApppackage(String apppackage) {
        this.apppackage = apppackage;
    }

    public String getApptypecode() {
        return this.apptypecode;
    }

    public void setApptypecode(String apptypecode) {
        this.apptypecode = apptypecode;
    }
}