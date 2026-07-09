package com.ltech.smarthome.ui.device.r8;

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
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant;
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
public class ActR8SettingVM extends BaseDeviceSetViewModel {
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
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.r8.ActR8SettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActR8SettingVM.this.lambda$new$0((View) obj);
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
                        this.batteryNum.setValue(Integer.valueOf(new JSONObject(value.getExtParam()).optInt("battery", 100)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.mCmdAssistant = CmdAssistant.getDeviceAssistant(device, new int[0]);
        }
    }

    private void updateParamExt(String json, final Device controlObjectDevice) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(controlObjectDevice.getDeviceId(), json).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActR8SettingVM$$ExternalSyntheticLambda1
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
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), new int[0]).setKInfo(context, 15, arrayList, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.r8.ActR8SettingVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActR8SettingVM.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_succee));
                } else {
                    ActR8SettingVM.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_fail));
                }
            }
        });
    }
}