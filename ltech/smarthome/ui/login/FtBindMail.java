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
import com.ltech.smarthome.databinding.FtBindMailBinding;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.TextClickUtil;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class FtBindMail extends VMFragment<FtBindMailBinding, FtBindMailVM> {
    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_bind_mail;
    }

    public static FtBindMail newInstance(String openId, String source) {
        FtBindMail ftBindMail = new FtBindMail();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.OAUTHBIND_OPENID, openId);
        bundle.putString(Constants.OAUTHBIND_SOURCE, source);
        ftBindMail.setArguments(bundle);
        return ftBindMail;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            ((FtBindMailVM) this.mViewModel).setOpenid(arguments.getString(Constants.OAUTHBIND_OPENID));
            ((FtBindMailVM) this.mViewModel).setSource(arguments.getString(Constants.OAUTHBIND_SOURCE));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        ProtocolDefault protocolDefault = new ProtocolDefault();
        getViewModel().checkedProtocol = protocolDefault.getCheckedProtocol();
        TitleDefault titleDefault = new TitleDefault();
        titleDefault.setBackImageRes(R.mipmap.icon_back);
        titleDefault.setTitle(getString(R.string.bind_mail));
        titleDefault.setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.login.FtBindMail$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtBindMail.this.back();
            }
        }));
        ((FtBindMailBinding) this.mViewBinding).setTitle(titleDefault);
        initButtonStatus();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((FtBindMailVM) this.mViewModel).showVerificationCodeSendEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtBindMail$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtBindMail.this.lambda$startObserve$1((String) obj);
            }
        });
        ((FtBindMailVM) this.mViewModel).pSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtBindMail$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtBindMail.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((FtBindMailVM) this.mViewModel).selectAreaEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtBindMail$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtBindMail.this.lambda$startObserve$3((Void) obj);
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
            ((FtBindMailBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see);
            ((FtBindMailBinding) this.mViewBinding).etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((FtBindMailBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((FtBindMailBinding) this.mViewBinding).etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((FtBindMailBinding) this.mViewBinding).etPwd.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        NavUtils.destination(ActSelectCountry.class).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.Fragment
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(3, enter, 250L);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 100 || data == null) {
            return;
        }
        ((FtBindMailVM) this.mViewModel).country.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_KEY));
        ((FtBindMailVM) this.mViewModel).countryCode.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_EN_KEY));
        if (((FtBindMailVM) this.mViewModel).countryCode.get().equals("China")) {
            ((FtBindMailBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.phone_mail));
        } else {
            ((FtBindMailBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.mail));
        }
    }

    private void initButtonStatus() {
        TextClickUtil.setTextClick(ActivityUtils.getTopActivity(), (TextView) ((FtBindMailBinding) this.mViewBinding).getRoot().findViewById(R.id.tv_protocol_text), getClickableSpans(), R.string.reg_protocol, R.string.reg_protocol_part1, R.string.reg_protocol_part2);
        ((FtBindMailBinding) this.mViewBinding).protocol.checkBox.setChecked(true);
        ((FtBindMailBinding) this.mViewBinding).protocol.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.login.FtBindMail.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((FtBindMailBinding) FtBindMail.this.mViewBinding).btRegister.setEnabled(isChecked);
                if (isChecked) {
                    ((FtBindMailBinding) FtBindMail.this.mViewBinding).btRegister.setBackgroundResource(R.drawable.shape_red_bt);
                } else {
                    ((FtBindMailBinding) FtBindMail.this.mViewBinding).btRegister.setBackgroundResource(R.drawable.shape_gray_bt_30);
                }
            }
        });
    }

    private ClickableSpan[] getClickableSpans() {
        return new ClickableSpan[]{new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.FtBindMail.2
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(FtBindMail.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, FtBindMail.this.getString(R.string.user_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        }, new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.FtBindMail.3
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(FtBindMail.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, FtBindMail.this.getString(R.string.privacy_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        }};
    }
}