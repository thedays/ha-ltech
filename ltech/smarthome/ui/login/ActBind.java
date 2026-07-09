package com.ltech.smarthome.ui.login;

import androidx.fragment.app.FragmentTransaction;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActRegisterBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LiveBusHelper;

/* loaded from: classes4.dex */
public class ActBind extends BaseNormalActivity<ActRegisterBinding> {
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
        beginTransaction.replace(R.id.fragment_container, FtBindPhone.newInstance(false, getIntent().getStringExtra(Constants.OAUTHBIND_OPENID), getIntent().getStringExtra(Constants.OAUTHBIND_SOURCE)));
        beginTransaction.commit();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper liveBusHelper) {
        if (1 == liveBusHelper.getCode()) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.fragment_container, FtBindPhone.newInstance(true, getIntent().getStringExtra(Constants.OAUTHBIND_OPENID), getIntent().getStringExtra(Constants.OAUTHBIND_SOURCE)));
            beginTransaction.commit();
        }
        if (2 == liveBusHelper.getCode()) {
            FragmentTransaction beginTransaction2 = getSupportFragmentManager().beginTransaction();
            beginTransaction2.replace(R.id.fragment_container, FtBindMail.newInstance(getIntent().getStringExtra(Constants.OAUTHBIND_OPENID), getIntent().getStringExtra(Constants.OAUTHBIND_SOURCE)));
            beginTransaction2.commit();
        }
    }
}