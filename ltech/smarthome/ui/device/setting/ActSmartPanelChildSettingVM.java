package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.control.ActIntelligence;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSmartPanelChildSettingVM extends BaseDeviceSetViewModel {
    public MutableLiveData<Boolean> isAddToRoom = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelChildSettingVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSmartPanelChildSettingVM.this.lambda$new$0((View) obj);
        }
    });
    public boolean isFirst = true;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_icon /* 2131297387 */:
                this.showSelectDeviceIconDialogEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_scene_and_automation /* 2131297612 */:
                navigation(NavUtils.destination(ActIntelligence.class).withLong("device_id", this.controlDevice.getValue().getDeviceId()).withBoolean(Constants.GROUP_CONTROL, false));
                break;
            case R.id.layout_scene_name /* 2131297615 */:
                this.showEditNameDialogEvent.call();
                break;
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    private void updateParamExt(String json) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getValue().getDeviceId(), json).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelChildSettingVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelChildSettingVM.this.lambda$updateParamExt$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$1(Object obj) throws Exception {
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
    }

    public void changeHide(final boolean isChecked) {
        final Device value = this.controlDevice.getValue();
        if (value != null) {
            ((ObservableSubscribeProxy) Injection.net().updateSubDeviceHide(value.getDeviceId(), isChecked).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelChildSettingVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelChildSettingVM.this.lambda$changeHide$2((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelChildSettingVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActSmartPanelChildSettingVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelChildSettingVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelChildSettingVM.lambda$changeHide$3(Device.this, isChecked, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeHide$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    static /* synthetic */ void lambda$changeHide$3(Device device, boolean z, Object obj) throws Exception {
        device.setSubhide(!z ? 1 : 0);
        Injection.repo().device().saveDevice(device);
        SmartToast.showShort(R.string.save_success);
    }
}