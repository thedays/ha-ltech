package com.ltech.smarthome.ui.device.ir;

import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class ActFan extends BaseActFan<ActFanVM> {
    @Override // com.ltech.smarthome.ui.device.ir.BaseActFan
    protected int devicePicRes() {
        return R.mipmap.pic_fan;
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected int deviceType() {
        return 8;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMActivity
    public ActFanVM getViewModel() {
        return (ActFanVM) new ViewModelProvider(this).get(ActFanVM.class);
    }
}