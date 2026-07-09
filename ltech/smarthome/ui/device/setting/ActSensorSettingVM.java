package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class ActSensorSettingVM extends BaseDeviceSetViewModel {
    private RelateInfoAssistant relateInfoAssistant;
    public MutableLiveData<String> sensitive = new MutableLiveData<>("");
    public MutableLiveData<String> reportInterval = new MutableLiveData<>("");
    public SingleLiveEvent<Void> reportIntervalEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> sensitivityEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSettingVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSensorSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_interval /* 2131297499 */:
                this.reportIntervalEvent.call();
                break;
            case R.id.layout_sensitivity /* 2131297628 */:
                this.sensitivityEvent.call();
                break;
            case R.id.layout_upgrade /* 2131297687 */:
                this.upgradeEvent.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void querySensorStatus() {
        if (this.controlDevice.getValue() != null) {
            this.relateInfoAssistant = new RelateInfoAssistant(this.controlDevice.getValue());
            MutableLiveData<String> mutableLiveData = this.sensitive;
            StringBuilder sb = new StringBuilder();
            sb.append(this.relateInfoAssistant.getSensitive() == 0 ? 10 : this.relateInfoAssistant.getSensitive());
            sb.append("DB");
            mutableLiveData.setValue(sb.toString());
            MutableLiveData<String> mutableLiveData2 = this.reportInterval;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.relateInfoAssistant.getReportTime() == 0 ? 30 : this.relateInfoAssistant.getReportTime());
            sb2.append(getContext().getString(R.string.sec));
            mutableLiveData2.setValue(sb2.toString());
            CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSettingVM$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSensorSettingVM.this.lambda$querySensorStatus$1((ResponseMsg) obj);
                }
            }, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$querySensorStatus$1(ResponseMsg responseMsg) {
        LHomeLog.i(getClass(), "querySensorControllerState enter---" + responseMsg);
        if (responseMsg != null) {
            LHomeLog.i(getClass(), "responseMsg --->" + responseMsg.getResData());
        }
    }

    public void setReportInterval(final int i) {
        showLoadingDialog(getContext().getString(R.string.saving));
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), new int[0]).setSensorReportInterval(getContext(), i, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSettingVM$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSensorSettingVM.this.lambda$setReportInterval$3(i, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setReportInterval$3(int i, Boolean bool) {
        if (bool.booleanValue()) {
            if (this.controlDevice.getValue() != null) {
                this.relateInfoAssistant.setReportTime(i);
                ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getValue().getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doFinally(new ActSensorSettingVM$$ExternalSyntheticLambda0(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSettingVM$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSensorSettingVM.this.lambda$setReportInterval$2(obj);
                    }
                }, new SmartErrorComsumer());
                return;
            } else {
                dismissLoadingDialog();
                showErrorTipDialog(getContext().getString(R.string.save_fail));
                return;
            }
        }
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setReportInterval$2(Object obj) throws Exception {
        this.controlDevice.getValue().setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }

    public void setSensitivity(final int i) {
        showLoadingDialog(getContext().getString(R.string.saving));
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), new int[0]).setSensorSensitivity(getContext(), i, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSettingVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSensorSettingVM.this.lambda$setSensitivity$5(i, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensitivity$5(int i, Boolean bool) {
        if (bool.booleanValue()) {
            if (this.controlDevice.getValue() != null) {
                this.relateInfoAssistant.setSensitive(i);
                ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getValue().getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doFinally(new ActSensorSettingVM$$ExternalSyntheticLambda0(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSettingVM$$ExternalSyntheticLambda5
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSensorSettingVM.this.lambda$setSensitivity$4(obj);
                    }
                }, new SmartErrorComsumer());
                return;
            } else {
                showErrorTipDialog(getContext().getString(R.string.save_fail));
                dismissLoadingDialog();
                return;
            }
        }
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensitivity$4(Object obj) throws Exception {
        this.controlDevice.getValue().setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }
}