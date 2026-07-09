package com.ltech.smarthome.ui.device.light;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.LightExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.control.ActEngineeringMode;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.RhythmsAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActLightSettingVM extends ActRhythmsSettingVM {
    public int imageIndex;
    public int lightType;
    public int lineOrder;
    public int showPowerOffSceneDelay;
    public int showPowerOnSceneDelay;
    public int whiteBalance;
    public SingleLiveEvent<Void> showDmxTypeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDimDepthDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showKValueEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showLineOrderEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showWhiteBalanceEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showControlTypeEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> constantPowerCheck = new MutableLiveData<>(false);
    public MutableLiveData<Integer> controlType = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> showPowerOnOffScene = new MutableLiveData<>(false);
    public MutableLiveData<String> showPowerOnScene = new MutableLiveData<>();
    public MutableLiveData<String> showPowerOffScene = new MutableLiveData<>();
    public MutableLiveData<String> showPowerOnSceneDelayStr = new MutableLiveData<>();
    public MutableLiveData<String> showPowerOffSceneDelayStr = new MutableLiveData<>();
    public MutableLiveData<Boolean> selectSceneDialogEvent = new MutableLiveData<>();
    public MutableLiveData<Boolean> selectSceneDelayDialogEvent = new MutableLiveData<>();
    public boolean supportDimDepth = true;
    public boolean supportDmxType = true;
    public boolean supportLineOrder = true;
    public boolean supportDimRange = true;
    public MutableLiveData<Boolean> query = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> refreshLineOrder = new SingleLiveEvent<>();
    public LightExtParam extParam = new LightExtParam();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActLightSettingVM.this.lambda$new$0((View) obj);
        }
    });

    static /* synthetic */ boolean lambda$setting$4(BaseDialog baseDialog, View view) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_batch_set /* 2131297362 */:
                if (getCurPlace() != null && (getCurPlace().isManager() || getCurPlace().isOwner())) {
                    NavUtils.destination(ActEngineeringMode.class).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()).navigation(ActivityUtils.getTopActivity());
                    break;
                } else {
                    this.showNoPermissionDialogEvent.call();
                    break;
                }
                break;
            case R.id.layout_change_icon /* 2131297387 */:
                this.showSelectDeviceIconDialogEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_control_type /* 2131297404 */:
                this.showControlTypeEvent.call();
                break;
            case R.id.layout_create_group /* 2131297408 */:
                this.showCreateGroupDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                NavUtils.destination(ActDiyLightName.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, false).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_dim_depth /* 2131297438 */:
                this.showDimDepthDialogEvent.call();
                break;
            case R.id.layout_dim_range /* 2131297439 */:
                showDimRangeDialog();
                break;
            case R.id.layout_duv /* 2131297453 */:
                NavUtils.destination(ActDuvList.class).withLong("device_id", this.deviceId).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_k_range /* 2131297503 */:
                this.showKValueEvent.call();
                break;
            case R.id.layout_line_set /* 2131297521 */:
                this.showLineOrderEvent.call();
                break;
            case R.id.layout_on_off_time /* 2131297559 */:
                navigation(NavUtils.destination(ActLightOnOffTime.class).withLong(Constants.CONTROL_ID, this.controlId).withDefaultRequestCode());
                break;
            case R.id.layout_power_off_scene /* 2131297580 */:
                this.selectSceneDialogEvent.setValue(false);
                break;
            case R.id.layout_power_off_scene_delay /* 2131297581 */:
                this.selectSceneDelayDialogEvent.setValue(false);
                break;
            case R.id.layout_power_on_scene /* 2131297582 */:
                this.selectSceneDialogEvent.setValue(true);
                break;
            case R.id.layout_power_on_scene_delay /* 2131297583 */:
                this.selectSceneDelayDialogEvent.setValue(true);
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
            case R.id.layout_white_balance /* 2131297695 */:
                this.showWhiteBalanceEvent.call();
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

    public boolean isBleSwitch() {
        return ProductRepository.isBleSwitch(Injection.repo().device().getDeviceById(this.controlId).getProductId());
    }

    @Override // com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM
    public RhythmsAssistant getRhythmsAssistant() {
        return CmdAssistant.getLightRhythmsCmdAssistant(this.controlDevice.getValue(), new int[0]);
    }

    @Override // com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM
    public RhythmsAssistant getRhythmsQueryAssistant() {
        return getRhythmsAssistant();
    }

    public void queryConstantPower() {
        CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryConstantPower(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSettingVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSettingVM.this.lambda$queryConstantPower$1((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryConstantPower$1(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData().length() <= 18) {
            return;
        }
        this.constantPowerCheck.setValue(Boolean.valueOf(Integer.parseInt(responseMsg.getResData().substring(18, 20), 16) == 2));
    }

    public void setConstantPower(final boolean isChecked) {
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), new int[0]).setConstantPower(ActivityUtils.getTopActivity(), isChecked ? 2 : 1, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSettingVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSettingVM.this.lambda$setConstantPower$2(isChecked, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setConstantPower$2(boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            return;
        }
        this.constantPowerCheck.setValue(Boolean.valueOf(!z));
    }

    public void changeControlType(final Integer integer) {
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), 65535).setControlType(getContext(), integer.intValue() == 0 ? 1 : 0, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSettingVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    SmartToast.showShort(R.string.app_str_setting_success);
                    ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CONTROL_INPUT_TYPE, CmdAssistant.getSettingCmdAssistant(ActLightSettingVM.this.controlDevice.getValue(), 65535).setControlType(integer.intValue() == 0 ? 1 : 0));
                    ReplaceHelper.instance().backupData(ActLightSettingVM.this.getLifecycleOwner(), ActLightSettingVM.this.controlDevice.getValue().getDeviceId());
                    ActLightSettingVM.this.controlType.setValue(Integer.valueOf(integer.intValue() != 0 ? 0 : 1));
                    return;
                }
                SmartToast.showShort(R.string.app_str_setting_failed);
            }
        });
    }

    public void queryControlType() {
        CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryControlType(getContext(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSettingVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                if (msg == null || msg.getResData() == null || msg.getResData().length() != 18) {
                    return;
                }
                ActLightSettingVM.this.controlType.setValue(Integer.valueOf(Integer.parseInt(msg.getResData().substring(16, 18)) & 1));
            }
        });
    }

    public void queryPowerOnOffScene() {
        final Device value = this.controlDevice.getValue();
        CmdAssistant.getQueryCmdAssistant(value, new int[0]).queryPowerOnOffScene(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSettingVM$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSettingVM.this.lambda$queryPowerOnOffScene$3(value, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPowerOnOffScene$3(Device device, ResponseMsg responseMsg) {
        int parseInt;
        if (device == null || responseMsg == null || responseMsg.getStateCode() != 0 || responseMsg.getResData() == null) {
            return;
        }
        BleParam bleParam = (BleParam) device.getParam(BleParam.class);
        if (Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) == (bleParam != null ? bleParam.getUnicastAddress() : 0) && Integer.parseInt(responseMsg.getResData().substring(18, 20), 16) == 2) {
            String substring = responseMsg.getResData().substring(20);
            if (substring.isEmpty() || (parseInt = Integer.parseInt(responseMsg.getResData().substring(18, 20), 16)) <= 0) {
                return;
            }
            String substring2 = substring.substring(2);
            for (int i = 0; i < parseInt * 2; i++) {
                boolean z = Integer.parseInt(substring2.substring(0, 2), 16) == 1;
                int parseInt2 = Integer.parseInt(substring2.substring(2, 4), 16);
                int parseInt3 = Integer.parseInt(substring2.substring(4, 6), 16);
                Scene localSceneBySceneNum = Injection.repo().scene().getLocalSceneBySceneNum(parseInt2);
                if (z) {
                    if (localSceneBySceneNum != null) {
                        this.showPowerOnScene.setValue(localSceneBySceneNum.getName());
                        this.showPowerOnSceneDelayStr.setValue(parseInt3 + getContext().getString(R.string.sec));
                        this.showPowerOnSceneDelay = parseInt3;
                    }
                } else if (localSceneBySceneNum != null) {
                    this.showPowerOffScene.setValue(localSceneBySceneNum.getName());
                    this.showPowerOffSceneDelayStr.setValue(parseInt3 + getContext().getString(R.string.sec));
                    this.showPowerOffSceneDelay = parseInt3;
                }
            }
        }
    }

    public void unbindScene(boolean isExc) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(isExc ? 1 : 2));
        arrayList.add(0);
        setting(3, arrayList, isExc);
    }

    public void bindSceneDelayTime(boolean isExc, int time) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(isExc ? 1 : 2));
        arrayList.add(Integer.valueOf(time));
        setting(4, arrayList, isExc);
    }

    private void setting(final int cmd, final List<Integer> data, final boolean isExc) {
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), new int[0]).setSmartPanelNightUpMode(ActivityUtils.getTopActivity(), cmd, data, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSettingVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSettingVM.this.lambda$setting$5(cmd, data, isExc, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setting$5(int i, List list, boolean z, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            if (i == 3 && ((Integer) list.get(1)).intValue() == 0) {
                ReplaceHelper.instance().addBackupIndexAndTypeData(UpdateBackDataRequest.TRIGGER_SCENE, null, 1, z ? 1 : 2);
                ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId());
                if (z) {
                    this.showPowerOnScene.setValue("");
                    return;
                } else {
                    this.showPowerOffScene.setValue("");
                    return;
                }
            }
            ReplaceHelper.instance().addBackupIndexAndTypeData(UpdateBackDataRequest.TRIGGER_DELAY, CmdAssistant.getSettingCmdAssistant(this.controlDevice, new int[0]).setSmartPanelNightUpMode(i, list), 1, z ? 1 : 2);
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId());
            int intValue = ((Integer) list.get(1)).intValue();
            if (z) {
                this.showPowerOnSceneDelay = ((Integer) list.get(1)).intValue();
                this.showPowerOnSceneDelayStr.setValue(intValue + getContext().getString(R.string.sec));
                return;
            }
            this.showPowerOffSceneDelay = ((Integer) list.get(1)).intValue();
            this.showPowerOffSceneDelayStr.setValue(intValue + getContext().getString(R.string.sec));
            return;
        }
        if (responseMsg == null || responseMsg.getStateCode() != 3) {
            return;
        }
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getContext().getString(R.string.app_str_operation_failure), getContext().getString(R.string.local_scene_error_03)).setOkButton(getContext().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSettingVM$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActLightSettingVM.lambda$setting$4(baseDialog, view);
            }
        });
    }

    public boolean isHB4() {
        BleParam bleParam = (BleParam) this.controlDevice.getValue().getParam(BleParam.class);
        return bleParam != null && bleParam.getBleType() == 526;
    }

    public boolean supportRhythm() {
        int i = this.lightType;
        return i == 2 || i == 20;
    }
}