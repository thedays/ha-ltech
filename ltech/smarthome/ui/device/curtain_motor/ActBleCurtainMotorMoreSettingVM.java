package com.ltech.smarthome.ui.device.curtain_motor;

import android.view.View;
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
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.config.ActRemoteControlLearning;
import com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.CurtainMotorState;
import com.smart.product_agreement.parser.IMotorParser;

/* loaded from: classes4.dex */
public class ActBleCurtainMotorMoreSettingVM extends BaseViewModel {
    private SettingAssistant mCmdAssistant;
    public long controlId = -1;
    public SingleLiveEvent<Void> showChangeMotorDirectionDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showMotorWhenStopDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showMotorAdjustDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showMotorSpeedDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> manuallyPull = new MutableLiveData<>();
    public MutableLiveData<Boolean> softStart = new MutableLiveData<>();
    public MutableLiveData<Boolean> limitPosition = new MutableLiveData<>();
    public MutableLiveData<Boolean> whenMotorStop = new MutableLiveData<>();
    public MutableLiveData<Boolean> memorizePosition = new MutableLiveData<>();
    public MutableLiveData<Boolean> syncManual = new MutableLiveData<>();
    public MutableLiveData<Boolean> adjustState = new MutableLiveData<>();
    public MutableLiveData<Integer> motorSpeed = new MutableLiveData<>();
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActBleCurtainMotorMoreSettingVM.this.lambda$new$0((View) obj);
        }
    });
    private CurtainMotorState state = new CurtainMotorState();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.btn_motor_direction) {
            this.showChangeMotorDirectionDialogEvent.call();
            return;
        }
        if (id == R.id.layout_when_to_stop) {
            this.showMotorWhenStopDialogEvent.call();
            return;
        }
        if (id == R.id.btn_adjust) {
            this.showMotorAdjustDialogEvent.call();
            return;
        }
        if (id == R.id.layout_remote_control) {
            navigation(NavUtils.destination(ActRemoteControlLearning.class).withLong(Constants.CONTROL_ID, this.controlId));
        } else if (id == R.id.layout_motor_open_type) {
            navigation(NavUtils.destination(ActBleCurtainMotorTypeSetting.class).withLong(Constants.CONTROL_ID, this.controlId));
        } else if (id == R.id.layout_motor_speed) {
            this.showMotorSpeedDialogEvent.call();
        }
    }

    public void loadSettingParam() {
        this.adjustState.setValue(false);
        if (this.controlId == -1) {
            return;
        }
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
        final Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
        this.mCmdAssistant = CmdAssistant.getSettingCmdAssistant(deviceById, new int[0]);
        CmdAssistant.getQueryCmdAssistant(deviceById, new int[0]).queryCurtainMotorSetting(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSettingVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                ActBleCurtainMotorMoreSettingVM.this.dismissLoadingDialog();
                if (msg == null) {
                    ActBleCurtainMotorMoreSettingVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.error_loading));
                    return;
                }
                BleParam bleParam = (BleParam) deviceById.getParam(BleParam.class);
                ActBleCurtainMotorMoreSettingVM.this.state = ((IMotorParser) Injection.strategy().getParserStrategy(msg.getAgreementId())).parserCurtainMotorSettingState(msg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, msg.getResData());
                if (ActBleCurtainMotorMoreSettingVM.this.state != null) {
                    ActBleCurtainMotorMoreSettingVM.this.manuallyPull.setValue(Boolean.valueOf(ActBleCurtainMotorMoreSettingVM.this.state.getManuallyPull() == 0));
                    ActBleCurtainMotorMoreSettingVM.this.softStart.setValue(Boolean.valueOf(ActBleCurtainMotorMoreSettingVM.this.state.getSoftStart() == 0));
                    ActBleCurtainMotorMoreSettingVM.this.limitPosition.setValue(Boolean.valueOf(ActBleCurtainMotorMoreSettingVM.this.state.getLimitPosition() == 0));
                    ActBleCurtainMotorMoreSettingVM.this.whenMotorStop.setValue(Boolean.valueOf(ActBleCurtainMotorMoreSettingVM.this.state.getWhenMotorStop() == 0));
                    ActBleCurtainMotorMoreSettingVM.this.memorizePosition.setValue(Boolean.valueOf(ActBleCurtainMotorMoreSettingVM.this.state.getMemorizePosition() == 0));
                    ActBleCurtainMotorMoreSettingVM.this.syncManual.setValue(Boolean.valueOf(ActBleCurtainMotorMoreSettingVM.this.state.getSyncManual() == 0));
                    ActBleCurtainMotorMoreSettingVM.this.motorSpeed.setValue(Integer.valueOf(ActBleCurtainMotorMoreSettingVM.this.state.getMotorSpeed()));
                }
                LHomeLog.i(ActBleCurtainMotorMoreSetting.class, msg.toString());
            }
        });
    }

    public void setMotorSpeed(final int speed) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        this.mCmdAssistant.setCurtainMotorSetting(ActivityUtils.getTopActivity(), getState().getManuallyPull(), getState().getMotorDirection(), getState().getSoftStart(), getState().getLimitPosition(), getState().getWhenMotorStop(), getState().getMemorizePosition(), getState().getSyncManual(), speed, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSettingVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean b2) {
                ActBleCurtainMotorMoreSettingVM.this.dismissLoadingDialog();
                if (b2.booleanValue()) {
                    ReplaceHelper.instance().backupData(ActBleCurtainMotorMoreSettingVM.this.getLifecycleOwner(), ActBleCurtainMotorMoreSettingVM.this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.CURTAIN_SETTING, ActBleCurtainMotorMoreSettingVM.this.mCmdAssistant.setCurtainMotorSetting(ActBleCurtainMotorMoreSettingVM.this.getState().getManuallyPull(), ActBleCurtainMotorMoreSettingVM.this.getState().getMotorDirection(), ActBleCurtainMotorMoreSettingVM.this.getState().getSoftStart(), ActBleCurtainMotorMoreSettingVM.this.getState().getLimitPosition(), ActBleCurtainMotorMoreSettingVM.this.getState().getWhenMotorStop(), ActBleCurtainMotorMoreSettingVM.this.getState().getMemorizePosition(), ActBleCurtainMotorMoreSettingVM.this.getState().getSyncManual(), speed));
                    ActBleCurtainMotorMoreSettingVM.this.motorSpeed.setValue(Integer.valueOf(speed));
                    SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_success));
                    return;
                }
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_failed));
            }
        });
    }

    public CurtainMotorState getState() {
        return this.state;
    }

    public void saveBleCurtainSetting() {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        this.mCmdAssistant.setCurtainMotorSetting(ActivityUtils.getTopActivity(), getState().getManuallyPull(), getState().getMotorDirection(), getState().getSoftStart(), getState().getLimitPosition(), getState().getWhenMotorStop(), getState().getMemorizePosition(), getState().getSyncManual(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSettingVM.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean b2) {
                ActBleCurtainMotorMoreSettingVM.this.dismissLoadingDialog();
                if (b2.booleanValue()) {
                    ReplaceHelper.instance().backupData(ActBleCurtainMotorMoreSettingVM.this.getLifecycleOwner(), ActBleCurtainMotorMoreSettingVM.this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.CURTAIN_SETTING, ActBleCurtainMotorMoreSettingVM.this.mCmdAssistant.setCurtainMotorSetting(ActBleCurtainMotorMoreSettingVM.this.getState().getManuallyPull(), ActBleCurtainMotorMoreSettingVM.this.getState().getMotorDirection(), ActBleCurtainMotorMoreSettingVM.this.getState().getSoftStart(), ActBleCurtainMotorMoreSettingVM.this.getState().getLimitPosition(), ActBleCurtainMotorMoreSettingVM.this.getState().getWhenMotorStop(), ActBleCurtainMotorMoreSettingVM.this.getState().getMemorizePosition(), ActBleCurtainMotorMoreSettingVM.this.getState().getSyncManual()));
                    SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_success));
                } else {
                    SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_failed));
                }
            }
        });
    }

    public void adjustLimitPosition() {
        this.mCmdAssistant.adjustLimitPosition(ActivityUtils.getTopActivity(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSettingVM.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean b2) {
                ActBleCurtainMotorMoreSettingVM.this.adjustState.setValue(false);
                if (b2.booleanValue()) {
                    SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_success));
                } else {
                    SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_failed));
                }
            }
        });
    }
}