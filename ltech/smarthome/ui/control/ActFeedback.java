package com.ltech.smarthome.ui.control;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActFeedBackBinding;

/* loaded from: classes4.dex */
public class ActFeedback extends VMActivity<ActFeedBackBinding, ActFeedBackVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_feed_back;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.feedback));
    }
}