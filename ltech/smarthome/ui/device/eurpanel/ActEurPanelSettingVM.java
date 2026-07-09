package com.ltech.smarthome.ui.device.eurpanel;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class ActEurPanelSettingVM extends BaseDeviceSetViewModel {
    public RelateInfoAssistant relateInfoAssistant;
    public MutableLiveData<Boolean> buzzerIsOpen = new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowBindKRange = new MutableLiveData<>();
    public SingleLiveEvent<Void> adjustKRange = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDmxTypeDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Integer> sensitivity = new MutableLiveData<>(0);
    public SingleLiveEvent<Void> showSensitivityDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showModeOrderDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> memorizeMode = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> keySave = new MutableLiveData<>(false);
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActEurPanelSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.btn_adjust /* 2131296487 */:
                this.adjustKRange.call();
                break;
            case R.id.layout_battery /* 2131297363 */:
                this.getBatteryEvent.call();
                break;
            case R.id.layout_brt_button /* 2131297380 */:
                NavUtils.destination(ActBrtButtonSetting.class).withLong("device_id", this.controlDevice.getValue().getDeviceId()).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_create_group /* 2131297408 */:
                this.showCreateGroupDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_mode_order /* 2131297540 */:
                this.showModeOrderDialogEvent.call();
                break;
            case R.id.layout_sensitivity /* 2131297628 */:
                this.showSensitivityDialogEvent.call();
                break;
            case R.id.layout_set_dmx_type /* 2131297635 */:
                this.showDmxTypeDialogEvent.call();
                break;
            case R.id.layout_set_on_state /* 2131297646 */:
                this.showPowerStateDialogEvent.call();
                break;
            case R.id.layout_upgrade /* 2131297687 */:
                this.upgradeEvent.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    public void initRelateInfoList(Device device) {
        RelateInfoUtils.initRelateInfoList(device);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void updateBuzzerState(boolean z) {
        this.relateInfoAssistant.setBuzzerState(z ? 1 : 0);
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getValue().getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSettingVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActEurPanelSettingVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEurPanelSettingVM.this.lambda$updateBuzzerState$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateBuzzerState$1(Object obj) throws Exception {
        this.controlDevice.getValue().setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
    }
}