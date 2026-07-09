package com.ltech.smarthome.ui.device.smartpanel;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class ActSmartPanelKeySetVM extends BaseViewModel {
    public long controlId;
    public int controlType;
    public boolean groupControl;
    public int position;
    public String productId;
    public RelateInfoAssistant relateInfoAssistant;
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<String> keyName = new MutableLiveData<>();
    public SingleLiveEvent<Void> showBindDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditNameDialogEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySetVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSmartPanelKeySetVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id != R.id.layout_bind_object) {
            if (id != R.id.layout_key_name) {
                return;
            }
            this.showEditNameDialogEvent.call();
        } else {
            if (this.groupControl) {
                if (((Group) this.controlObject.getValue()).getDeviceIds().isEmpty()) {
                    SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_group_empty));
                    return;
                } else {
                    this.showBindDialogEvent.call();
                    return;
                }
            }
            this.showBindDialogEvent.call();
        }
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public RelateInfoAssistant getRelateInfoAssistant(Object object) {
        if (object instanceof Device) {
            return new RelateInfoAssistant((Device) object);
        }
        if (object instanceof Group) {
            return new RelateInfoAssistant((Group) object);
        }
        return null;
    }

    public void changeKeyName(String s, int position) {
        this.relateInfoAssistant = getRelateInfoAssistant(this.controlObject.getValue());
        RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
        relateInfo.name = s;
        if (this.relateInfoAssistant.getRelateInfo(position) != null) {
            relateInfo.type = this.relateInfoAssistant.getRelateInfo(position).type;
            relateInfo.action = this.relateInfoAssistant.getRelateInfo(position).action;
            relateInfo.objectId = this.relateInfoAssistant.getRelateInfo(position).objectId;
            relateInfo.bindingZone = this.relateInfoAssistant.getRelateInfo(position).bindingZone;
        }
        this.relateInfoAssistant.setRelateInfo(position, relateInfo);
        if (this.groupControl) {
            uploadGroupData(s);
        } else {
            uploadDeviceData(s);
        }
    }

    private void uploadDeviceData(final String s) {
        final Device device = (Device) this.controlObject.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySetVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelKeySetVM.this.lambda$uploadDeviceData$1((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySetVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelKeySetVM.this.lambda$uploadDeviceData$2(device, s, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySetVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelKeySetVM.this.lambda$uploadDeviceData$3((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$2(Device device, String str, Object obj) throws Exception {
        device.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(device);
        this.controlObject.setValue(device);
        this.keyName.setValue(str);
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$3(Throwable th) throws Exception {
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    private void uploadGroupData(final String s) {
        final Group group = (Group) this.controlObject.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySetVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelKeySetVM.this.lambda$uploadGroupData$4((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySetVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelKeySetVM.this.lambda$uploadGroupData$5(group, s, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySetVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelKeySetVM.this.lambda$uploadGroupData$6((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadGroupData$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadGroupData$5(Group group, String str, Object obj) throws Exception {
        group.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().group().saveGroup(group);
        this.controlObject.setValue(group);
        this.keyName.setValue(str);
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadGroupData$6(Throwable th) throws Exception {
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }
}