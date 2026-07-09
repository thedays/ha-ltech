package com.ltech.smarthome.model.device_param;

import com.kookong.app.data.IrData;
import java.util.List;

/* loaded from: classes4.dex */
public class IrParam extends BaseIrParam {
    private int brandId;
    private List<IrData> irDatas;

    public int getBrandId() {
        return this.brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public List<IrData> getIrDatas() {
        return this.irDatas;
    }

    public void setIrDatas(List<IrData> irDatas) {
        this.irDatas = irDatas;
    }
}