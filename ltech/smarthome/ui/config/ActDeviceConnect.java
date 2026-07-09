package com.ltech.smarthome.ui.config;

import android.os.Bundle;
import android.view.View;
import com.blankj.utilcode.util.Utils;
import com.granwin.apkit.GranwinAgent;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActDeviceConnectBinding;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.NetWorkUtil;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class ActDeviceConnect extends BaseNormalActivity<ActDeviceConnectBinding> {
    boolean comeFromSettingPage;
    String password;
    String ssid;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_device_connect;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ssid = getIntent().getStringExtra(Constants.SSID);
        this.password = getIntent().getStringExtra(Constants.PWD);
        this.comeFromSettingPage = getIntent().getBooleanExtra(Constants.SETTING_PAGE, false);
        connectDeviceHot();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        initUi();
        ((ActDeviceConnectBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActDeviceConnect$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActDeviceConnect.this.lambda$initView$0((View) obj);
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
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_stop_config)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActDeviceConnect$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showCancelDialog$1;
                lambda$showCancelDialog$1 = ActDeviceConnect.this.lambda$showCancelDialog$1(baseDialog, view);
                return lambda$showCancelDialog$1;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showCancelDialog$1(BaseDialog baseDialog, View view) {
        GranwinAgent.getInstance().stopConnectDeviceHot();
        NetWorkUtil.bindProcessToNetwork(Utils.getApp());
        if (this.comeFromSettingPage) {
            finish();
            return false;
        }
        NavUtils.destination(ActAddDevice.class).navigation(this);
        return false;
    }

    private void connectDeviceHot() {
        LHomeLog.i(ActDeviceConnect.class, "connectDeviceHot enter");
        GranwinAgent.getInstance().start(Utils.getApp());
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActDeviceConnect$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ActDeviceConnect.this.lambda$connectDeviceHot$2();
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectDeviceHot$2() {
        GranwinAgent.getInstance().connectDeviceHot(ProductRepository.HOT_SSID, ProductRepository.HOT_PWD, new GranwinAgent.ConnectDeviceListener() { // from class: com.ltech.smarthome.ui.config.ActDeviceConnect.1
            @Override // com.granwin.apkit.GranwinAgent.ConnectDeviceListener
            public void onConnectSuccess() {
                LHomeLog.i(ActDeviceConnect.class, "connectDeviceHot success");
                ActDeviceConnect.this.connectSuccess();
            }

            @Override // com.granwin.apkit.GranwinAgent.ConnectDeviceListener
            public void onConnectFail(String s) {
                LHomeLog.i(ActDeviceConnect.class, "connectDeviceHot Fail=" + s);
                GranwinAgent.getInstance().stopConnectDeviceHot();
                NetWorkUtil.bindProcessToNetwork(Utils.getApp());
                ActDeviceConnect.this.connectFail();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectSuccess() {
        finish();
        NavUtils.destination(ActDeviceConfig.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withString(Constants.SSID, this.ssid).withString(Constants.PWD, this.password).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectFail() {
        finish();
        NavUtils.destination(ActDeviceConfigFail.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withInt(ActDeviceConfigFail.ERROR_CODE, 1).withString(Constants.SSID, this.ssid).withString(Constants.PWD, this.password).navigation(this);
    }

    private void initUi() {
        ((ActDeviceConnectBinding) this.mViewBinding).tvConfigTip1.setText(getString(R.string.tip_connecting_device_1));
        ((ActDeviceConnectBinding) this.mViewBinding).tvConfigTip2.setVisibility(0);
        ((ActDeviceConnectBinding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.pic_configing);
    }
}