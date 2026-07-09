package com.ltech.smarthome.ui.login;

import android.text.TextUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActChangePhoneBinding;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActChangePhone extends VMActivity<ActChangePhoneBinding, ActChangePhoneVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_change_phone;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        if (TextUtils.isEmpty(getIntent().getStringExtra(Constants.USER_ACCOUNT))) {
            setTitle(getString(R.string.bind_phone));
            ((ActChangePhoneBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.phone_number));
        } else {
            setTitle(getString(R.string.change_phone));
            ((ActChangePhoneBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.new_phone));
        }
        ((ActChangePhoneVM) this.mViewModel).loadCountry();
    }
}