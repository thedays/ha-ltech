package com.ltech.smarthome.ui.device.ir;

import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActTvBinding;

/* loaded from: classes4.dex */
public class ActTv extends BaseActTv<ActTvVM> {
    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected int deviceType() {
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMActivity
    public ActTvVM getViewModel() {
        return (ActTvVM) new ViewModelProvider(this).get(ActTvVM.class);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseActTv, com.ltech.smarthome.ui.device.ir.BaseIrVMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActTvBinding) this.mViewBinding).ivHome.setImageResource(R.mipmap.icon_ir_home);
    }
}