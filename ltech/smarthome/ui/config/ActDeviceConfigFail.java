package com.ltech.smarthome.ui.config;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.text.Html;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.granwin.apkit.utils.CommonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActDeviceConfigFailBinding;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.TextClickUtil;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class ActDeviceConfigFail extends BaseNormalActivity<ActDeviceConfigFailBinding> {
    public static final String ERROR_CODE = "error_code";
    public static final int ERROR_CONFIG_FAIL = 2;
    public static final int ERROR_CONNECT_FAIL = 1;
    public static final int ERROR_SEARCH_TIMEOUT = 3;
    private boolean comeFromSettingPage;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_device_config_fail;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        this.comeFromSettingPage = getIntent().getBooleanExtra(Constants.SETTING_PAGE, false);
        TextClickUtil.setTextClick(this, ((ActDeviceConfigFailBinding) this.mViewBinding).appCompatTextView10, new ClickableSpan() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfigFail.1
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ActDeviceConfigFail.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActDeviceConfigFail.this.getString(R.string.faq_url)).navigation(ActDeviceConfigFail.this);
            }
        }, R.string.tip_help, R.string.tip_possible_problem);
        int intExtra = getIntent().getIntExtra(ERROR_CODE, -1);
        if (intExtra == 1) {
            ((ActDeviceConfigFailBinding) this.mViewBinding).clLayout.setVisibility(8);
            ((ActDeviceConfigFailBinding) this.mViewBinding).guidLayout.setVisibility(0);
            TextView textView = (TextView) ((ActDeviceConfigFailBinding) this.mViewBinding).guidLayout.findViewById(R.id.tv_guid_step1);
            TextView textView2 = (TextView) ((ActDeviceConfigFailBinding) this.mViewBinding).guidLayout.findViewById(R.id.tv_guid_step2);
            textView.setText(Html.fromHtml(getString(R.string.go_to_setting_connect_wifi1)));
            textView2.setText(String.format(getString(R.string.go_to_setting_connect_wifi2), ProductRepository.HOT_SSID, ProductRepository.HOT_PWD));
            ((ActDeviceConfigFailBinding) this.mViewBinding).guidLayout.findViewById(R.id.tv_copy_password).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfigFail.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    ActDeviceConfigFail.this.copyPassword();
                }
            });
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfigFail.3
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    ActDeviceConfigFail.this.setWifi();
                }
            });
        } else if (intExtra == 3) {
            ((ActDeviceConfigFailBinding) this.mViewBinding).clLayout.setVisibility(0);
            ((ActDeviceConfigFailBinding) this.mViewBinding).guidLayout.setVisibility(8);
            ((ActDeviceConfigFailBinding) this.mViewBinding).timeBar.setState(1);
            ((ActDeviceConfigFailBinding) this.mViewBinding).timeBar.setVisibility(0);
            ((ActDeviceConfigFailBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.tip_search_timeout));
            ((ActDeviceConfigFailBinding) this.mViewBinding).tvFailTip1.setText(getString(R.string.tip_search_timeout_1));
            ((ActDeviceConfigFailBinding) this.mViewBinding).tvFailTip2.setText(getString(R.string.tip_search_timeout_2));
        } else {
            ((ActDeviceConfigFailBinding) this.mViewBinding).clLayout.setVisibility(0);
            ((ActDeviceConfigFailBinding) this.mViewBinding).guidLayout.setVisibility(8);
            ((ActDeviceConfigFailBinding) this.mViewBinding).ivConfigTimeOut.setBackgroundResource(R.mipmap.pic_config_timeout);
            ((ActDeviceConfigFailBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.tip_config_fail));
            ((ActDeviceConfigFailBinding) this.mViewBinding).tvFailTip1.setText(getString(R.string.tip_config_fail_1));
            ((ActDeviceConfigFailBinding) this.mViewBinding).tvFailTip2.setText(getString(R.string.tip_config_fail_2));
        }
        ((ActDeviceConfigFailBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfigFail$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActDeviceConfigFail.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.bt_config_again) {
            return;
        }
        if (checkDeviceHasConnect()) {
            String stringExtra = getIntent().getStringExtra(Constants.SSID);
            NavUtils.destination(ActDeviceConfig.class).withString(Constants.SSID, stringExtra).withString(Constants.PWD, getIntent().getStringExtra(Constants.PWD)).navigation(this);
            return;
        }
        back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        LHomeLog.i(getClass(), "comeFromSettingPage=" + this.comeFromSettingPage);
        if (this.comeFromSettingPage) {
            finish();
        } else {
            NavUtils.destination(ActAddDevice.class).navigation(this);
        }
    }

    private boolean checkDeviceHasConnect() {
        return CommonUtils.getWIFISSID(this).equals(ProductRepository.HOT_SSID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWifi() {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
        startActivity(intent);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        checkDeviceHasConnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void copyPassword() {
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Label", ProductRepository.HOT_PWD));
        Toast.makeText(this, "copy password ok", 0).show();
    }
}