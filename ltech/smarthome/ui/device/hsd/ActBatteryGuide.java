package com.ltech.smarthome.ui.device.hsd;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActBatteryGuideBinding;

/* loaded from: classes4.dex */
public class ActBatteryGuide extends BaseNormalActivity<ActBatteryGuideBinding> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_battery_guide;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.battery_guide));
        setBackImage(R.mipmap.icon_back);
    }
}