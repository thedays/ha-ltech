package com.ltech.smarthome.ui.device.smartpanel;

import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;

/* loaded from: classes4.dex */
public class RelateInfoItem {
    public String actionInfo;
    public int iconRes;
    public int index;
    public String infoName;
    public RelatedInfoExtParam.RelateInfo relateInfo;
    public int type;

    public RelateInfoItem(String infoName) {
        this.infoName = infoName;
    }

    public RelateInfoItem() {
    }
}