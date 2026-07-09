package com.ltech.smarthome.model.scene_param;

/* loaded from: classes4.dex */
public class AutomationParam {
    public long automationid;
    public int enable;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass() && this.automationid == ((AutomationParam) o).automationid;
    }
}