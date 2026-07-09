package com.ltech.smarthome.model.device_param;

import com.ltech.smarthome.ui.device.ir.Device433Repository;

/* loaded from: classes4.dex */
public class MotorParam extends BaseIrParam {
    private Device433Repository.MotorCodeLib irDatas;

    public Device433Repository.MotorCodeLib getIrDatas() {
        return this.irDatas;
    }

    public void setIrDatas(Device433Repository.MotorCodeLib irDatas) {
        this.irDatas = irDatas;
    }
}