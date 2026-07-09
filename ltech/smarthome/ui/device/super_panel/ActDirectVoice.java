package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSuperPanelContinousTalkBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.item.SwitchItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.SwitchButton;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActDirectVoice extends BaseDeviceSetActivity<ActSuperPanelContinousTalkBinding, BaseDeviceSetViewModel> {
    private SwitchItem switchItem;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_continous_talk;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.direct_voice));
        ((ActSuperPanelContinousTalkBinding) this.mViewBinding).talkSwitch.tvMain.setText(getString(R.string.direct_voice));
        ((BaseDeviceSetViewModel) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Device deviceById = Injection.repo().device().getDeviceById(((BaseDeviceSetViewModel) this.mViewModel).controlId);
        if (deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || deviceById.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
            ((ActSuperPanelContinousTalkBinding) this.mViewBinding).tvTip.setText(getString(R.string.direct_voice_tip_4s));
        } else {
            ((ActSuperPanelContinousTalkBinding) this.mViewBinding).tvTip.setText(getString(R.string.direct_voice_tip));
        }
        this.switchItem = new SwitchItem("ActDirectVoice", getIntent().getBooleanExtra("isDirectVoice", false));
        ((ActSuperPanelContinousTalkBinding) this.mViewBinding).talkSwitch.setItem(this.switchItem);
        ((ActSuperPanelContinousTalkBinding) this.mViewBinding).talkSwitch.setCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActDirectVoice.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ActDirectVoice.this.switchItem.setChecked(isChecked);
                ActDirectVoice.this.updateDeviceInfo(isChecked);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        Intent intent = new Intent();
        intent.putExtra("DirectVoice_back", this.switchItem.isChecked().getValue());
        setResult(-1, intent);
        super.back();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceInfo(final boolean z) {
        final SuperPanelExtParam superPanelExtParam;
        final Device deviceById = Injection.repo().device().getDeviceById(((BaseDeviceSetViewModel) this.mViewModel).controlId);
        if (deviceById != null) {
            if (deviceById.getExtParam() != null) {
                superPanelExtParam = (SuperPanelExtParam) deviceById.getExtParam(SuperPanelExtParam.class);
                if (superPanelExtParam == null) {
                    superPanelExtParam = new SuperPanelExtParam();
                }
            } else {
                superPanelExtParam = new SuperPanelExtParam();
            }
            superPanelExtParam.setQuick_cmd(z ? 1 : 0);
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(deviceById.getDeviceId(), GsonUtils.toJson(superPanelExtParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActDirectVoice$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDirectVoice.this.lambda$updateDeviceInfo$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActDirectVoice$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActDirectVoice.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActDirectVoice$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDirectVoice.this.lambda$updateDeviceInfo$1(deviceById, superPanelExtParam, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActDirectVoice$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDirectVoice.this.lambda$updateDeviceInfo$2(z, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDeviceInfo$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDeviceInfo$1(Device device, SuperPanelExtParam superPanelExtParam, Object obj) throws Exception {
        SmartToast.showCenterShort(getString(R.string.save_success));
        device.setExtParam(GsonUtils.toJson(superPanelExtParam));
        Injection.repo().device().saveDevice(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDeviceInfo$2(boolean z, Throwable th) throws Exception {
        SmartToast.showCenterShort(getString(R.string.save_fail));
        this.switchItem.setChecked(!z);
    }
}