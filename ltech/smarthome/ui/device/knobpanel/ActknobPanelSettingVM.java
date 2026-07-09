package com.ltech.smarthome.ui.device.knobpanel;

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
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.smart.product_agreement.parser.IPanelParser;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActknobPanelSettingVM extends BaseDeviceSetViewModel {
    private SettingAssistant mCmdAssistant;
    private SmartPanelSettingState state;
    public MutableLiveData<Boolean> memorizeMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> showMore = new MutableLiveData<>(false);
    public MutableLiveData<Integer> sensitivity = new MutableLiveData<>(0);
    public SingleLiveEvent<Void> showSensitivityDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showModeOrderDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> isProPanel = new MutableLiveData<>();
    public MutableLiveData<Boolean> engravedText = new MutableLiveData<>();
    public MutableLiveData<Boolean> nightMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> autoTurnOff = new MutableLiveData<>();
    public MutableLiveData<Boolean> isOld = new MutableLiveData<>();
    public MutableLiveData<String> starTimeText = new MutableLiveData<>();
    public MutableLiveData<String> endTimeText = new MutableLiveData<>();
    public SingleLiveEvent<Void> selectStarTimeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> selectEndTimeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> elderlyModeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeLanguageEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> isShowIconVersion = new MutableLiveData<>();
    public MutableLiveData<Boolean> newIconVersion = new MutableLiveData<>();
    public MutableLiveData<String> iconVersion = new MutableLiveData<>();
    public SingleLiveEvent<Void> upgradeIconEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> adjustKRange = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> isShowBindKRange = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM$$ExternalSyntheticLambda2
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActknobPanelSettingVM.this.lambda$new$0((View) obj);
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
            case R.id.layout_end_time_knob /* 2131297464 */:
                this.selectEndTimeDialogEvent.call();
                break;
            case R.id.layout_icon_upgrade /* 2131297492 */:
                if (Boolean.TRUE.equals(this.newIconVersion.getValue())) {
                    this.upgradeIconEvent.call();
                    break;
                }
                break;
            case R.id.layout_mode_order /* 2131297540 */:
                this.showModeOrderDialogEvent.call();
                break;
            case R.id.layout_sensitivity /* 2131297628 */:
                this.showSensitivityDialogEvent.call();
                break;
            case R.id.layout_start_time_knob /* 2131297658 */:
                this.selectStarTimeDialogEvent.call();
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

    public void loadDeviceStatus(final Device device) {
        if (this.isFirst) {
            this.isFirst = false;
            this.mCmdAssistant = CmdAssistant.getSettingCmdAssistant(device, new int[0]);
            CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryKnobPanelSetting(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM.1
                @Override // com.ltech.smarthome.base.IAction
                public void act(ResponseMsg msg) {
                    ActknobPanelSettingVM.this.dismissLoadingDialog();
                    if (msg == null) {
                        return;
                    }
                    BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                    if (Integer.parseInt(msg.getResData().substring(6, 10), 16) != (bleParam != null ? bleParam.getUnicastAddress() : 0)) {
                        return;
                    }
                    ActknobPanelSettingVM.this.sensitivity.setValue(Integer.valueOf(Integer.parseInt(msg.getResData().substring(16, 18), 16)));
                    for (int i = 0; i < ActknobPanelSettingVM.this.orderArray.length; i++) {
                        int i2 = i * 2;
                        ActknobPanelSettingVM.this.orderArray[i] = Integer.parseInt(msg.getResData().substring(18 + i2, i2 + 20), 16);
                    }
                    if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                        ActknobPanelSettingVM.this.loadMoreDeviceStatus(device);
                    } else {
                        ActknobPanelSettingVM.this.queryMemoryState();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMoreDeviceStatus(final Device device) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).querySmartPanelSetting(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                if (msg == null) {
                    return;
                }
                BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                ActknobPanelSettingVM.this.state = ((IPanelParser) Injection.strategy().getParserStrategy(msg.getAgreementId())).parserSmartPanelSettingState(msg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, msg.getResData());
                if (ActknobPanelSettingVM.this.state != null) {
                    ActknobPanelSettingVM.this.engravedText.setValue(Boolean.valueOf(ActknobPanelSettingVM.this.state.getEngravedTextMode() == 1));
                    if (ActknobPanelSettingVM.this.state.getEngravedTextMode() == 1) {
                        ActknobPanelSettingVM.this.setEngravedText(false);
                    }
                    ActknobPanelSettingVM.this.nightMode.setValue(Boolean.valueOf(ActknobPanelSettingVM.this.state.getNightMode() == 1 || ActknobPanelSettingVM.this.state.getNightMode() == 2));
                    ActknobPanelSettingVM.this.autoTurnOff.setValue(Boolean.valueOf(ActknobPanelSettingVM.this.state.getNightMode() == 2));
                    ActknobPanelSettingVM.this.showMore.setValue(true);
                    ActknobPanelSettingVM.this.isOld.setValue(Boolean.valueOf(ActknobPanelSettingVM.this.state.isOld()));
                    ActknobPanelSettingVM.this.starTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(ActknobPanelSettingVM.this.state.getStartH()), Integer.valueOf(ActknobPanelSettingVM.this.state.getStartM())));
                    ActknobPanelSettingVM.this.endTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(ActknobPanelSettingVM.this.state.getEndH()), Integer.valueOf(ActknobPanelSettingVM.this.state.getEndM())));
                    ActknobPanelSettingVM.this.checkIconVersion();
                }
            }
        });
    }

    public void checkIconVersion() {
        final Device value = this.controlDevice.getValue();
        if (value != null) {
            CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryScreenPanelIconStart(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActknobPanelSettingVM.this.lambda$checkIconVersion$1(value, (ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkIconVersion$1(Device device, ResponseMsg responseMsg) {
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
        queryMemoryState();
    }

    public void setEngravedText(boolean z) {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            smartPanelSettingState.setEngravedTextMode(z ? 1 : 0);
            setting();
        }
    }

    public void setNightMode(boolean z) {
        if (this.state != null) {
            this.nightMode.setValue(Boolean.valueOf(z));
            this.state.setNightMode(z ? 1 : 0);
            setting();
        }
    }

    public void setAutoTurnOff(boolean b2) {
        if (this.state != null) {
            this.autoTurnOff.setValue(Boolean.valueOf(b2));
            if (this.nightMode.getValue().booleanValue()) {
                this.state.setNightMode(b2 ? 2 : 1);
            } else {
                this.state.setNightMode(b2 ? 2 : 0);
            }
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

    private void setting() {
        this.mCmdAssistant.setSmartPanelMode(ActivityUtils.getTopActivity(), this.state.getDoubleLight(), this.state.getReverseLight(), this.state.getNightMode(), this.state.getEngravedTextMode(), this.state.getStartH(), this.state.getStartM(), this.state.getEndH(), this.state.getEndM(), new IAction<Boolean>(this) { // from class: com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                aBoolean.booleanValue();
            }
        });
    }

    public void queryMemoryState() {
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).queryOnState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActknobPanelSettingVM.this.lambda$queryMemoryState$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryMemoryState$2(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        if (Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) == 3) {
            this.memorizeMode.setValue(true);
        } else {
            this.memorizeMode.setValue(false);
        }
    }

    public void setPowerOffStatus(final SwitchButton view, final boolean b2, final ImageTipDialog dialog) {
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).setOnState(ActivityUtils.getTopActivity(), b2 ? 3 : 2, 0, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActknobPanelSettingVM.this.lambda$setPowerOffStatus$3(dialog, b2, view, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPowerOffStatus$3(ImageTipDialog imageTipDialog, boolean z, SwitchButton switchButton, Boolean bool) {
        if (imageTipDialog != null) {
            imageTipDialog.dismissDialog();
        }
        if (!bool.booleanValue()) {
            switchButton.setCheckedNotByUser(!z);
        } else if (imageTipDialog != null) {
            uploadPowerOffStatus(Boolean.valueOf(z));
        }
    }

    public void uploadPowerOffStatus(Boolean status) {
        Device value = this.controlDevice.getValue();
        try {
            JSONObject jSONObject = value.getExtParam() != null ? new JSONObject(value.getExtParam()) : new JSONObject();
            jSONObject.put("switchPowerMemory", status.booleanValue() ? 3 : 0);
            value.setExtParam(jSONObject.toString());
            updateParamExt(jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateParamExt(String json) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getValue().getDeviceId(), json).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActknobPanelSettingVM.this.lambda$updateParamExt$4(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$4(Object obj) throws Exception {
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
    }
}