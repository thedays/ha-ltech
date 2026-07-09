package com.ltech.smarthome.ui.config;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.granwin.apkit.GranwinAgent;
import com.granwin.apkit.entity.SetDeviceNetworkResultEntity;
import com.granwin.apkit.utils.CommonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActNetConnectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.RetryFunction;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.net.response.device.GetDeviceOnlineResponse;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.NetWorkUtil;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActDeviceConfig extends BaseConnectNetActivity {
    boolean comeFromSettingPage;
    private Disposable disposable;
    String mPlatformId;
    String mProductId;
    private SetDeviceNetworkResultEntity mResultEntity;
    String password;
    String ssid;
    boolean timeout;
    private boolean configFinish = false;
    boolean isAvailableByPing = false;

    @Override // com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity
    protected void startConfig() {
        this.ssid = getIntent().getStringExtra(Constants.SSID);
        this.password = getIntent().getStringExtra(Constants.PWD);
        this.comeFromSettingPage = getIntent().getBooleanExtra(Constants.SETTING_PAGE, false);
        setDeviceNetwork(this.ssid, this.password);
    }

    @Override // com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity
    protected void connectFail() {
        super.connectFail();
        LHomeLog.i(ActDeviceConfig.class, "setDeviceNetwork timeout ");
        NetWorkUtil.bindProcessToNetwork(Utils.getApp());
        this.timeout = true;
        if (!this.configFinish) {
            this.configFinish = true;
            NavUtils.destination(ActDeviceConfigFail.class).withBoolean(Constants.SETTING_PAGE, this.comeFromSettingPage).withInt(ActDeviceConfigFail.ERROR_CODE, 2).navigation(this);
        }
        stopCheckOnline();
    }

    @Override // com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity
    protected void connectSuccess() {
        if (this.configFinish) {
            return;
        }
        this.configFinish = true;
        ConfigHelper.instance().mac = this.mResultEntity.getMAC();
        ConfigHelper.instance().platDeviceId = this.mResultEntity.getPK() + ProductId.SPLIT + this.mResultEntity.getMID();
        ConfigHelper.instance().mid = this.mResultEntity.getMID();
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
        GranwinAgent.getInstance().setDeviceNetwork(ssid, password, ApiConstants.getWifiGatewayUrl(), new GranwinAgent.SetDeviceNetworkListener() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfig.1
            @Override // com.granwin.apkit.GranwinAgent.SetDeviceNetworkListener
            public void onConnectSuccess(SetDeviceNetworkResultEntity setDeviceNetworkResultEntity) {
                LHomeLog.i(getClass(), "setDeviceNetwork onConnectSuccess");
                ActDeviceConfig.this.mResultEntity = setDeviceNetworkResultEntity;
                LHomeLog.i(getClass(), "pk=" + ActDeviceConfig.this.mResultEntity.getPK() + "___mid=" + ActDeviceConfig.this.mResultEntity.getMID() + "_mac=" + ActDeviceConfig.this.mResultEntity.getMAC());
                GranwinAgent.getInstance().stopSetDeviceNetwork();
                ActDeviceConfig.this.checkOnline(ConfigHelper.instance().productInfo.getProductId(), ActDeviceConfig.this.mResultEntity.getMID());
            }

            @Override // com.granwin.apkit.GranwinAgent.SetDeviceNetworkListener
            public void onConnectFail(String s) {
                LHomeLog.i(getClass(), "setDeviceNetwork onConnectFail.s=" + s);
                ((ActNetConnectBinding) ActDeviceConfig.this.mViewBinding).timeBar.setState(1);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        showCancelDialog();
    }

    private void showCancelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_stop_config)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfig$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showCancelDialog$0;
                lambda$showCancelDialog$0 = ActDeviceConfig.this.lambda$showCancelDialog$0(baseDialog, view);
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
        GranwinAgent.getInstance().stopSetDeviceNetwork();
        NetWorkUtil.bindProcessToNetwork(Utils.getApp());
        NavUtils.destination(ActAddDevice.class).navigation(this);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkOnline(final String productId, final String platformId) {
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip1.setText(getString(R.string.send_info_to_device_success));
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip2.setText(getString(R.string.wait_for_device_connect));
        this.mProductId = productId;
        this.mPlatformId = platformId;
        this.disposable = ((ObservableSubscribeProxy) Observable.interval(3L, 5L, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).flatMap(new Function() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfig$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource deviceOnlineStatus;
                deviceOnlineStatus = Injection.net().getDeviceOnlineStatus(productId, platformId);
                return deviceOnlineStatus;
            }
        }).observeOn(AndroidSchedulers.mainThread()).retryWhen(new RetryFunction(30, 0, 0)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfig$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDeviceConfig.this.lambda$checkOnline$2((GetDeviceOnlineResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfig$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDeviceConfig.this.lambda$checkOnline$3((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkOnline$2(GetDeviceOnlineResponse getDeviceOnlineResponse) throws Exception {
        LHomeLog.i(getClass(), "request enter response-->" + getDeviceOnlineResponse);
        if ("ONLINE".equals(getDeviceOnlineResponse.getStatus())) {
            stopCheckOnline();
            ((ActNetConnectBinding) this.mViewBinding).timeBar.setState(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkOnline$3(Throwable th) throws Exception {
        LHomeLog.i(getClass(), "request enter throwable-->" + th);
    }

    private void stopCheckOnline() {
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
    }

    private void checkWifiConnect() {
        LHomeLog.i(getClass(), "checkWifiConnect enter");
        while (!this.timeout) {
            if (isConnectInternet()) {
                LHomeLog.i(getClass(), "checkWifiConnect true");
                this.disposable = ((ObservableSubscribeProxy) Observable.interval(3L, 5L, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).flatMap(new Function() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfig$$ExternalSyntheticLambda4
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        ObservableSource lambda$checkWifiConnect$4;
                        lambda$checkWifiConnect$4 = ActDeviceConfig.this.lambda$checkWifiConnect$4((Long) obj);
                        return lambda$checkWifiConnect$4;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfig$$ExternalSyntheticLambda5
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActDeviceConfig.this.lambda$checkWifiConnect$5((GetDeviceOnlineResponse) obj);
                    }
                }, new Consumer() { // from class: com.ltech.smarthome.ui.config.ActDeviceConfig$$ExternalSyntheticLambda6
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActDeviceConfig.this.lambda$checkWifiConnect$6((Throwable) obj);
                    }
                });
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource lambda$checkWifiConnect$4(Long l) throws Exception {
        return Injection.net().getDeviceOnlineStatus(this.mProductId, this.mPlatformId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkWifiConnect$5(GetDeviceOnlineResponse getDeviceOnlineResponse) throws Exception {
        LHomeLog.i(getClass(), "request enter response-->" + getDeviceOnlineResponse);
        if ("ONLINE".equals(getDeviceOnlineResponse.getStatus())) {
            stopCheckOnline();
            ((ActNetConnectBinding) this.mViewBinding).timeBar.setState(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkWifiConnect$6(Throwable th) throws Exception {
        LHomeLog.i(getClass(), "request enter throwable-->" + th);
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
        GranwinAgent.getInstance().stopSetDeviceNetwork();
    }
}