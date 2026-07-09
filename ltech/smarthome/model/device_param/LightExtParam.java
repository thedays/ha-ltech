package com.ltech.smarthome.model.device_param;

import com.ltech.smarthome.base.BaseExtParam;

/* loaded from: classes4.dex */
public class LightExtParam extends BaseExtParam {
    protected int[] currentValue;
    protected int pwmFrequency;

    public int[] getCurrentValue() {
        if (this.currentValue == null) {
            if (isW5B()) {
                this.currentValue = new int[5];
            } else {
                this.currentValue = new int[1];
            }
        }
        return this.currentValue;
    }

    public void setCurrentValue(int[] currentValue) {
        this.currentValue = currentValue;
    }

    public int getPwmFrequency() {
        return this.pwmFrequency;
    }

    public void setPwmFrequency(int pwmFrequency) {
        this.pwmFrequency = pwmFrequency;
    }

    public boolean isW5B() {
        return this.binNum == 48;
    }
}