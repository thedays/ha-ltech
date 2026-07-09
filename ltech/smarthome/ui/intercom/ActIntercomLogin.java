package com.ltech.smarthome.ui.intercom;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntercomLoginBinding;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActIntercomLogin extends VMActivity<ActIntercomLoginBinding, ActIntercomLoginVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intercom_login;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActIntercomLoginVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActIntercomLoginBinding) this.mViewBinding).etPassword.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLogin.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (ActIntercomLogin.this.mViewBinding == null) {
                    return;
                }
                if (TextUtils.isEmpty(((ActIntercomLoginBinding) ActIntercomLogin.this.mViewBinding).etAccount.getText().toString()) || TextUtils.isEmpty(editable.toString())) {
                    ((ActIntercomLoginBinding) ActIntercomLogin.this.mViewBinding).tvLogin.setAlpha(0.5f);
                } else {
                    ((ActIntercomLoginBinding) ActIntercomLogin.this.mViewBinding).tvLogin.setAlpha(1.0f);
                }
            }
        });
        ((ActIntercomLoginBinding) this.mViewBinding).etAccount.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLogin.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (ActIntercomLogin.this.mViewBinding == null) {
                    return;
                }
                if (TextUtils.isEmpty(((ActIntercomLoginBinding) ActIntercomLogin.this.mViewBinding).etPassword.getText().toString()) || TextUtils.isEmpty(editable.toString())) {
                    ((ActIntercomLoginBinding) ActIntercomLogin.this.mViewBinding).tvLogin.setAlpha(0.5f);
                } else {
                    ((ActIntercomLoginBinding) ActIntercomLogin.this.mViewBinding).tvLogin.setAlpha(1.0f);
                }
            }
        });
        ((ActIntercomLoginBinding) this.mViewBinding).tvLogin.setAlpha(0.5f);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActIntercomLoginVM) this.mViewModel).clickToLoginEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLogin$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomLogin.this.lambda$startObserve$0((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r3) {
        ((ActIntercomLoginVM) this.mViewModel).login(((ActIntercomLoginBinding) this.mViewBinding).etAccount.getText().toString(), ((ActIntercomLoginBinding) this.mViewBinding).etPassword.getText().toString());
    }
}