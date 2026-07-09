package com.ltech.smarthome.model.device_param;

/* loaded from: classes4.dex */
public class CameraParam {
    private int cameraNum;
    private int configType;
    private String verifyCode;

    public CameraParam(String verifyCode, int configType, int cameraNum) {
        this.verifyCode = verifyCode;
        this.configType = configType;
        this.cameraNum = cameraNum;
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public int getConfigType() {
        return this.configType;
    }

    public void setConfigType(int configType) {
        this.configType = configType;
    }

    public int getCameraNum() {
        int i = this.cameraNum;
        if (i == 0) {
            return 1;
        }
        return i;
    }

    public void setCameraNum(int cameraNum) {
        this.cameraNum = cameraNum;
    }
}