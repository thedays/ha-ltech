package com.ltech.smarthome.ui.camera.config;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.granwin.apkit.utils.CommonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActNetConnectBinding;
import com.ltech.smarthome.ui.camera.EZManager;
import com.ltech.smarthome.ui.config.ActAddDevice;
import com.ltech.smarthome.ui.config.ActConfigSuccess;
import com.ltech.smarthome.ui.config.ActDeviceConfigFail;
import com.ltech.smarthome.ui.config.ActDeviceConnect;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.NetWorkUtil;
import com.ltech.smarthome.utils.ThreadPoolManager;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class ActCameraConfig extends BaseConnectNetActivity {
    private static final int MSG_QUERY_INFO = 111;
    boolean comeFromSettingPage;
    private boolean configFinish = false;
    private MessageHandler mHandler;
    String password;
    String ssid;
    boolean timeout;

    @Override // com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mHandler = new MessageHandler();
    }

    @Override // com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity
    protected void startConfig() {
        this.ssid = getIntent().getStringExtra(Constants.SSID);
        this.password = getIntent().getStringExtra(Constants.PWD);
        this.comeFromSettingPage = getIntent().getBooleanExtra(Constants.SETTING_PAGE, false);
        if (EZManager.instance().configType == 1) {
            setDeviceNetworkByAP(this.ssid, this.password);
        } else {
            setDeviceNetwork(this.ssid, this.password);
        }
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip1.setText(getString(R.string.send_info_to_device_success));
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip2.setText(getString(R.string.wait_for_device_connect));
    }

    @Override // com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity
    protected void connectFail() {
        super.connectFail();
        LHomeLog.i(ActCameraConfig.class, "setDeviceNetwork timeout ");
        NetWorkUtil.bindProcessToNetwork(Utils.getApp());
        this.timeout = true;
        if (this.configFinish) {
            return;
        }
        this.configFinish = true;
        NavUtils.destination(ActDeviceConfigFail.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withInt(ActDeviceConfigFail.ERROR_CODE, 2).navigation(this);
    }

    @Override // com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity
    protected void connectSuccess() {
        if (this.configFinish) {
            return;
        }
        this.configFinish = true;
        if (this.comeFromSettingPage) {
            NavUtils.destination(ActHome.class).navigation(this);
        } else {
            NavUtils.destination(ActConfigSuccess.class).navigation(this);
        }
    }

    private void setDeviceNetwork(String ssid, String password) {
        LHomeLog.i(ActDeviceConnect.class, "setDeviceNetwork enter");
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip1.setText(getString(R.string.send_info_to_device));
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip2.setText("");
        EZManager.instance().startConfigWifi(this, "", ssid, password, new EZManager.IConfigListener() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraConfig.1
            @Override // com.ltech.smarthome.ui.camera.EZManager.IConfigListener
            public void onConnected() {
                LHomeLog.i(getClass(), "setDeviceNetwork onConnectSuccess");
                EZManager.instance().stopConfigWifi();
                ActCameraConfig.this.mHandler.sendEmptyMessage(111);
            }

            @Override // com.ltech.smarthome.ui.camera.EZManager.IConfigListener
            public void onRegistered() {
                LHomeLog.i(getClass(), "setDeviceNetwork onRegisterSuccess");
            }
        });
    }

    private void setDeviceNetworkByAP(String ssid, String password) {
        LHomeLog.i(ActDeviceConnect.class, "setDeviceNetwork enter");
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip1.setText(getString(R.string.send_info_to_device));
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip2.setText("");
        EZManager.instance().startAPConfigWifi("", "", ssid, password, new EZManager.IConfigListener() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraConfig.2
            @Override // com.ltech.smarthome.ui.camera.EZManager.IConfigListener
            public void onRegistered() {
            }

            @Override // com.ltech.smarthome.ui.camera.EZManager.IConfigListener
            public void onConnected() {
                LHomeLog.i(getClass(), "setDeviceNetworkByAP onConnectSuccess");
                EZManager.instance().stopAPConfigWifi();
                NetWorkUtil.bindProcessToNetwork(Utils.getApp());
                ActCameraConfig.this.mHandler.sendEmptyMessage(111);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        showCancelDialog();
    }

    private void showCancelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_stop_config)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraConfig$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showCancelDialog$0;
                lambda$showCancelDialog$0 = ActCameraConfig.this.lambda$showCancelDialog$0(baseDialog, view);
                return lambda$showCancelDialog$0;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showCancelDialog$0(BaseDialog baseDialog, View view) {
        if (this.comeFromSettingPage) {
            finish();
            return false;
        }
        EZManager.instance().stopConfigWifi();
        NetWorkUtil.bindProcessToNetwork(Utils.getApp());
        NavUtils.destination(ActAddDevice.class).navigation(this);
        return false;
    }

    class MessageHandler extends Handler {
        MessageHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            ActCameraConfig.this.queryCamera();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryCamera() {
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraConfig.3
            @Override // java.lang.Runnable
            public void run() {
                EZManager.instance().queryDeviceInfo(new EZManager.IQueryDeviceListener() { // from class: com.ltech.smarthome.ui.camera.config.ActCameraConfig.3.1
                    @Override // com.ltech.smarthome.ui.camera.EZManager.IQueryDeviceListener
                    public void onAlreadyAddedByOther(boolean online) {
                    }

                    @Override // com.ltech.smarthome.ui.camera.EZManager.IQueryDeviceListener
                    public void onNeedAdd(boolean online) {
                        if (online) {
                            ActCameraConfig.this.connectSuccess();
                        } else {
                            if (ActCameraConfig.this.mHandler.hasMessages(111)) {
                                return;
                            }
                            ActCameraConfig.this.mHandler.sendEmptyMessageDelayed(111, 1000L);
                        }
                    }

                    @Override // com.ltech.smarthome.ui.camera.EZManager.IQueryDeviceListener
                    public void onAlreadyAddedBySelf(boolean online) {
                        if (online) {
                            ActCameraConfig.this.connectSuccess();
                        }
                    }

                    @Override // com.ltech.smarthome.ui.camera.EZManager.IQueryDeviceListener
                    public void onFail() {
                        if (ActCameraConfig.this.mHandler.hasMessages(111)) {
                            return;
                        }
                        ActCameraConfig.this.mHandler.sendEmptyMessageDelayed(111, 1000L);
                    }
                });
            }
        });
    }

    private boolean isConnectInternet() {
        if (NetworkUtils.isAvailableByPing() && NetworkUtils.isConnected()) {
            return true;
        }
        return NetworkUtils.isAvailableByPing("8.8.8.8") && NetworkUtils.isConnected();
    }

    private boolean checkWifiHasConnect() {
        return CommonUtils.getWIFISSID(this).equals(this.ssid);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        if (EZManager.instance().configType == 1) {
            EZManager.instance().stopAPConfigWifi();
        } else {
            EZManager.instance().stopConfigWifi();
        }
        this.mHandler.removeCallbacksAndMessages(null);
    }
}