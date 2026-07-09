package com.ltech.smarthome.ui.login;

import android.text.Editable;
import android.text.Selection;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.animation.Animation;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.animation.FlipAnimation;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.FtFindMailPwdBinding;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class FtFindMailPwd extends VMFragment<FtFindMailPwdBinding, FtFindMailPwdVM> {
    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_find_mail_pwd;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        TitleDefault titleDefault = new TitleDefault();
        titleDefault.setBackImageRes(R.mipmap.icon_back);
        titleDefault.setTitle(getString(R.string.find_back_password));
        titleDefault.setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.login.FtFindMailPwd$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtFindMailPwd.this.back();
            }
        }));
        ((FtFindMailPwdBinding) this.mViewBinding).setTitle(titleDefault);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        getViewModel().checkedProtocol = new ProtocolDefault().getCheckedProtocol();
        ((FtFindMailPwdVM) this.mViewModel).showVerificationCodeSendEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtFindMailPwd$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtFindMailPwd.this.lambda$startObserve$1((String) obj);
            }
        });
        ((FtFindMailPwdVM) this.mViewModel).pSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtFindMailPwd$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtFindMailPwd.this.lambda$startObserve$2((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(String str) {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.tips), str, getString(R.string.ok));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool.booleanValue()) {
            ((FtFindMailPwdBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see);
            ((FtFindMailPwdBinding) this.mViewBinding).etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((FtFindMailPwdBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((FtFindMailPwdBinding) this.mViewBinding).etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((FtFindMailPwdBinding) this.mViewBinding).etPwd.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    @Override // androidx.fragment.app.Fragment
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(3, enter, 250L);
    }
}