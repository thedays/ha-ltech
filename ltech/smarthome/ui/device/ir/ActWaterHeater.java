package com.ltech.smarthome.ui.device.ir;

import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class ActWaterHeater extends BaseActFan<ActWaterHeaterVM> {
    @Override // com.ltech.smarthome.ui.device.ir.BaseActFan
    protected int devicePicRes() {
        return R.mipmap.pic_water_heater;
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected int deviceType() {
        return 12;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMActivity
    public ActWaterHeaterVM getViewModel() {
        return (ActWaterHeaterVM) new ViewModelProvider(this).get(ActWaterHeaterVM.class);
    }
}