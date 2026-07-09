package com.ltech.smarthome.ui.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActNetConfigBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.permission.ActGetLocationPermission;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.NetWorkUtil;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;

/* loaded from: classes4.dex */
public class ActNetConfig extends BaseNormalActivity<ActNetConfigBinding> {
    private static final int REQUEST_ENABLE_BLUETOOTH = 1021;
    private static final String UNKNOWN_SSID = "<unknown ssid>";
    private boolean comeFromSettingPage;
    private String productId;
    public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>(false);
    public MutableLiveData<String> ssidLiveData = new MutableLiveData<>(UNKNOWN_SSID);
    public MutableLiveData<String> pwdLiveData = new MutableLiveData<>("");
    private boolean isRegisterReceiver = false;
    private boolean isConnectWifi = false;
    private boolean isWifiEnable = false;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActNetConfig$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActNetConfig.this.lambda$new$1((View) obj);
        }
    });
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() { // from class: com.ltech.smarthome.ui.config.ActNetConfig.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                Log.i(Constants.SSID, "ssid=" + ActNetConfig.this.ssidLiveData.getValue());
                if (3 == intent.getIntExtra("wifi_state", 0)) {
                    ActNetConfig.this.isWifiEnable = true;
                } else {
                    ActNetConfig.this.isConnectWifi = false;
                    ActNetConfig.this.isWifiEnable = false;
                }
                ActNetConfig.this.setNextBg();
                return;
            }
            if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction())) {
                ActNetConfig actNetConfig = ActNetConfig.this;
                String sSid = actNetConfig.getSSid(actNetConfig);
                Log.i(Constants.SSID, "ssid=" + sSid + "__ssidLiveData=" + ActNetConfig.this.ssidLiveData.getValue());
                if (TextUtils.isEmpty(sSid) || ProductRepository.HOT_SSID.equals(sSid) || ActNetConfig.UNKNOWN_SSID.equals(sSid)) {
                    return;
                }
                ActNetConfig.this.ssidLiveData.setValue(sSid);
                ActNetConfig.this.pwdLiveData.setValue(SharedPreferenceUtil.queryValue(Constants.SSID_PWD + sSid));
                ActNetConfig.this.isConnectWifi = ActNetConfig.UNKNOWN_SSID.equals(sSid) ^ true;
                ActNetConfig.this.setNextBg();
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void setNextBg() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_net_config;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        this.comeFromSettingPage = getIntent().getBooleanExtra(Constants.SETTING_PAGE, false);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActNetConfigBinding) this.mViewBinding).setClickCommand(this.viewClick);
        ((ActNetConfigBinding) this.mViewBinding).setSsid(this.ssidLiveData);
        ((ActNetConfigBinding) this.mViewBinding).setPassword(this.pwdLiveData);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.pSwitchEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActNetConfig$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNetConfig.this.lambda$startObserve$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActNetConfigBinding) this.mViewBinding).ivEye.setBackgroundResource(R.mipmap.sign_icon_see);
            ((ActNetConfigBinding) this.mViewBinding).etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ((ActNetConfigBinding) this.mViewBinding).ivEye.setBackgroundResource(R.mipmap.sign_icon_see_none);
            ((ActNetConfigBinding) this.mViewBinding).etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable text = ((ActNetConfigBinding) this.mViewBinding).etPwd.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        int id = view.getId();
        if (id == R.id.bt_next) {
            if (!Injection.state().isBluetoothEnabled()) {
                startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 1021);
                return;
            } else {
                next();
                return;
            }
        }
        if (id == R.id.iv_eye) {
            SingleLiveEvent<Boolean> singleLiveEvent = this.pSwitchEvent;
            singleLiveEvent.setValue(Boolean.valueOf(singleLiveEvent.getValue() == null || !this.pSwitchEvent.getValue().booleanValue()));
        } else {
            if (id != R.id.tv_change_network) {
                return;
            }
            startActivity(new Intent("android.settings.WIFI_SETTINGS"));
        }
    }

    private void checkPermission() {
        ActivityResultHelper.init(this).startActivityForResult(ActGetLocationPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.config.ActNetConfig$$ExternalSyntheticLambda2
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ActNetConfig.this.lambda$checkPermission$2(i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkPermission$2(int i, Intent intent) {
        if (100 == i) {
            registerReceiver();
        } else if (101 == i) {
            back();
        }
    }

    private void registerReceiver() {
        if (this.isRegisterReceiver) {
            return;
        }
        this.isRegisterReceiver = true;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(this.wifiStateReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        if (this.isRegisterReceiver) {
            unregisterReceiver(this.wifiStateReceiver);
            this.isRegisterReceiver = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSSid(Context context) {
        WifiInfo connectionInfo;
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (wifiManager != null && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
            return getSSidString(connectionInfo);
        }
        return "";
    }

    private String getSSidString(WifiInfo wifiInfo) {
        if (wifiInfo != null) {
            String ssid = wifiInfo.getSSID();
            return (ssid == null || ssid.length() <= 0 || ssid.length() <= 2 || ssid.charAt(0) != '\"' || ssid.charAt(ssid.length() - 1) != '\"') ? ssid : ssid.substring(1, ssid.length() - 1);
        }
        return "";
    }

    protected void next() {
        if (this.pwdLiveData.getValue().equals("")) {
            SmartToast.showShort(R.string.input_wifi_pwd);
            return;
        }
        ConfigHelper.instance().ssid = this.ssidLiveData.getValue();
        ConfigHelper.instance().password = this.pwdLiveData.getValue();
        if (this.productId.equals(ProductId.ID_MESH_GATEWAY)) {
            if (getApplicationInfo().targetSdkVersion >= 29) {
                if (Build.VERSION.SDK_INT >= 29) {
                    NavUtils.destination(ActDeviceConnectAndroid10.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withString(Constants.SSID, this.ssidLiveData.getValue()).withString(Constants.PWD, this.pwdLiveData.getValue()).navigation(this);
                } else {
                    NavUtils.destination(ActDeviceConnect.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withString(Constants.SSID, this.ssidLiveData.getValue()).withString(Constants.PWD, this.pwdLiveData.getValue()).navigation(this);
                }
            } else if (Build.VERSION.SDK_INT >= 30) {
                if (NetWorkUtil.isNetWorkSaved(this, ProductRepository.HOT_SSID)) {
                    NavUtils.destination(ActDeviceConnect.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withString(Constants.SSID, this.ssidLiveData.getValue()).withString(Constants.PWD, this.pwdLiveData.getValue()).navigation(this);
                } else if (!NetWorkUtil.isNetWorkSaved(this, ProductRepository.HOT_SSID)) {
                    NavUtils.destination(ActDeviceConnectAndroid10.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withString(Constants.SSID, this.ssidLiveData.getValue()).withString(Constants.PWD, this.pwdLiveData.getValue()).navigation(this);
                }
            } else {
                NavUtils.destination(ActDeviceConnect.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withString(Constants.SSID, this.ssidLiveData.getValue()).withString(Constants.PWD, this.pwdLiveData.getValue()).navigation(this);
            }
        } else if (this.productId.equals(ProductId.ID_WIFI_CAMERA)) {
            NavUtils.destination("com.ltech.smarthome.ui.camera.config.ActCameraConfig").withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withString(Constants.SSID, this.ssidLiveData.getValue()).withString(Constants.PWD, this.pwdLiveData.getValue()).navigation(this);
        }
        SharedPreferenceUtil.edit().keepShared(Constants.SSID_PWD + this.ssidLiveData.getValue(), this.pwdLiveData.getValue()).apply();
    }

    protected boolean validSSID() {
        return (TextUtils.isEmpty(this.ssidLiveData.getValue()) || UNKNOWN_SSID.equals(this.ssidLiveData.getValue())) ? false : true;
    }
}