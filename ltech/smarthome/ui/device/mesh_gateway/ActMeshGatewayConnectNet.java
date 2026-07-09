package com.ltech.smarthome.ui.device.mesh_gateway;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActNetConnectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.device.GetDeviceOnlineResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.ResponseMsg;
import com.smart.message.base.IReceiveListener;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.extra.Emitter;
import com.smart.product_agreement.param.SettingCmdParam;
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
public class ActMeshGatewayConnectNet extends BaseConnectNetActivity {
    private Device controlDevice;
    private Disposable disposable;
    private String platformId;
    private String pwd;
    private String ssid;

    static /* synthetic */ void lambda$checkOnline$3(Throwable th) throws Exception {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        long longExtra = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.ssid = getIntent().getStringExtra(Constants.SSID);
        this.pwd = getIntent().getStringExtra(Constants.PWD);
        Injection.repo().device().getDeviceFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayConnectNet$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshGatewayConnectNet.this.lambda$startObserve$0((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        if (this.controlDevice == null) {
            this.controlDevice = device;
        }
    }

    @Override // com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity
    protected void connectFail() {
        super.connectFail();
        stopCheckOnline();
    }

    @Override // com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity
    protected void startConfig() {
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip1.setText(getString(R.string.send_info_to_device));
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip2.setText("");
        LHomeLog.i(getClass(), "startConfig");
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(1);
        settingCmdParam.setCmdType(2);
        settingCmdParam.setSsid(this.ssid);
        settingCmdParam.setPwd(this.pwd);
        Injection.message().create(this).cmdParam(settingCmdParam).control(this.controlDevice).emitter(Emitter.BLUETOOTH).timeOutTime(5000).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayConnectNet.1
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                StringBuilder sb = new StringBuilder();
                int length = msg.getResData().length() / 2;
                for (int i = 0; i < length; i++) {
                    int i2 = i * 2;
                    sb.append((char) Integer.parseInt(msg.getResData().substring(i2, i2 + 2), 16));
                }
                String[] split = sb.toString().split("&");
                ActMeshGatewayConnectNet.this.platformId = split[2] + ProductId.SPLIT + split[0];
                ActMeshGatewayConnectNet actMeshGatewayConnectNet = ActMeshGatewayConnectNet.this;
                actMeshGatewayConnectNet.checkOnline(actMeshGatewayConnectNet.controlDevice.getProductId(), ActMeshGatewayConnectNet.this.platformId);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                ((ActNetConnectBinding) ActMeshGatewayConnectNet.this.mViewBinding).timeBar.setState(1);
            }
        }).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkOnline(final String productId, final String platformId) {
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip1.setText(getString(R.string.send_info_to_device_success));
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip2.setText(getString(R.string.wait_for_device_connect));
        this.disposable = ((ObservableSubscribeProxy) Observable.interval(1L, 5L, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).flatMap(new Function() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayConnectNet$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource deviceOnlineStatus;
                deviceOnlineStatus = Injection.net().getDeviceOnlineStatus(productId, platformId);
                return deviceOnlineStatus;
            }
        }).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayConnectNet$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshGatewayConnectNet.this.lambda$checkOnline$2(platformId, (GetDeviceOnlineResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayConnectNet$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshGatewayConnectNet.lambda$checkOnline$3((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkOnline$2(String str, GetDeviceOnlineResponse getDeviceOnlineResponse) throws Exception {
        if ("ONLINE".equals(getDeviceOnlineResponse.getStatus())) {
            stopCheckOnline();
            updateDeviceData(str);
        }
    }

    private void stopCheckOnline() {
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
    }

    private void updateDeviceData(final String platformId) {
        ((ObservableSubscribeProxy) Injection.net().updatePlatformId(this.controlDevice.getDeviceId(), platformId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayConnectNet$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshGatewayConnectNet.this.lambda$updateDeviceData$4(platformId, obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayConnectNet.2
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ((ActNetConnectBinding) ActMeshGatewayConnectNet.this.mViewBinding).timeBar.setState(1);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDeviceData$4(String str, Object obj) throws Exception {
        this.controlDevice.setPlatFormDeviceId(str);
        Injection.repo().device().saveDevice(this.controlDevice);
        ((ActNetConnectBinding) this.mViewBinding).timeBar.setState(0);
    }
}