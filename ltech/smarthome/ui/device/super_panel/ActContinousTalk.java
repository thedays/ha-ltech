package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.iflytek.home.sdk.IFlyHome;
import com.iflytek.home.sdk.callback.ResponseCallback;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSuperPanelContinousTalkBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.response.super_panel.IFlyDeviceDetail;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.item.SwitchItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Response;

/* loaded from: classes4.dex */
public class ActContinousTalk extends BaseDeviceSetActivity<ActSuperPanelContinousTalkBinding, ActContinousTalkVM> {
    public static final String DEVICE_SN = "device_sn";
    public static final String TAG = "ActContinousTalk";
    private String deviceSN;
    private boolean isContinous_mode;
    private SwitchItem switchItem;

    static /* synthetic */ void lambda$startObserve$1(Device device) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_continous_talk;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        this.isContinous_mode = getIntent().getBooleanExtra("continous", false);
        Log.i("isContinous_mode", "isContinous_mode=" + this.isContinous_mode);
        this.deviceSN = getIntent().getStringExtra("deviceSN");
        super.onCreate(savedInstanceState);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.talk));
        this.switchItem = new SwitchItem(TAG, this.isContinous_mode);
        ((ActSuperPanelContinousTalkBinding) this.mViewBinding).talkSwitch.setItem(this.switchItem);
        ((ActSuperPanelContinousTalkBinding) this.mViewBinding).talkSwitch.setCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActContinousTalk.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ActContinousTalk.this.switchItem.setChecked(isChecked);
                ActContinousTalk.this.updateDeviceInfo(isChecked);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        Intent intent = new Intent();
        intent.putExtra("continous_back", this.switchItem.isChecked().getValue());
        setResult(-1, intent);
        super.back();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActContinousTalkVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActContinousTalkVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActContinousTalk$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActContinousTalk.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActContinousTalkVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActContinousTalk$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActContinousTalk.lambda$startObserve$1((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActContinousTalkVM) this.mViewModel).controlDevice.setValue(device);
    }

    public void getDeviceDetail() {
        IFlyHome.INSTANCE.getDeviceDetail("000d9d32-b027-410d-981d-fead0b8cbddd." + ((ActContinousTalkVM) this.mViewModel).controlDevice.getValue().getDevicesn(), new ResponseCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.ActContinousTalk.2
            @Override // com.iflytek.home.sdk.callback.ResponseCallback
            public void onFailure(Call<String> call, Throwable throwable) {
            }

            @Override // com.iflytek.home.sdk.callback.ResponseCallback
            public void onResponse(Response<String> response) {
                if (response.isSuccessful()) {
                    ((ActSuperPanelContinousTalkBinding) ActContinousTalk.this.mViewBinding).talkSwitch.getItem().setChecked(((IFlyDeviceDetail) GsonUtils.getGson().fromJson(response.body(), IFlyDeviceDetail.class)).isContinous_mode());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceInfo(boolean z) {
        final SuperPanelExtParam superPanelExtParam;
        final Device value = ((ActContinousTalkVM) this.mViewModel).controlDevice.getValue();
        if (value != null) {
            if (value.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                IFlyHome.INSTANCE.updateDeviceInfo("000d9d32-b027-410d-981d-fead0b8cbddd." + this.deviceSN, null, null, Boolean.valueOf(z), null, new ResponseCallback(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActContinousTalk.3
                    @Override // com.iflytek.home.sdk.callback.ResponseCallback
                    public void onFailure(Call<String> call, Throwable throwable) {
                    }

                    @Override // com.iflytek.home.sdk.callback.ResponseCallback
                    public void onResponse(Response<String> response) {
                    }
                });
                return;
            }
            if (value.getExtParam() != null) {
                superPanelExtParam = (SuperPanelExtParam) value.getExtParam(SuperPanelExtParam.class);
                if (superPanelExtParam == null) {
                    superPanelExtParam = new SuperPanelExtParam();
                }
                superPanelExtParam.setContinous_mode(z ? 1 : 0);
            } else {
                superPanelExtParam = new SuperPanelExtParam();
                superPanelExtParam.setContinous_mode(z ? 1 : 0);
            }
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(value.getDeviceId(), GsonUtils.toJson(superPanelExtParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActContinousTalk$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActContinousTalk.this.lambda$updateDeviceInfo$2((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActContinousTalk$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActContinousTalk.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActContinousTalk$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActContinousTalk.lambda$updateDeviceInfo$3(Device.this, superPanelExtParam, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDeviceInfo$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    static /* synthetic */ void lambda$updateDeviceInfo$3(Device device, SuperPanelExtParam superPanelExtParam, Object obj) throws Exception {
        device.setExtParam(GsonUtils.toJson(superPanelExtParam));
        Injection.repo().device().saveDevice(device);
    }
}