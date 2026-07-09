package com.ltech.smarthome.model.device_param;

import com.ltech.smarthome.base.BaseExtParam;

/* loaded from: classes4.dex */
public class CgdProLightExtParam extends BaseExtParam {
    private int daliAddr;
    private int daliGroupBit;
    private int daliType = 1;
    private int daliHidden = 0;

    public int getDaliGroupBit() {
        return this.daliGroupBit;
    }

    public void setDaliGroupBit(int daliGroupBit) {
        this.daliGroupBit = daliGroupBit;
    }

    public int getDaliAddr() {
        return this.daliAddr;
    }

    public void setDaliAddr(int daliAddr) {
        this.daliAddr = daliAddr;
    }

    public int getDaliType() {
        return this.daliType;
    }

    public void setDaliType(int daliType) {
        this.daliType = daliType;
    }

    public int getDaliHidden() {
        return this.daliHidden;
    }

    public void setDaliHidden(int daliHidden) {
        this.daliHidden = daliHidden;
    }

    public String getTypeStr() {
        int i = this.daliType;
        if (i == 1) {
            return "DIM";
        }
        if (i == 2) {
            return "CT";
        }
        return "RGB";
    }
}