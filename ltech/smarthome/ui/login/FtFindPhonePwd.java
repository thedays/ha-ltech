package com.ltech.smarthome.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
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
import com.ltech.smarthome.databinding.FtFindPhonePwdBinding;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class FtFindPhonePwd extends VMFragment<FtFindPhonePwdBinding, FtFindPhonePwdVM> {
    private boolean animation;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_find_phone_pwd;
    }

    public static FtFindPhonePwd newInstance(boolean animation) {
        FtFindPhonePwd ftFindPhonePwd = new FtFindPhonePwd();
        Bundle bundle = new Bundle();
        bundle.putBoolean("animation", animation);
        ftFindPhonePwd.setArguments(bundle);
        return ftFindPhonePwd;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        if (getArguments() != null) {
            this.animation = getArguments().getBoolean("animation");
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        TitleDefault titleDefault = new TitleDefault();
        titleDefault.setBackImageRes(R.mipmap.icon_back);
        titleDefault.setTitle(getString(R.string.find_back_password));
        titleDefault.setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.login.FtFindPhonePwd$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtFindPhonePwd.this.back();
            }
        }));
        ((FtFindPhonePwdBinding) this.mViewBinding).setTitle(titleDefault);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        getViewModel().checkedProtocol = new ProtocolDefault().getCheckedProtocol();
        ((FtFindPhonePwdVM) this.mViewModel).showVerificationCodeSendEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtFindPhonePwd$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtFindPhonePwd.this.lambda$startObserve$1((String) obj);
            }
        });
        ((FtFindPhonePwdVM) this.mViewModel).pSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtFindPhonePwd$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtFindPhonePwd.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((FtFindPhonePwdVM) this.mViewModel).country.set(SharedPreferenceUtil.queryValue(ActSelectCountry.SELECT_COUNTRY_KEY));
        ((FtFindPhonePwdVM) this.mViewModel).countryCode.set(SharedPreferenceUtil.queryValue(ActSelectCountry.SELECT_COUNTRY_EN_KEY));
        if (TextUtils.isEmpty(((FtFindPhonePwdVM) this.mViewModel).countryCode.get()) || ((FtFindPhonePwdVM) this.mViewModel).countryCode.get().equals("China")) {
            ((FtFindPhonePwdBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.phone_mail));
        } else {
            ((FtFindPhonePwdBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.mail));
        }
        ((FtFindPhonePwdVM) this.mViewModel).selectAreaEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtFindPhonePwd$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtFindPhonePwd.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((FtFindPhonePwdVM) this.mViewModel).loadCountry();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(String str) {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.tips), str, getString(R.string.ok));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool.booleanValue()) {
            ((FtFindPhonePwdBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see);
            ((FtFindPhonePwdBinding) this.mViewBinding).etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((FtFindPhonePwdBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((FtFindPhonePwdBinding) this.mViewBinding).etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((FtFindPhonePwdBinding) this.mViewBinding).etPwd.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        NavUtils.destination(ActSelectCountry.class).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 100 || data == null) {
            return;
        }
        ((FtFindPhonePwdVM) this.mViewModel).countryStr.setValue(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_KEY));
        ((FtFindPhonePwdVM) this.mViewModel).country.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_KEY));
        ((FtFindPhonePwdVM) this.mViewModel).countryCode.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_EN_KEY));
        if (((FtFindPhonePwdVM) this.mViewModel).countryCode.get().equals("China")) {
            ((FtFindPhonePwdBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.phone_mail));
        } else {
            ((FtFindPhonePwdBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.mail));
        }
    }

    @Override // androidx.fragment.app.Fragment
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(3, enter, (this.animation || !enter) ? 250L : 0L);
    }
}