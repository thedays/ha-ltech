package com.ltech.smarthome.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActChangePwdBinding;
import com.ltech.smarthome.model.Injection;

/* loaded from: classes4.dex */
public class ActChangePwd extends VMActivity<ActChangePwdBinding, ActChangePwdVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_change_pwd;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.change_pwd));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActChangePwdVM) this.mViewModel).pOldPwdSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActChangePwd$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActChangePwd.this.lambda$startObserve$0((Boolean) obj);
            }
        });
        ((ActChangePwdVM) this.mViewModel).pNewPwdSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActChangePwd$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActChangePwd.this.lambda$startObserve$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActChangePwdBinding) this.mViewBinding).ivOldPassword.setBackgroundResource(R.mipmap.sign_icon_see);
            ((ActChangePwdBinding) this.mViewBinding).etOldPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((ActChangePwdBinding) this.mViewBinding).ivOldPassword.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((ActChangePwdBinding) this.mViewBinding).etOldPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((ActChangePwdBinding) this.mViewBinding).etOldPwd.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActChangePwdBinding) this.mViewBinding).ivNewPassword.setBackgroundResource(R.mipmap.sign_icon_see);
            ((ActChangePwdBinding) this.mViewBinding).etNewPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((ActChangePwdBinding) this.mViewBinding).ivNewPassword.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((ActChangePwdBinding) this.mViewBinding).etNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((ActChangePwdBinding) this.mViewBinding).etNewPwd.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (2003 == resultCode) {
            Injection.logout();
        }
    }
}