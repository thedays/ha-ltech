package com.ltech.smarthome.ui.device.gqx;

import android.content.Context;
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
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.key.KeyInfo;
import com.ltech.smarthome.model.key.KeyZone;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.device.KeyZonesResponse;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.smart.product_agreement.param.SettingCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActGqxSettingVM extends BaseDeviceSetViewModel {
    private List<KeyZone> keyZoneList;
    private DeviceAssistant mCmdAssistant;
    private SmartPanelSettingState state;
    public SingleLiveEvent<Void> adjustKRange = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> volumeSetEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> isShowBindKRange = new MutableLiveData<>();
    public MutableLiveData<Boolean> showSensitivitySetting = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> showVoiceSetting = new MutableLiveData<>(true);
    public MutableLiveData<Integer> SensitivitySelectedEvent = new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowIconVersion = new MutableLiveData<>();
    public MutableLiveData<Boolean> newIconVersion = new MutableLiveData<>();
    public MutableLiveData<String> iconVersion = new MutableLiveData<>();
    public MutableLiveData<String> key1Sensitivity = new MutableLiveData<>(getContext().getString(R.string.medium));
    public MutableLiveData<String> key2Sensitivity = new MutableLiveData<>(getContext().getString(R.string.medium));
    public MutableLiveData<String> key3Sensitivity = new MutableLiveData<>(getContext().getString(R.string.medium));
    public MutableLiveData<String> key4Sensitivity = new MutableLiveData<>(getContext().getString(R.string.medium));
    public MutableLiveData<Integer> batteryNum = new MutableLiveData<>();
    public boolean isFirst = true;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActGqxSettingVM.this.lambda$new$0((View) obj);
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
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_key_1 /* 2131297504 */:
                this.SensitivitySelectedEvent.setValue(0);
                break;
            case R.id.layout_key_2 /* 2131297505 */:
                this.SensitivitySelectedEvent.setValue(1);
                break;
            case R.id.layout_key_3 /* 2131297506 */:
                this.SensitivitySelectedEvent.setValue(2);
                break;
            case R.id.layout_key_4 /* 2131297507 */:
                this.SensitivitySelectedEvent.setValue(3);
                break;
            case R.id.layout_upgrade /* 2131297687 */:
                this.upgradeEvent.call();
                break;
            case R.id.layout_volume /* 2131297692 */:
                this.adjustKRange.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    public void loadDeviceStatus(Device device) {
        if (this.isFirst) {
            this.isFirst = false;
            Device value = this.controlDevice.getValue();
            if (value != null) {
                try {
                    if (value.getExtParam() != null) {
                        JSONObject jSONObject = new JSONObject(value.getExtParam());
                        this.batteryNum.setValue(Integer.valueOf(jSONObject.optInt("battery", 100)));
                        boolean z = true;
                        if (jSONObject.optInt(UpdateBackDataRequest.BUZZER_STATUS, 1) != 1) {
                            z = false;
                        }
                        this.showVoiceSetting.setValue(Boolean.valueOf(z));
                        setBuzzerEnable(z, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            getKeySensitivity();
            this.mCmdAssistant = CmdAssistant.getDeviceAssistant(device, new int[0]);
        }
    }

    private void getKeySensitivity() {
        Device value = this.controlDevice.getValue();
        if (value != null) {
            ((ObservableSubscribeProxy) Injection.net().getKeyZone(value.getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActGqxSettingVM.this.lambda$getKeySensitivity$1((KeyZonesResponse) obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getKeySensitivity$1(KeyZonesResponse keyZonesResponse) throws Exception {
        if (keyZonesResponse == null) {
            return;
        }
        List<KeyZone> rows = keyZonesResponse.getRows();
        this.keyZoneList = rows;
        if (rows.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.keyZoneList.size(); i++) {
            int sensitivity = this.keyZoneList.get(i).getSensitivity();
            int zoneNum = this.keyZoneList.get(i).getZoneNum();
            if (zoneNum == 1) {
                this.key1Sensitivity.setValue(getSensitivityString(sensitivity));
            } else if (zoneNum == 2) {
                this.key2Sensitivity.setValue(getSensitivityString(sensitivity));
            } else if (zoneNum == 3) {
                this.key3Sensitivity.setValue(getSensitivityString(sensitivity));
            } else {
                this.key4Sensitivity.setValue(getSensitivityString(sensitivity));
            }
        }
    }

    private String getSensitivityString(int s) {
        if (s == 1) {
            return getContext().getString(R.string.low);
        }
        if (s == 2) {
            return getContext().getString(R.string.medium);
        }
        if (s == 3) {
            return getContext().getString(R.string.high);
        }
        return getContext().getString(R.string.medium);
    }

    public void setSensitivity(final int keyPos, final int num, final IAction<Boolean> iAction) {
        if (keyPos == 1) {
            this.key1Sensitivity.setValue(getSensitivityString(num));
        } else if (keyPos == 2) {
            this.key2Sensitivity.setValue(getSensitivityString(num));
        } else if (keyPos == 3) {
            this.key3Sensitivity.setValue(getSensitivityString(num));
        } else if (keyPos == 4) {
            this.key4Sensitivity.setValue(getSensitivityString(num));
        }
        this.mCmdAssistant.setKnobSensitivity(getContext(), 1 << keyPos, num, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(aBoolean);
                }
                int i = keyPos + 1;
                if (aBoolean.booleanValue()) {
                    if (ActGqxSettingVM.this.keyZoneList != null && !ActGqxSettingVM.this.keyZoneList.isEmpty()) {
                        for (int i2 = 0; i2 < ActGqxSettingVM.this.keyZoneList.size(); i2++) {
                            KeyZone keyZone = (KeyZone) ActGqxSettingVM.this.keyZoneList.get(i2);
                            if (keyZone.getZoneNum() == i) {
                                keyZone.setSensitivity(num);
                                ActGqxSettingVM.this.keyZoneList.set(i2, keyZone);
                                ActGqxSettingVM.this.editZone(keyZone);
                                return;
                            }
                        }
                        ArrayList arrayList = new ArrayList();
                        KeyZone keyZone2 = new KeyZone();
                        keyZone2.setSensitivity(num);
                        keyZone2.setZoneNum(i);
                        arrayList.add(keyZone2);
                        ActGqxSettingVM.this.addNewZone(arrayList);
                        return;
                    }
                    ActGqxSettingVM.this.keyZoneList = new ArrayList();
                    KeyZone keyZone3 = new KeyZone();
                    keyZone3.setSensitivity(num);
                    keyZone3.setZoneNum(i);
                    ActGqxSettingVM.this.keyZoneList.add(keyZone3);
                    ActGqxSettingVM actGqxSettingVM = ActGqxSettingVM.this;
                    actGqxSettingVM.addNewZone(actGqxSettingVM.keyZoneList);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void editZone(KeyZone zone) {
        ((ObservableSubscribeProxy) Injection.net().editKeyZone(zone).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqxSettingVM.this.lambda$editZone$2(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editZone$2(Object obj) throws Exception {
        getKeySensitivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addNewZone(List<KeyZone> keyZoneList) {
        ((ObservableSubscribeProxy) Injection.net().keyZonesSet(this.controlDevice.getValue().getDeviceId(), keyZoneList).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqxSettingVM.this.lambda$addNewZone$3(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addNewZone$3(Object obj) throws Exception {
        getKeySensitivity();
    }

    private void updateParamExt(String json, final Device controlObjectDevice) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(controlObjectDevice.getDeviceId(), json).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Injection.repo().device().saveDevice(Device.this);
            }
        }, new SmartErrorComsumer());
    }

    public void refreshRelateInfoList() {
        this.isShowBindKRange.setValue(Boolean.valueOf(ComboCmdHelper.getInstance().isShowKRange()));
    }

    public void clickAdjustKRange(Context context, boolean isKnobPanel) {
        Group groupByGroupId;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 4; i++) {
            List<KeyInfo> keysByZone = ComboCmdHelper.getInstance().getKeysByZone(1 << i);
            SettingCmdParam.KInfo kInfo = new SettingCmdParam.KInfo();
            if (!keysByZone.isEmpty()) {
                for (KeyInfo keyInfo : keysByZone) {
                    if (keyInfo != null) {
                        if (keyInfo.getObjectType() == 1) {
                            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(keyInfo.getObjectId());
                            if (deviceByDeviceId != null && (isKnobPanel || ComboCmdHelper.getInstance().parseCmdForAction(deviceByDeviceId, keyInfo.getInstruction()).contains(getContext().getString(R.string.type_ct)))) {
                                kInfo.setMinK(deviceByDeviceId.getMinkelvin());
                                kInfo.setMaxK(deviceByDeviceId.getMaxkelvin());
                                break;
                            }
                        } else if (keyInfo.getObjectType() == 2 && (groupByGroupId = Injection.repo().group().getGroupByGroupId(keyInfo.getObjectId())) != null && (isKnobPanel || ComboCmdHelper.getInstance().parseCmdForAction(groupByGroupId, keyInfo.getInstruction()).contains(getContext().getString(R.string.type_ct)))) {
                            kInfo.setMinK(groupByGroupId.getMinkelvin());
                            kInfo.setMaxK(groupByGroupId.getMaxkelvin());
                            break;
                        }
                    }
                }
            }
            arrayList.add(kInfo);
        }
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_process));
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), new int[0]).setKInfo(context, 15, arrayList, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActGqxSettingVM.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_succee));
                } else {
                    ActGqxSettingVM.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_fail));
                }
            }
        });
    }

    public void setBuzzerEnable(final boolean z, final IAction<Boolean> iAction) {
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).setBuzzerState(getContext(), 1, z ? 1 : 0, new IAction() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGqxSettingVM.this.lambda$setBuzzerEnable$5(iAction, z, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBuzzerEnable$5(IAction iAction, boolean z, ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            if (iAction != null) {
                iAction.act(false);
                return;
            }
            return;
        }
        if (iAction != null) {
            iAction.act(true);
        }
        Device value = this.controlDevice.getValue();
        try {
            JSONObject jSONObject = value.getExtParam() != null ? new JSONObject(value.getExtParam()) : new JSONObject();
            jSONObject.put(UpdateBackDataRequest.BUZZER_STATUS, z ? 1 : 0);
            value.setExtParam(jSONObject.toString());
            updateParamExt(jSONObject.toString(), value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryBuzzerState() {
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).queryBuzzerState(getContext(), 1, new IAction() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGqxSettingVM.this.lambda$queryBuzzerState$6((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryBuzzerState$6(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        this.showVoiceSetting.setValue(Boolean.valueOf(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) == 1));
    }
}