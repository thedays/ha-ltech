package com.ltech.smarthome.ui.splash;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSplashBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.login.ActLogin;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActSplash extends BaseNormalActivity<ActSplashBinding> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_splash;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (Injection.repo().user().isLogin()) {
            NavUtils.destination(ActHome.class).navigation(this);
        } else {
            NavUtils.destination(ActLogin.class).navigation(this);
        }
    }
}