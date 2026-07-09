package com.ltech.smarthome.ui.login;

import androidx.fragment.app.FragmentTransaction;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActRegisterBinding;
import com.ltech.smarthome.utils.LiveBusHelper;

/* loaded from: classes4.dex */
public class ActRegister extends BaseNormalActivity<ActRegisterBinding> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_register;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean useEventBus() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, FtPhoneRegister.newInstance(false));
        beginTransaction.commit();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper helper) {
        if (1 == helper.getCode()) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.fragment_container, FtPhoneRegister.newInstance(true));
            beginTransaction.commit();
        } else if (2 == helper.getCode()) {
            FragmentTransaction beginTransaction2 = getSupportFragmentManager().beginTransaction();
            beginTransaction2.replace(R.id.fragment_container, new FtMailRegister());
            beginTransaction2.commit();
        }
    }
}