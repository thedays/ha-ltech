package com.ltech.smarthome.base;

import com.alibaba.fastjson.JSONObject;

/* loaded from: classes3.dex */
public class BaseExtParam {
    protected String binName;
    protected int binNum;
    protected int hideDevice;

    public int getHideDevice() {
        return this.hideDevice;
    }

    public void setHideDevice(int hideDevice) {
        this.hideDevice = hideDevice;
    }

    public String getBinName() {
        return this.binName;
    }

    public void setBinName(String binName) {
        this.binName = binName;
    }

    public int getBinNum() {
        return this.binNum;
    }

    public void setBinNum(int binNum) {
        this.binNum = binNum;
    }

    protected void putBaseFields(JSONObject object) {
        object.put("hideDevice", (Object) Integer.valueOf(this.hideDevice));
        object.put("binName", (Object) this.binName);
        int i = this.binNum;
        if (i != 0) {
            object.put("binNum", (Object) Integer.valueOf(i));
        }
    }

    protected void getBaseFields(JSONObject object, String key) {
        key.hashCode();
        switch (key) {
            case "binNum":
                this.binNum = object.getIntValue(key);
                break;
            case "binName":
                this.binName = object.getString(key);
                break;
            case "hideDevice":
                this.hideDevice = object.getIntValue(key);
                break;
        }
    }
}