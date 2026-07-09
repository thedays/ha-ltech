package com.ltech.smarthome.ui.device.e6knob;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.RhythmsAssistant;

/* loaded from: classes4.dex */
public class ActE6PanelSettingVM extends ActRhythmsSettingVM {
    public MutableLiveData<Boolean> busPower = new MutableLiveData<>(false);
    public MutableLiveData<Integer> rgbInterface = new MutableLiveData<>(0);
    public MutableLiveData<Integer> dimSignal = new MutableLiveData<>(0);
    public SingleLiveEvent<Void> showSensitivityDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showModeOrderDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> buzzer = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> showKValueEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showRgbInterfaceEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showPowerStateEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDimSignalEvent = new SingleLiveEvent<>();
    public MutableLiveData<String> timeData = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActE6PanelSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_create_group /* 2131297408 */:
                this.showCreateGroupDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_dim_signal /* 2131297440 */:
                this.showDimSignalEvent.call();
                break;
            case R.id.layout_k_range /* 2131297503 */:
                this.showKValueEvent.call();
                break;
            case R.id.layout_mode_order /* 2131297540 */:
                this.showModeOrderDialogEvent.call();
                break;
            case R.id.layout_rgb_interface /* 2131297599 */:
                this.showRgbInterfaceEvent.call();
                break;
            case R.id.layout_sensitivity /* 2131297628 */:
                this.showSensitivityDialogEvent.call();
                break;
            case R.id.layout_set_on_state /* 2131297646 */:
                this.showPowerStateEvent.call();
                break;
            case R.id.layout_upgrade /* 2131297687 */:
                this.upgradeEvent.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    public void parseResponseMsg(String msg) {
        if (msg.length() >= 26) {
            String substring = msg.substring(16);
            if (Integer.parseInt(substring.substring(0, 2), 16) == 1) {
                int parseInt = Integer.parseInt(substring.substring(4, 6), 16);
                this.buzzer.setValue(Boolean.valueOf(((parseInt >> 7) & 1) == 1));
                this.rgbInterface.setValue(Integer.valueOf((parseInt >> 6) & 1));
                this.indicator.setValue(Boolean.valueOf(((parseInt >> 5) & 1) == 1));
                this.dimSignal.setValue(Integer.valueOf(((parseInt >> 4) & 1) == 0 ? 1 : 2));
                this.busPower.setValue(Boolean.valueOf(((parseInt >> 1) & 1) == 1));
                this.doubleMemorize.setValue(Boolean.valueOf((parseInt & 1) == 1));
                this.sensitivity.setValue(Integer.valueOf(Integer.parseInt(substring.substring(6, 8), 16)));
                int parseInt2 = Integer.parseInt(substring.substring(8, 10), 16);
                this.orderArray[0] = (parseInt2 >> 4) & 3;
                this.orderArray[1] = (parseInt2 >> 2) & 3;
                this.orderArray[2] = parseInt2 & 3;
                if (substring.length() >= 46) {
                    this.onState.setValue(new PowerState(substring.substring(40, 46)));
                }
                if (substring.length() >= 62) {
                    this.timeData.setValue(substring.substring(46));
                }
                checkBackData();
            }
        }
    }

    private void checkBackData() {
        int lightColorType = ProductRepository.getLightColorType((Object) this.controlDevice.getValue());
        int[] parseBackupData = ReplaceHelper.instance().parseBackupData(this.backDataResult.getValue(), UpdateBackDataRequest.RGB_TYPE);
        if (ProductId.ID_KNOB_PANEL_E6D.equals(this.controlDevice.getValue().getProductId()) && lightColorType == 3 && parseBackupData[0] != this.rgbInterface.getValue().intValue()) {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.RGB_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setRgbInterface(this.rgbInterface.getValue().intValue()));
        }
        if (ProductId.ID_KNOB_PANEL_E6T.equals(this.controlDevice.getValue().getProductId()) && ReplaceHelper.instance().parseBackupData(this.backDataResult.getValue(), UpdateBackDataRequest.LIGHT_TYPE)[0] != this.dimSignal.getValue().intValue()) {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setDimSignal(this.dimSignal.getValue().intValue()));
        }
        if ((ReplaceHelper.instance().parseBackupData(this.backDataResult.getValue(), UpdateBackDataRequest.INDICATOR_STATUS)[0] == 1) != this.indicator.getValue().booleanValue()) {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.INDICATOR_STATUS, CmdAssistant.getLightCmdAssistant(null, new int[0]).setBuzzerState(2, this.indicator.getValue().booleanValue() ? 1 : 0));
        }
        if ((ReplaceHelper.instance().parseBackupData(this.backDataResult.getValue(), UpdateBackDataRequest.BUZZER_STATUS)[0] == 1) != this.buzzer.getValue().booleanValue()) {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.BUZZER_STATUS, CmdAssistant.getLightCmdAssistant(null, new int[0]).setBuzzerState(1, this.buzzer.getValue().booleanValue() ? 1 : 0));
        }
        int[] parseBackupData2 = ReplaceHelper.instance().parseBackupData(this.backDataResult.getValue(), UpdateBackDataRequest.POWER_STATUS);
        if (this.onState.getValue() != null && (parseBackupData2[0] != this.onState.getValue().state || parseBackupData2[1] != this.onState.getValue().brt)) {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.POWER_STATUS, CmdAssistant.getLightCmdAssistant(null, new int[0]).setOnState(this.onState.getValue().state, this.onState.getValue().brt, this.onState.getValue().brt));
        }
        if (ProductId.ID_KNOB_PANEL_E6D.equals(this.controlDevice.getValue().getProductId())) {
            if ((ReplaceHelper.instance().parseBackupData(this.backDataResult.getValue(), UpdateBackDataRequest.DALI_ON_OFF)[0] == 1) != this.busPower.getValue().booleanValue()) {
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.DALI_ON_OFF, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setDaliBusPower(this.busPower.getValue().booleanValue() ? 1 : 0));
            }
        }
        if (lightColorType >= 2) {
            if ((ReplaceHelper.instance().parseBackupData(this.backDataResult.getValue(), UpdateBackDataRequest.KNOB_DOUBLE_MEMORY)[0] == 1) != this.doubleMemorize.getValue().booleanValue()) {
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.KNOB_DOUBLE_MEMORY, CmdAssistant.getLightCmdAssistant(null, new int[0]).setModeMemorize(this.doubleMemorize.getValue().booleanValue() ? 1 : 0));
            }
        }
        if (ReplaceHelper.instance().parseBackupData(this.backDataResult.getValue(), UpdateBackDataRequest.KNOB_SENSITIVITY)[0] != this.sensitivity.getValue().intValue()) {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.KNOB_SENSITIVITY, CmdAssistant.getDeviceAssistant(null, new int[0]).setKnobSensitivity(this.sensitivity.getValue().intValue()));
        }
        if (lightColorType >= 4) {
            int[] parseBackupData3 = ReplaceHelper.instance().parseBackupData(this.backDataResult.getValue(), UpdateBackDataRequest.KNOB_SORT);
            if (parseBackupData3[0] != this.orderArray[0] || parseBackupData3[1] != this.orderArray[1] || parseBackupData3[2] != this.orderArray[2]) {
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.KNOB_SORT, CmdAssistant.getDeviceAssistant(null, new int[0]).setKnobOrder(this.orderArray));
            }
        }
        ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId());
    }

    @Override // com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM
    public RhythmsAssistant getRhythmsAssistant() {
        return CmdAssistant.getLightRhythmsCmdAssistant(this.controlDevice.getValue(), new int[0]);
    }

    @Override // com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM
    public RhythmsAssistant getRhythmsQueryAssistant() {
        return getRhythmsAssistant();
    }
}