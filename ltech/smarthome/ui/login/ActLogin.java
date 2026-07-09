package com.ltech.smarthome.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActLoginBinding;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.TextClickUtil;
import com.smart.message.utils.LHomeLog;
import com.videogo.openapi.model.req.GetSmsCodeResetReq;

/* loaded from: classes4.dex */
public class ActLogin extends VMActivity<ActLoginBinding, ActLoginVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isSupportExit() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_login;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        ProtocolDefault protocolDefault = new ProtocolDefault();
        ((ActLoginVM) this.mViewModel).checkedProtocol = protocolDefault.getCheckedProtocol();
        initButtonStatus();
        ((ActLoginBinding) this.mViewBinding).etAccount.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.ui.login.ActLogin.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (ActLogin.this.mViewBinding == null) {
                    return;
                }
                String queryValue = SharedPreferenceUtil.queryValue(Constants.USER_ACCOUNT);
                if (TextUtils.isEmpty(queryValue) || queryValue.equals(editable.toString())) {
                    return;
                }
                ((ActLoginVM) ActLogin.this.mViewModel).password.set("");
            }
        });
    }

    private void initButtonStatus() {
        TextClickUtil.setTextClick(this, (TextView) ((ActLoginBinding) this.mViewBinding).getRoot().findViewById(R.id.tv_protocol_text), getClickableSpans(), R.string.reg_protocol, R.string.reg_protocol_part1, R.string.reg_protocol_part2);
        ((ActLoginBinding) this.mViewBinding).protocol.checkBox.setChecked(false);
        ((ActLoginBinding) this.mViewBinding).btLogin.setEnabled(false);
        ((ActLoginBinding) this.mViewBinding).btLogin.setBackgroundResource(R.drawable.shape_gray_bt_30);
        ((ActLoginBinding) this.mViewBinding).protocol.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.login.ActLogin.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LHomeLog.i(ActLogin.class, "onCheckedChanged: ischeck :" + isChecked);
                ((ActLoginBinding) ActLogin.this.mViewBinding).btLogin.setEnabled(isChecked);
                if (isChecked) {
                    ((ActLoginBinding) ActLogin.this.mViewBinding).btLogin.setBackgroundResource(R.drawable.shape_red_bt);
                } else {
                    ((ActLoginBinding) ActLogin.this.mViewBinding).btLogin.setBackgroundResource(R.drawable.shape_gray_bt_30);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActLoginVM) this.mViewModel).pErrorEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActLogin$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HelpUtils.vibrate(500L);
            }
        });
        ((ActLoginVM) this.mViewModel).pSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActLogin$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLogin.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        ((ActLoginVM) this.mViewModel).showRegDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActLogin$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLogin.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActLoginVM) this.mViewModel).showFindPwdDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActLogin$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLogin.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActLoginVM) this.mViewModel).selectAreaEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.ActLogin$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLogin.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActLoginBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see);
            ((ActLoginBinding) this.mViewBinding).etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((ActLoginBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((ActLoginBinding) this.mViewBinding).etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((ActLoginBinding) this.mViewBinding).etPwd.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        NavUtils.destination(ActRegister.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        NavUtils.destination(ActFindPwd.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        NavUtils.destination(ActSelectCountry.class).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        String queryValue = SharedPreferenceUtil.queryValue(ActSelectCountry.SELECT_COUNTRY_KEY);
        String queryValue2 = SharedPreferenceUtil.queryValue(ActSelectCountry.SELECT_COUNTRY_EN_KEY);
        if (!TextUtils.isEmpty(queryValue)) {
            ((ActLoginVM) this.mViewModel).country.set(queryValue);
            ((ActLoginVM) this.mViewModel).countryCode.set(queryValue2);
        }
        ((ActLoginVM) this.mViewModel).loadCountry();
        setAccountHint();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (2001 == resultCode || 2002 == resultCode) {
            back();
        }
        if (resultCode == 2003) {
            String stringExtra = data.getStringExtra("psw");
            String stringExtra2 = data.getStringExtra(GetSmsCodeResetReq.ACCOUNT);
            ((ActLoginVM) this.mViewModel).country.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_KEY));
            ((ActLoginVM) this.mViewModel).countryCode.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_EN_KEY));
            ((ActLoginVM) this.mViewModel).password.set(stringExtra);
            ((ActLoginVM) this.mViewModel).account.set(stringExtra2);
            setAccountHint();
            ((ActLoginVM) this.mViewModel).loadCountry();
        }
        if (resultCode != 100 || data == null) {
            return;
        }
        ((ActLoginVM) this.mViewModel).countryStr.setValue(LanguageUtils.isChinese(this) ? data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_KEY) : data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_EN_KEY));
        ((ActLoginVM) this.mViewModel).country.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_KEY));
        ((ActLoginVM) this.mViewModel).countryCode.set(data.getStringExtra(ActSelectCountry.SELECT_COUNTRY_EN_KEY));
        if (((ActLoginVM) this.mViewModel).countryCode.get().equals("China")) {
            ((ActLoginBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.phone_mail));
        } else {
            ((ActLoginBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.mail));
        }
    }

    private void setAccountHint() {
        if (TextUtils.isEmpty(((ActLoginVM) this.mViewModel).countryCode.get()) || ((ActLoginVM) this.mViewModel).countryCode.get().equals("China")) {
            ((ActLoginBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.phone_mail));
        } else {
            ((ActLoginBinding) this.mViewBinding).tilAccount.setHint(getString(R.string.mail));
        }
        String queryValue = SharedPreferenceUtil.queryValue(Constants.USER_ACCOUNT);
        String queryValue2 = SharedPreferenceUtil.queryValue(Constants.USER_PWD);
        ((ActLoginVM) this.mViewModel).account.set(queryValue);
        ((ActLoginVM) this.mViewModel).password.set(queryValue2);
    }

    private ClickableSpan[] getClickableSpans() {
        return new ClickableSpan[]{new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.ActLogin.4
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ActLogin.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActLogin.this.getString(R.string.user_agreement_url)).navigation(ActLogin.this);
            }
        }, new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.ActLogin.5
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ActLogin.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActLogin.this.getString(R.string.privacy_agreement_url)).navigation(ActLogin.this);
            }
        }};
    }
}