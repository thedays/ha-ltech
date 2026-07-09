package com.ltech.smarthome.ui.config;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.blankj.utilcode.util.Utils;
import com.granwin.apkit.GranwinAgent;
import com.granwin.apkit.utils.CommonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActDeviceConnectAndroid10Binding;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class ActDeviceConnectAndroid10 extends BaseNormalActivity<ActDeviceConnectAndroid10Binding> {
    boolean comeFromSettingPage;
    String password;
    String ssid;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_device_connect_android10;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ssid = getIntent().getStringExtra(Constants.SSID);
        this.password = getIntent().getStringExtra(Constants.PWD);
        this.comeFromSettingPage = getIntent().getBooleanExtra(Constants.SETTING_PAGE, false);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (checkDeviceHasConnect()) {
            connectSuccess();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        initUi();
        ((ActDeviceConnectAndroid10Binding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActDeviceConnectAndroid10$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActDeviceConnectAndroid10.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        showCancelDialog();
    }

    private void showCancelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_stop_config)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActDeviceConnectAndroid10$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showCancelDialog$1;
                lambda$showCancelDialog$1 = ActDeviceConnectAndroid10.this.lambda$showCancelDialog$1(baseDialog, view);
                return lambda$showCancelDialog$1;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showCancelDialog$1(BaseDialog baseDialog, View view) {
        if (this.comeFromSettingPage) {
            finish();
            return false;
        }
        NavUtils.destination(ActAddDevice.class).navigation(this);
        return false;
    }

    private void connectSuccess() {
        GranwinAgent.getInstance().start(Utils.getApp());
        finish();
        NavUtils.destination(ActDeviceConfig.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withString(Constants.SSID, this.ssid).withString(Constants.PWD, this.password).navigation(this);
    }

    private void initUi() {
        TextView textView = (TextView) ((ActDeviceConnectAndroid10Binding) this.mViewBinding).guidLayout.findViewById(R.id.tv_guid_step1);
        TextView textView2 = (TextView) ((ActDeviceConnectAndroid10Binding) this.mViewBinding).guidLayout.findViewById(R.id.tv_guid_step2);
        textView.setText(Html.fromHtml(getString(R.string.go_to_setting_connect_wifi1)));
        textView2.setText(String.format(getString(R.string.go_to_setting_connect_wifi2), ProductRepository.HOT_SSID, ProductRepository.HOT_PWD));
        ((ActDeviceConnectAndroid10Binding) this.mViewBinding).guidLayout.findViewById(R.id.tv_copy_password).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActDeviceConnectAndroid10.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActDeviceConnectAndroid10.this.copyPassword();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActDeviceConnectAndroid10.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActDeviceConnectAndroid10.this.setWifi();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWifi() {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void copyPassword() {
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Label", ProductRepository.HOT_PWD));
        Toast.makeText(this, "copy password ok", 0).show();
    }

    private boolean checkDeviceHasConnect() {
        return CommonUtils.getWIFISSID(this).equals(ProductRepository.HOT_SSID);
    }
}