package com.ltech.smarthome.ui.device.dalipro;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.CgdProGroupExtParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.group.BaseGroupSettingVM;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class ActDaliLightGroupSettingVM extends BaseGroupSettingVM {
    public MutableLiveData<Boolean> isAddToRoom = new MutableLiveData<>();
    public MutableLiveData<String> groupName = new MutableLiveData<>("");
    public SingleLiveEvent<Void> showEditNameDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeRoomEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeIconEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> managerGroupEvent = new SingleLiveEvent<>();
    public MutableLiveData<Device> parentDevice = new MutableLiveData<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDaliLightGroupSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_icon /* 2131297387 */:
                this.changeIconEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.changeRoomEvent.call();
                break;
            case R.id.layout_manager_group /* 2131297532 */:
                this.managerGroupEvent.call();
                break;
            case R.id.layout_scene_name /* 2131297615 */:
                this.showEditNameDialogEvent.call();
                break;
        }
    }

    public void setAddToSmart(boolean z) {
        final CgdProGroupExtParam cgdProGroupExtParam = (CgdProGroupExtParam) this.controlObject.getValue().getExtParam(CgdProGroupExtParam.class);
        cgdProGroupExtParam.setDaliHidden(!z ? 1 : 0);
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.controlObject.getValue().getGroupId(), GsonUtils.toJson(cgdProGroupExtParam)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSettingVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightGroupSettingVM.this.lambda$setAddToSmart$1((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActDaliLightGroupSettingVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSettingVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightGroupSettingVM.this.lambda$setAddToSmart$2(cgdProGroupExtParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAddToSmart$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAddToSmart$2(CgdProGroupExtParam cgdProGroupExtParam, Object obj) throws Exception {
        this.controlObject.getValue().setExtParam(GsonUtils.toJson(cgdProGroupExtParam));
        Injection.repo().group().saveGroup(this.controlObject.getValue());
        this.controlObject.setValue(this.controlObject.getValue());
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }
}