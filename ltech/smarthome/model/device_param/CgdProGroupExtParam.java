package com.ltech.smarthome.model.device_param;

import com.ltech.smarthome.base.BaseExtParam;

/* loaded from: classes4.dex */
public class CgdProGroupExtParam extends BaseExtParam {
    private int daliAddr;
    private int daliHidden = 0;
    private int multiTypeBit;

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

    public int getMultiTypeBit() {
        return this.multiTypeBit;
    }

    public void setMultiTypeBit(int multiTypeBit) {
        this.multiTypeBit = multiTypeBit;
    }
}