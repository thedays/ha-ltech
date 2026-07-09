package com.ltech.smarthome.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.animation.FlipAnimation;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.FtPhoneRegBinding;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.TextClickUtil;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class FtPhoneRegister extends VMFragment<FtPhoneRegBinding, FtPhoneRegVM> {
    private boolean animation;
    private int register_type = 0;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_phone_reg;
    }

    public static FtPhoneRegister newInstance(boolean animation) {
        FtPhoneRegister ftPhoneRegister = new FtPhoneRegister();
        Bundle bundle = new Bundle();
        bundle.putBoolean("animation", animation);
        ftPhoneRegister.setArguments(bundle);
        return ftPhoneRegister;
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
        ProtocolDefault protocolDefault = new ProtocolDefault();
        getViewModel().checkedProtocol = protocolDefault.getCheckedProtocol();
        TitleDefault titleDefault = new TitleDefault();
        titleDefault.setBackImageRes(R.mipmap.icon_back);
        titleDefault.setTitle(getString(R.string.register));
        titleDefault.setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegister$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtPhoneRegister.this.back();
            }
        }));
        ((FtPhoneRegBinding) this.mViewBinding).setTitle(titleDefault);
        initButtonStatus();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((FtPhoneRegVM) this.mViewModel).showVerificationCodeSendEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegister$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtPhoneRegister.this.lambda$startObserve$1((String) obj);
            }
        });
        ((FtPhoneRegVM) this.mViewModel).pSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegister$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtPhoneRegister.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        String queryValue = SharedPreferenceUtil.queryValue(ActSelectCountry.SELECT_COUNTRY_KEY);
        String queryValue2 = SharedPreferenceUtil.queryValue(ActSelectCountry.SELECT_COUNTRY_EN_KEY);
        if (!TextUtils.isEmpty(queryValue)) {
            ((FtPhoneRegVM) this.mViewModel).country.set(queryValue);
            ((FtPhoneRegVM) this.mViewModel).countryCode.set(queryValue2);
        }
        if (TextUtils.isEmpty(((FtPhoneRegVM) this.mViewModel).countryCode.get()) || ((FtPhoneRegVM) this.mViewModel).countryCode.get().equals("China")) {
            ((FtPhoneRegBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.phone_mail));
        } else {
            ((FtPhoneRegBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.mail));
        }
        ((FtPhoneRegVM) this.mViewModel).selectAreaEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegister$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtPhoneRegister.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((FtPhoneRegVM) this.mViewModel).loadCountry();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(String str) {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.tips), str, getString(R.string.ok));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool.booleanValue()) {
            ((FtPhoneRegBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see);
            ((FtPhoneRegBinding) this.mViewBinding).etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((FtPhoneRegBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((FtPhoneRegBinding) this.mViewBinding).etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((FtPhoneRegBinding) this.mViewBinding).etPwd.getText();
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
        ((FtPhoneRegVM) this.mViewModel).countryStr.setValue(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_KEY));
        ((FtPhoneRegVM) this.mViewModel).country.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_KEY));
        ((FtPhoneRegVM) this.mViewModel).countryCode.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_EN_KEY));
        if (((FtPhoneRegVM) this.mViewModel).countryCode.get().equals("China")) {
            ((FtPhoneRegBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.phone_mail));
        } else {
            ((FtPhoneRegBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.mail));
        }
    }

    @Override // androidx.fragment.app.Fragment
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(3, enter, (this.animation || !enter) ? 250L : 0L);
    }

    private void initButtonStatus() {
        TextClickUtil.setTextClick(ActivityUtils.getTopActivity(), (TextView) ((FtPhoneRegBinding) this.mViewBinding).getRoot().findViewById(R.id.tv_protocol_text), getClickableSpans(), R.string.reg_protocol, R.string.reg_protocol_part1, R.string.reg_protocol_part2);
        ((FtPhoneRegBinding) this.mViewBinding).protocol.checkBox.setChecked(true);
        ((FtPhoneRegBinding) this.mViewBinding).protocol.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegister.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((FtPhoneRegBinding) FtPhoneRegister.this.mViewBinding).btRegister.setEnabled(isChecked);
                if (isChecked) {
                    ((FtPhoneRegBinding) FtPhoneRegister.this.mViewBinding).btRegister.setBackgroundResource(R.drawable.shape_red_bt);
                } else {
                    ((FtPhoneRegBinding) FtPhoneRegister.this.mViewBinding).btRegister.setBackgroundResource(R.drawable.shape_gray_bt_30);
                }
            }
        });
    }

    private ClickableSpan[] getClickableSpans() {
        return new ClickableSpan[]{new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegister.2
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(FtPhoneRegister.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, FtPhoneRegister.this.getString(R.string.user_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        }, new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegister.3
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(FtPhoneRegister.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, FtPhoneRegister.this.getString(R.string.privacy_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        }};
    }
}