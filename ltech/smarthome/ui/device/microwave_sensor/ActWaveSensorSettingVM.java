package com.ltech.smarthome.ui.device.microwave_sensor;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SelectLuxDialog;

/* loaded from: classes4.dex */
public class ActWaveSensorSettingVM extends BaseDeviceSetViewModel {
    public boolean is24G;
    public SingleLiveEvent<Void> showAutomationDelayEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showTestModeEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSettingVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActWaveSensorSettingVM.this.lambda$new$2((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        switch (view.getId()) {
            case R.id.layout_automation_delay /* 2131297357 */:
                this.showAutomationDelayEvent.call();
                break;
            case R.id.layout_set_illuminance /* 2131297636 */:
                if (this.is24G) {
                    final Device value = this.controlDevice.getValue();
                    SelectLuxDialog.asDefault().setTitle(getContext().getString(R.string.edit)).setLux(value.getDeviceState().getWaveSensorState().getIlluminance()).setOnSaveListener(new SelectLuxDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSettingVM$$ExternalSyntheticLambda2
                        @Override // com.ltech.smarthome.view.dialog.SelectLuxDialog.OnSaveListener
                        public final void onSave(int i) {
                            ActWaveSensorSettingVM.this.lambda$new$1(value, i);
                        }
                    }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
                    break;
                } else {
                    NavUtils.destination(ActIlluminanceSetting.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, false).navigation(ActivityUtils.getTopActivity());
                    break;
                }
            case R.id.layout_set_sensitivity /* 2131297647 */:
                NavUtils.destination(ActSensitivitySetting.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, false).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_test_mode /* 2131297680 */:
                this.showTestModeEvent.call();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(final Device device, final int i) {
        showLoadingDialog();
        CmdAssistant.getDeviceAssistant(device, new int[0]).setIllumincance(getContext(), true, i, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSettingVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorSettingVM.this.lambda$new$0(device, i, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Device device, int i, Boolean bool) {
        if (bool.booleanValue()) {
            device.getDeviceState().getWaveSensorState().setIlluminance(i);
            Injection.repo().device().saveDevice(device);
            this.controlDevice.setValue(device);
            showSuccessTipDialog(getContext().getString(R.string.app_str_setting_success));
            return;
        }
        showErrorTipDialog(getContext().getString(R.string.app_str_setting_failed));
    }

    public String getLuxString(int lux) {
        return lux == 0 ? getContext().getString(R.string.illuminance_value_disable) : getContext().getString(R.string.lux_value, Integer.valueOf(lux));
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}