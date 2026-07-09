package com.ltech.smarthome.net.response;

import java.io.Serializable;

/* loaded from: classes4.dex */
public class VersionInfo implements Serializable {
    public String appversioncode;
    public int appversionnum;
    public String content;
    public String fileurl;

    public String getAppversioncode() {
        return this.appversioncode;
    }

    public void setAppversioncode(String appversioncode) {
        this.appversioncode = appversioncode;
    }

    public int getAppversionnum() {
        return this.appversionnum;
    }

    public void setAppversionnum(int appversionnum) {
        this.appversionnum = appversionnum;
    }

    public String getFileurl() {
        return this.fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}