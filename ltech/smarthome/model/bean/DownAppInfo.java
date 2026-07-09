package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class DownAppInfo {
    public long apkSize;
    public long downSize;
    public String url;
    public int versionCode;

    public long getApkSize() {
        return this.apkSize;
    }

    public void setApkSize(long apkSize) {
        this.apkSize = apkSize;
    }

    public long getDownSize() {
        return this.downSize;
    }

    public void setDownSize(long downSize) {
        this.downSize = downSize;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return "DownAppInfo{downSize=" + this.downSize + ", versionCode=" + this.versionCode + ", url='" + this.url + "', apkSize=" + this.apkSize + '}';
    }
}