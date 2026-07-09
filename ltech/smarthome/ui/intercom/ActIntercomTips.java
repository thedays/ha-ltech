package com.ltech.smarthome.ui.intercom;

import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntercomTipsBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActIntercomTips extends VMActivity<ActIntercomTipsBinding, ActIntercomLoginVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intercom_tips;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.intercom));
        setBackImage(R.mipmap.icon_back);
        ((ActIntercomLoginVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActIntercomLoginVM) this.mViewModel).clickToLoginEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTips$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomTips.this.lambda$startObserve$0((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r4) {
        finishActivity();
        NavUtils.destination(ActIntercomLogin.class).withLong(Constants.PLACE_ID, ((ActIntercomLoginVM) this.mViewModel).placeId).navigation(this);
    }
}