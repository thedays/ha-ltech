package com.ltech.smarthome.ui.login;

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
import com.ltech.smarthome.databinding.FtMailRegBinding;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.TextClickUtil;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class FtMailRegister extends VMFragment<FtMailRegBinding, FtMailRegVM> {
    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_mail_reg;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        ProtocolDefault protocolDefault = new ProtocolDefault();
        getViewModel().checkedProtocol = protocolDefault.getCheckedProtocol();
        TitleDefault titleDefault = new TitleDefault();
        titleDefault.setBackImageRes(R.mipmap.icon_back);
        titleDefault.setTitle(getString(R.string.register));
        titleDefault.setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.login.FtMailRegister$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMailRegister.this.back();
            }
        }));
        ((FtMailRegBinding) this.mViewBinding).setTitle(titleDefault);
        initButtonStatus();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((FtMailRegVM) this.mViewModel).showVerificationCodeSendEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtMailRegister$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMailRegister.this.lambda$startObserve$1((String) obj);
            }
        });
        ((FtMailRegVM) this.mViewModel).pSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtMailRegister$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMailRegister.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((FtMailRegVM) this.mViewModel).selectAreaEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.login.FtMailRegister$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMailRegister.this.lambda$startObserve$3((Void) obj);
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
            ((FtMailRegBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see);
            ((FtMailRegBinding) this.mViewBinding).etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((FtMailRegBinding) this.mViewBinding).ivPassword.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((FtMailRegBinding) this.mViewBinding).etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((FtMailRegBinding) this.mViewBinding).etPwd.getText();
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

    private void initButtonStatus() {
        TextClickUtil.setTextClick(ActivityUtils.getTopActivity(), (TextView) ((FtMailRegBinding) this.mViewBinding).getRoot().findViewById(R.id.tv_protocol_text), getClickableSpans(), R.string.reg_protocol, R.string.reg_protocol_part1, R.string.reg_protocol_part2);
        ((FtMailRegBinding) this.mViewBinding).protocol.checkBox.setChecked(true);
        ((FtMailRegBinding) this.mViewBinding).protocol.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.login.FtMailRegister.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((FtMailRegBinding) FtMailRegister.this.mViewBinding).btRegister.setEnabled(isChecked);
                if (isChecked) {
                    ((FtMailRegBinding) FtMailRegister.this.mViewBinding).btRegister.setBackgroundResource(R.drawable.shape_red_bt);
                } else {
                    ((FtMailRegBinding) FtMailRegister.this.mViewBinding).btRegister.setBackgroundResource(R.drawable.shape_gray_bt_30);
                }
            }
        });
    }

    private ClickableSpan[] getClickableSpans() {
        return new ClickableSpan[]{new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.FtMailRegister.2
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(FtMailRegister.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, FtMailRegister.this.getString(R.string.user_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        }, new ClickableSpan() { // from class: com.ltech.smarthome.ui.login.FtMailRegister.3
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(FtMailRegister.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, FtMailRegister.this.getString(R.string.privacy_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        }};
    }
}