package com.ltech.smarthome.ui.device.light;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActUserInfoBinding;

/* loaded from: classes4.dex */
public class ActChoiceLightType extends VMActivity<ActUserInfoBinding, ActChoiceLightTypeVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_choice_light_type;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_device_type));
    }
}