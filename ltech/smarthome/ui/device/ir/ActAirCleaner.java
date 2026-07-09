package com.ltech.smarthome.ui.device.ir;

import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class ActAirCleaner extends BaseActFan<ActAirCleanerVM> {
    @Override // com.ltech.smarthome.ui.device.ir.BaseActFan
    protected int devicePicRes() {
        return R.mipmap.pic_air_cleaner;
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected int deviceType() {
        return 11;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMActivity
    public ActAirCleanerVM getViewModel() {
        return (ActAirCleanerVM) new ViewModelProvider(this).get(ActAirCleanerVM.class);
    }
}