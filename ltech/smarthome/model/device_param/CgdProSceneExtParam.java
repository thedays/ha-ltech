package com.ltech.smarthome.model.device_param;

/* loaded from: classes4.dex */
public class CgdProSceneExtParam {
    private int daliAddr;
    private int daliHidden = 0;

    public int getDaliAddr() {
        return this.daliAddr;
    }

    public void setDaliAddr(int daliAddr) {
        this.daliAddr = daliAddr;
    }

    public boolean isDaliHidden() {
        return this.daliHidden == 1;
    }

    public int getDaliHidden() {
        return this.daliHidden;
    }

    public void setDaliHidden(int daliHidden) {
        this.daliHidden = daliHidden;
    }
}