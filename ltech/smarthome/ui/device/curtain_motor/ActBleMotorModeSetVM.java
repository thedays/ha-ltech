package com.ltech.smarthome.ui.device.curtain_motor;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ActSelectIcon;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class ActBleMotorModeSetVM extends BaseViewModel {
    public long controlId;
    public CurtainMotorInfoExtParam curtainMotorInfoExtParam;
    public int modePosition;
    public int position;
    public String productId;
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<String> modeName = new MutableLiveData<>();
    public SingleLiveEvent<Void> showBindDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditModeIconDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditNameDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Integer> sceneIconPos = new MutableLiveData<>(0);
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSetVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActBleMotorModeSetVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.layout_change_icon) {
            navigation(NavUtils.destination(ActSelectIcon.class).withInt(Constants.ICON_POSITION, this.sceneIconPos.getValue().intValue()).withBoolean(Constants.IS_SCENE, false).withDefaultRequestCode());
        } else {
            if (id != R.id.layout_key_name) {
                return;
            }
            this.showEditNameDialogEvent.call();
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

    public void changeModeName(String s, int position) {
        this.curtainMotorInfoExtParam = new CurtainMotorInfoExtParam();
        this.curtainMotorInfoExtParam.fillMapWithString(((Device) this.controlObject.getValue()).getExtParam());
        this.curtainMotorInfoExtParam.setModeInfo(position, s);
        uploadDeviceData(s);
    }

    private void uploadDeviceData(final String s) {
        final Device device = (Device) this.controlObject.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), this.curtainMotorInfoExtParam.getParamMapString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSetVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleMotorModeSetVM.this.lambda$uploadDeviceData$1((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSetVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleMotorModeSetVM.this.lambda$uploadDeviceData$2(device, s, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSetVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleMotorModeSetVM.this.lambda$uploadDeviceData$3((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$2(Device device, String str, Object obj) throws Exception {
        device.setExtParam(this.curtainMotorInfoExtParam.getParamMapString());
        Injection.repo().device().saveDevice(device);
        this.controlObject.setValue(device);
        this.modeName.setValue(str);
        CurtainRepository.getModeList().get(this.modePosition).setName(str);
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$3(Throwable th) throws Exception {
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    public void setCurtainMode(final int progress) {
        final Device device = (Device) this.controlObject.getValue();
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        CmdAssistant.getSettingCmdAssistant(device, new int[0]).setCurtainMotorMode(ActivityUtils.getTopActivity(), this.modePosition + 1, this.sceneIconPos.getValue().intValue(), progress, new IAction() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSetVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleMotorModeSetVM.this.lambda$setCurtainMode$4(device, progress, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCurtainMode$4(Device device, int i, Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            if (device != null && device.getDeviceState() != null && device.getDeviceState().getCurtainMotorState() != null && device.getDeviceState().getCurtainMotorState().getModeInfoList().size() > 0) {
                ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), device.getDeviceId(), UpdateBackDataRequest.CURTAIN_MODES, CmdAssistant.getSettingCmdAssistant(device, new int[0]).setCurtainMotorMode(this.modePosition + 1, this.sceneIconPos.getValue().intValue(), i), this.modePosition + 1);
                device.getDeviceState().getCurtainMotorState().getModeInfoList().get(this.modePosition).setModePositionPercent(100 - i);
                device.getDeviceState().getCurtainMotorState().getModeInfoList().get(this.modePosition).setModeIconNum(this.sceneIconPos.getValue().intValue());
                Injection.repo().device().saveDevice(device);
                finishActivity(3005, null);
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_success));
                return;
            }
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_failed));
            return;
        }
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_failed));
    }
}