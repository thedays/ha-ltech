package com.ltech.smarthome.ui.login;

import android.text.TextUtils;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActChangeEmailBinding;
import com.ltech.smarthome.utils.Constants;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class ActChangeEmail extends VMActivity<ActChangeEmailBinding, ActChangeEmailVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_change_email;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        if (TextUtils.isEmpty(getIntent().getStringExtra(Constants.USER_ACCOUNT))) {
            setTitle(getString(R.string.bind_mail));
            ((ActChangeEmailBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.email));
        } else {
            setTitle(getString(R.string.change_email));
            ((ActChangeEmailBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.new_email));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActChangeEmailVM) this.mViewModel).showVerificationCodeSendEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActChangeEmail$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActChangeEmail.this.lambda$startObserve$0((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(String str) {
        MessageDialog.show(this, getString(R.string.tips), str, getString(R.string.ok));
    }
}