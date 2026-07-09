package com.ltech.smarthome.ui.user;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActAccountAndSecurityBinding;

/* loaded from: classes4.dex */
public class AccountAndSecurity extends VMActivity<ActAccountAndSecurityBinding, AccountAndSecurityVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_account_and_security;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.account_and_security));
    }
}