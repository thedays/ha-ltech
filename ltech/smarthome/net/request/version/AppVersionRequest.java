package com.ltech.smarthome.net.request.version;

/* loaded from: classes4.dex */
public class AppVersionRequest {
    private String apppackage;
    private String apptypecode;
    private int appversionnum;

    public AppVersionRequest(String apptypecode, String apppackage, int appversionnum) {
        setApptypecode(apptypecode);
        setApppackage(apppackage);
        setAppversionnum(appversionnum);
    }

    public String getApptypecode() {
        return this.apptypecode;
    }

    public void setApptypecode(String apptypecode) {
        this.apptypecode = apptypecode;
    }

    public String getApppackage() {
        return this.apppackage;
    }

    public void setApppackage(String apppackage) {
        this.apppackage = apppackage;
    }

    public int getAppversionnum() {
        return this.appversionnum;
    }

    public void setAppversionnum(int appversionnum) {
        this.appversionnum = appversionnum;
    }
}