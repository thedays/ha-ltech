package com.ltech.smarthome.ui.device.setting;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.nfc.WriteVirtualHelper;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting;
import com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelColorSet;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.SmartPanelNightUpState;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.smart.product_agreement.parser.IPanelParser;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActSmartPanelSettingVM extends BaseDeviceSetViewModel {
    private SettingAssistant mCmdAssistant;
    public boolean needBrtButton;
    public SmartPanelNightUpState nightUpstate;
    public boolean showRestore;
    public SmartPanelSettingState state;
    public MutableLiveData<Boolean> switchOn = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> engravedText = new MutableLiveData<>();
    public MutableLiveData<Boolean> nightMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> distanceSetting = new MutableLiveData<>();
    public MutableLiveData<Boolean> distanceOn = new MutableLiveData<>();
    public MutableLiveData<Boolean> autoTurnOff = new MutableLiveData<>();
    public MutableLiveData<Boolean> memorizePowerOff = new MutableLiveData<>();
    public MutableLiveData<Boolean> showMore = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isOld = new MutableLiveData<>();
    public MutableLiveData<String> starTimeText = new MutableLiveData<>();
    public MutableLiveData<String> endTimeText = new MutableLiveData<>();
    public MutableLiveData<String> distanceText = new MutableLiveData<>("");
    public MutableLiveData<Integer> distanceValue = new MutableLiveData<>(25);
    public SingleLiveEvent<Void> selectStarTimeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> selectEndTimeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> elderlyModeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeLanguageEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> isShowIconVersion = new MutableLiveData<>();
    public MutableLiveData<Boolean> newIconVersion = new MutableLiveData<>();
    public MutableLiveData<String> iconVersion = new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowBindKRange = new MutableLiveData<>();
    public SingleLiveEvent<Void> adjustKRange = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> upgradeIconEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> isProPanel = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> changeDistanceEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> switchSettingEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> upNightEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> rvKeysShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> switchSettingShow = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> switchPositionSetting = new MutableLiveData<>(false);
    public MutableLiveData<Integer> languageEvent = new MutableLiveData<>();
    public MutableLiveData<Boolean> touchVolumeOpen = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda11
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSmartPanelSettingVM.this.lambda$new$0((View) obj);
        }
    });
    public boolean isFirst = true;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.btn_adjust /* 2131296487 */:
                this.adjustKRange.call();
                break;
            case R.id.layout_battery /* 2131297363 */:
                this.getBatteryEvent.call();
                break;
            case R.id.layout_brt_button /* 2131297380 */:
                NavUtils.destination(ActBrtButtonSetting.class).withLong("device_id", this.controlDevice.getValue().getDeviceId()).withRequestCode(5010).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_change_language /* 2131297390 */:
                this.changeLanguageEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_create_group /* 2131297408 */:
                this.showCreateGroupDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_elderly_mode /* 2131297459 */:
                this.elderlyModeEvent.call();
                break;
            case R.id.layout_end_time /* 2131297463 */:
                this.selectEndTimeDialogEvent.call();
                break;
            case R.id.layout_get_up_night_mode /* 2131297482 */:
                this.upNightEvent.call();
                break;
            case R.id.layout_icon_upgrade /* 2131297492 */:
                if (Boolean.TRUE.equals(this.newIconVersion.getValue())) {
                    this.upgradeIconEvent.call();
                    break;
                }
                break;
            case R.id.layout_panel_back /* 2131297566 */:
                navigation(NavUtils.destination(ActSmartPanelColorSet.class).withLong(Constants.CONTROL_ID, this.controlDevice.getValue().getId()).withDefaultRequestCode());
                break;
            case R.id.layout_sensitivity /* 2131297628 */:
                this.changeDistanceEvent.call();
                break;
            case R.id.layout_start_time /* 2131297657 */:
                this.selectStarTimeDialogEvent.call();
                break;
            case R.id.layout_switch_position_setting /* 2131297672 */:
                navigation(NavUtils.destination(ActSmartPanelSwitchPositionSet.class).withLong(Constants.PLACE_ID, getCurrentPlace().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, false));
                break;
            case R.id.layout_switch_setting /* 2131297673 */:
                this.switchSettingEvent.call();
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

    public boolean isNeedUpgrade() {
        return SharedPreferenceUtil.queryBooleanValue(Constants.FIRM_NEED_UPGRADE);
    }

    public void initRelateInfoList(Object object) {
        RelateInfoUtils.initRelateInfoList(object);
        RelateInfoAssistant relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        this.showRestore = WriteVirtualHelper.instance().isSupportNfc(RelateInfoUtils.relateInfoAssistant.getExtParam().getBinName());
        for (int i = 0; i < RelateInfoUtils.relatedInfoList.size(); i++) {
            RelatedInfoExtParam.RelateInfo relateLongClickInfo = relateInfoAssistant.getRelateLongClickInfo(i);
            if (relateLongClickInfo != null) {
                boolean z = true;
                if (relateLongClickInfo.action != 1 && relateLongClickInfo.action != 2 && relateLongClickInfo.action != 3) {
                    z = false;
                }
                this.needBrtButton = z;
                if (z) {
                    return;
                }
            }
        }
    }

    public void setNightMode(boolean b2) {
        if (this.state != null) {
            this.nightMode.setValue(Boolean.valueOf(b2));
            this.state.setNightMode(b2 ? this.isProPanel.getValue().booleanValue() ? 2 : 1 : 0);
            setting();
        }
    }

    public void setAutoTurnOff(boolean b2) {
        if (this.state != null) {
            this.autoTurnOff.setValue(Boolean.valueOf(b2));
            if (Boolean.TRUE.equals(this.nightMode.getValue())) {
                this.state.setNightMode(b2 ? 2 : 1);
            } else {
                this.state.setNightMode(b2 ? 2 : 0);
            }
            setting();
        }
    }

    public void setEngravedText(boolean z) {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            smartPanelSettingState.setEngravedTextMode(z ? 1 : 0);
            setting();
        }
    }

    public void setStarTime(int h, int m2) {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            int endH = ((smartPanelSettingState.getEndH() * 60) + this.state.getEndM()) - ((h * 60) + m2);
            if (endH > 0 && endH < 30) {
                SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 30));
                return;
            }
            this.starTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
            this.state.setStartH(h);
            this.state.setStartM(m2);
            setting();
        }
    }

    public void setEndTime(int h, int m2) {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            int startH = ((h * 60) + m2) - ((smartPanelSettingState.getStartH() * 60) + this.state.getStartM());
            if (startH > 0 && startH < 30) {
                SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 30));
                return;
            }
            this.endTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
            this.state.setEndH(h);
            this.state.setEndM(m2);
            setting();
        }
    }

    public void setpowerOffStatus(final boolean b2) {
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).setOnState(ActivityUtils.getTopActivity(), b2 ? 3 : 2, 0, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSettingVM.this.lambda$setpowerOffStatus$1(b2, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setpowerOffStatus$1(boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.POWER_STATUS, CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).setOnState(z ? 3 : 2, 0, 0));
        }
    }

    private void setting() {
        this.mCmdAssistant.setSmartPanelMode(ActivityUtils.getTopActivity(), this.state.getDoubleLight(), this.state.getReverseLight(), this.state.getNightMode(), this.state.getEngravedTextMode(), this.state.getStartH(), this.state.getStartM(), this.state.getEndH(), this.state.getEndM(), new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSettingVM.this.lambda$setting$2((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setting$2(Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.NIGHT_MODE, this.mCmdAssistant.setSmartPanelMode(this.state.getDoubleLight(), this.state.getReverseLight(), this.state.getNightMode(), this.state.getEngravedTextMode(), this.state.getStartH(), this.state.getStartM(), this.state.getEndH(), this.state.getEndM()));
        }
    }

    public void loadDeviceStatus(final Device device) {
        if (this.isFirst) {
            this.isFirst = false;
            this.mCmdAssistant = CmdAssistant.getSettingCmdAssistant(device, new int[0]);
            if (ProductId.ID_SMART_PANEL_S6B.equals(device.getProductId())) {
                return;
            }
            CmdAssistant.getQueryCmdAssistant(device, new int[0]).querySmartPanelSetting(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM.1
                @Override // com.ltech.smarthome.base.IAction
                public void act(ResponseMsg msg) {
                    ActSmartPanelSettingVM.this.dismissLoadingDialog();
                    if (msg == null) {
                        return;
                    }
                    BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                    ActSmartPanelSettingVM.this.state = ((IPanelParser) Injection.strategy().getParserStrategy(msg.getAgreementId())).parserSmartPanelSettingState(msg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, msg.getResData());
                    ActSmartPanelSettingVM.this.showStateData();
                    ActSmartPanelSettingVM.this.queryState();
                }
            });
        }
    }

    public void showStateData() {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            this.engravedText.setValue(Boolean.valueOf(smartPanelSettingState.getEngravedTextMode() == 1));
            if (this.isProPanel.getValue().booleanValue()) {
                this.nightMode.setValue(Boolean.valueOf(this.state.getNightMode() == 2));
            } else {
                this.nightMode.setValue(Boolean.valueOf(this.state.getNightMode() != 0));
            }
            this.showMore.setValue(true);
            this.isOld.setValue(Boolean.valueOf(this.state.isOld()));
            this.starTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(this.state.getStartH()), Integer.valueOf(this.state.getStartM())));
            this.endTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(this.state.getEndH()), Integer.valueOf(this.state.getEndM())));
            this.distanceOn.setValue(Boolean.valueOf(this.state.getSensingDistanceSwitch() == 1));
            this.languageEvent.setValue(Integer.valueOf(this.state.getLanguage()));
            int sensingDistance = this.state.getSensingDistance();
            if (sensingDistance == 0) {
                this.distanceValue.setValue(25);
                return;
            }
            if (sensingDistance == 1) {
                this.distanceValue.setValue(50);
            } else if (sensingDistance == 2) {
                this.distanceValue.setValue(75);
            } else {
                if (sensingDistance != 3) {
                    return;
                }
                this.distanceValue.setValue(100);
            }
        }
    }

    public void queryState() {
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).queryOnState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSettingVM.this.lambda$queryState$3((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$3(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 18) {
                this.showMore.setValue(true);
                SelectPowerOnStateDialog.generateContentList();
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
                if (parseInt == 1 || parseInt == 2) {
                    this.memorizePowerOff.setValue(false);
                } else if (parseInt == 3) {
                    this.memorizePowerOff.setValue(true);
                }
            }
            Device value = this.controlDevice.getValue();
            if (value != null && (value.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || value.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || value.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) || value.getProductId().equals(ProductId.ID_SMART_PANEL_G4) || value.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO))) {
                checkIconVersion();
            }
            if (ProductId.ID_SMART_PANEL_G4TE.equals(value.getProductId())) {
                queryBuzzerState(value);
            } else {
                queryPanelNightUpState(value);
            }
        }
    }

    public void uploadDataLanguage(int language) {
        Device value = this.controlDevice.getValue();
        try {
            JSONObject jSONObject = value.getExtParam() != null ? new JSONObject(value.getExtParam()) : new JSONObject();
            jSONObject.put("switchScreenLanguage", language);
            value.setExtParam(jSONObject.toString());
            updateParamExt(jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateParamExt(String json) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getValue().getDeviceId(), json).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSettingVM.this.lambda$updateParamExt$4(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$4(Object obj) throws Exception {
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
    }

    public void checkIconVersion() {
        final Device value = this.controlDevice.getValue();
        if (value != null) {
            CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryScreenPanelIconStart(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelSettingVM.this.lambda$checkIconVersion$5(value, (ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkIconVersion$5(Device device, ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData().length() < 20) {
            this.isShowIconVersion.setValue(false);
            return;
        }
        BleParam bleParam = (BleParam) device.getParam(BleParam.class);
        if (Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) != (bleParam != null ? bleParam.getUnicastAddress() : 0)) {
            return;
        }
        this.iconVersion.setValue(String.format(Locale.US, "V%.1f", Float.valueOf(ScreenIconUtils.getVersionNum(Integer.parseInt(responseMsg.getResData().substring(16, 20), 16)))));
        this.isShowIconVersion.setValue(true);
        if (this.iconVersion.getValue().equals(ScreenIconUtils.LATEST_VERSION)) {
            this.newIconVersion.setValue(false);
        } else {
            this.newIconVersion.setValue(true);
        }
    }

    public int getStarH() {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            return smartPanelSettingState.getStartH();
        }
        return 0;
    }

    public int getStarM() {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            return smartPanelSettingState.getStartM();
        }
        return 0;
    }

    public int getEndH() {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            return smartPanelSettingState.getEndH();
        }
        return 0;
    }

    public int getEndM() {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            return smartPanelSettingState.getEndM();
        }
        return 0;
    }

    public void changeSensingDistance() {
        final int i;
        int intValue;
        if (this.distanceValue.getValue() != null && (intValue = this.distanceValue.getValue().intValue()) != 25) {
            if (intValue == 50) {
                i = 1;
            } else if (intValue == 75) {
                i = 2;
            } else if (intValue == 100) {
                i = 3;
            }
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
            CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).setSensitivity(ActivityUtils.getTopActivity(), i, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelSettingVM.this.lambda$changeSensingDistance$6(i, (Boolean) obj);
                }
            });
        }
        i = 0;
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).setSensitivity(ActivityUtils.getTopActivity(), i, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSettingVM.this.lambda$changeSensingDistance$6(i, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeSensingDistance$6(int i, Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            if (this.controlDevice.getValue() != null) {
                ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.SENSING_DISTANCE, CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).setSensitivity(i));
            }
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_success));
            return;
        }
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_failed));
    }

    public void setSensingDistanceOn(final boolean isChecked) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).setWaveEnable(ActivityUtils.getTopActivity(), isChecked, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSettingVM.this.lambda$setSensingDistanceOn$7(isChecked, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensingDistanceOn$7(boolean z, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg == null || responseMsg.getStateCode() == 3) {
            SmartToast.showShort(StringUtils.getString(R.string.save_fail));
        } else if (this.controlDevice.getValue() != null) {
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.SENSING_DISTANCE_ENABLED, CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).setWaveEnable(z));
        }
    }

    public void queryPanelNightUpState(final Device device) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryPanelUpNightState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSettingVM.this.lambda$queryPanelNightUpState$8(device, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPanelNightUpState$8(Device device, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            refreshPanelNightUpState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserPanelNightUpState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, responseMsg.getResData()));
        }
    }

    public void refreshPanelNightUpState(SmartPanelNightUpState smartPanelNightUpState) {
        if (smartPanelNightUpState == null) {
            this.nightUpstate = new SmartPanelNightUpState();
        } else {
            this.nightUpstate = smartPanelNightUpState;
        }
        this.switchOn.setValue(Boolean.valueOf(this.nightUpstate.getState() == 1));
    }

    private void queryBuzzerState(Device device) {
        CmdAssistant.getLightCmdAssistant(device, new int[0]).queryBuzzerState(ActivityUtils.getTopActivity(), 1, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSettingVM.this.lambda$queryBuzzerState$9((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryBuzzerState$9(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        this.touchVolumeOpen.setValue(Boolean.valueOf(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) == 1));
    }

    public void uploadPanelColor(final int color) {
        final Device value = this.controlDevice.getValue();
        ((ObservableSubscribeProxy) Injection.net().updatePanelColor(value.getDeviceId(), color).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSettingVM.this.lambda$uploadPanelColor$10((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSmartPanelSettingVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSettingVM.this.lambda$uploadPanelColor$11(value, color, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadPanelColor$10(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadPanelColor$11(Device device, int i, Object obj) throws Exception {
        device.setPanelColor(i + "");
        Injection.repo().device().saveDevice(device);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BACK_COLOR, i);
        finishActivity(5009, bundle);
    }
}