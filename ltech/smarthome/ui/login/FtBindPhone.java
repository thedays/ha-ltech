package com.ltech.smarthome.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextPaint;
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
import com.ltech.smarthome.databinding.FtBindPhoneBinding;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.TextClickUtil;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class FtBindPhone extends VMFragment<FtBindPhoneBinding, FtBindPhoneVM> {
    private boolean animation;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_bind_phone;
    }

    public static FtBindPhone newInstance(boolean animation, String openId, String source) {
        FtBindPhone ftBindPhone = new FtBindPhone();
        Bundle bundle = new Bundle();
        bundle.putBoolean("animation", animation);
        bundle.putString(Constants.OAUTHBIND_OPENID, openId);
        bundle.putString(Constants.OAUTHBIND_SOURCE, source);
        ftBindPhone.setArguments(bundle);
        return ftBindPhone;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.animation = getArguments().getBoolean("animation");
            ((FtBindPhoneVM) this.mViewModel).setOpenid(arguments.getString(Constants.OAUTHBIND_OPENID));
            ((FtBindPhoneVM) this.mViewModel).setSource(arguments.getString(Constants.OAUTHBIND_SOURCE));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        ProtocolDefault protocolDefault = new ProtocolDefault();
        getViewModel().checkedProtocol = protocolDefault.getCheckedProtocol();
        TitleDefault titleDefault = new TitleDefault();
        titleDefault.setBackImageRes(R.mipmap.icon_back);
        titleDefault.setTitle(getString(R.string.bind_phone));
        titleDefault.setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.login.FtBindPhone$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtBindPhone.this.back();
            }
        }));
        ((FtBindPhoneBinding) this.mViewBinding).setTitle(titleDefault);
        initButtonStatus();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((FtBindPhoneVM) this.mViewModel).showVerificationCodeSendEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtBindPhone$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtBindPhone.this.lambda$startObserve$1((String) obj);
            }
        });
        ((FtBindPhoneVM) this.mViewModel).pSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtBindPhone$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtBindPhone.this.lambda$startObserve$2((Boolean) obj);
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
            ((FtBindPhoneBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see);
            ((FtBindPhoneBinding) this.mViewBinding).etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((FtBindPhoneBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((FtBindPhoneBinding) this.mViewBinding).etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((FtBindPhoneBinding) this.mViewBinding).etPwd.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 100 || data == null) {
            return;
        }
        ((FtBindPhoneVM) this.mViewModel).country.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_KEY));
        ((FtBindPhoneVM) this.mViewModel).countryCode.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_EN_KEY));
        if (((FtBindPhoneVM) this.mViewModel).countryCode.get().equals("China")) {
            ((FtBindPhoneBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.phone_mail));
        } else {
            ((FtBindPhoneBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.mail));
        }
    }

    @Override // androidx.fragment.app.Fragment
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(3, enter, (this.animation || !enter) ? 250L : 0L);
    }

    private void initButtonStatus() {
        TextClickUtil.setTextClick(ActivityUtils.getTopActivity(), (TextView) ((FtBindPhoneBinding) this.mViewBinding).getRoot().findViewById(R.id.tv_protocol_text), getClickableSpans(), R.string.reg_protocol, R.string.reg_protocol_part1, R.string.reg_protocol_part2);
        ((FtBindPhoneBinding) this.mViewBinding).protocol.checkBox.setChecked(true);
        ((FtBindPhoneBinding) this.mViewBinding).protocol.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.login.FtBindPhone.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((FtBindPhoneBinding) FtBindPhone.this.mViewBinding).btRegister.setEnabled(isChecked);
                if (isChecked) {
                    ((FtBindPhoneBinding) FtBindPhone.this.mViewBinding).btRegister.setBackgroundResource(R.drawable.shape_red_bt);
                } else {
                    ((FtBindPhoneBinding) FtBindPhone.this.mViewBinding).btRegister.setBackgroundResource(R.drawable.shape_gray_bt_30);
                }
            }
        });
    }

    private ClickableSpan[] getClickableSpans() {
        return new ClickableSpan[]{new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.FtBindPhone.2
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(FtBindPhone.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, FtBindPhone.this.getString(R.string.user_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        }, new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.FtBindPhone.3
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(FtBindPhone.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, FtBindPhone.this.getString(R.string.privacy_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        }};
    }
}