package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSuperPanelSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.device_param.SuperPanelBleParam;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.net.response.super_panel.QuerySuperPanelVoiceControlRangeResponse;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.upgrade.ActSpUpgrade;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSuperPanelSetting extends BaseDeviceSetActivity<ActSuperPanelSettingBinding, ActSuperPanelSettingVM> {
    private int selectHour;
    private int selectMin;
    public SuperPanelExtParam superPanelExtParam;
    private List<Long> deviceIdList = new ArrayList();
    private List<Long> groupIdList = new ArrayList();
    private List<Long> sceneIdList = new ArrayList();
    private int voiceControlType = 1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
        this.superPanelExtParam = new SuperPanelExtParam();
        ((ActSuperPanelSettingBinding) this.mViewBinding).sbEngravedText.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).engravedText.setValue(Boolean.valueOf(z));
                ActSuperPanelSetting.this.superPanelExtParam.setSwitchWordMode(z ? 1 : 0);
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).updateSuperPanelExt(ActSuperPanelSetting.this.superPanelExtParam);
            }
        });
        ((ActSuperPanelSettingBinding) this.mViewBinding).sbNightMode.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.2
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).nightMode.setValue(Boolean.valueOf(z));
                ActSuperPanelSetting.this.superPanelExtParam.setSwitchNightMode(z ? 1 : 0);
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).updateSuperPanelExt(ActSuperPanelSetting.this.superPanelExtParam);
            }
        });
        ((ActSuperPanelSettingBinding) this.mViewBinding).sbAutoUpgrade.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.3
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).autoUpgrade.setValue(Boolean.valueOf(z));
                ActSuperPanelSetting.this.superPanelExtParam.setApkAutoUpdate(z ? 1 : 0);
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).updateSuperPanelExt(ActSuperPanelSetting.this.superPanelExtParam);
            }
        });
        ((ActSuperPanelSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.4
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).memorizePowerOff.setValue(Boolean.valueOf(z));
                ActSuperPanelSetting.this.superPanelExtParam.setSwitch_memory(z ? 1 : 0);
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).updateSuperPanelExt(ActSuperPanelSetting.this.superPanelExtParam);
            }
        });
        ((ActSuperPanelSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSuperPanelSetting.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActSuperPanelSettingVM) this.mViewModel).isChinaNode.setValue(Boolean.valueOf(ApiConstants.isChinaNode()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.layout_application_upgrade /* 2131297354 */:
                NavUtils.destination(ActSpUpgrade.class).withInt(Constants.COMMAND_TYPE, 1).withLong(Constants.CONTROL_ID, ((ActSuperPanelSettingVM) this.mViewModel).controlDevice.getValue().getId()).withString("version", ((ActSuperPanelSettingVM) this.mViewModel).mcuversion.getValue()).withString(Constants.NEW_VERSION, ((ActSuperPanelSettingVM) this.mViewModel).lastMcuVersion).navigation(this);
                return;
            case R.id.layout_end_time /* 2131297463 */:
                editNightTime(false);
                return;
            case R.id.layout_firmware_upgrade /* 2131297474 */:
                break;
            case R.id.layout_log_upload /* 2131297527 */:
                showUploadLogDialog();
                return;
            case R.id.layout_start_time /* 2131297657 */:
                editNightTime(true);
                return;
            case R.id.layout_switch_1 /* 2131297668 */:
                SuperPanelExtParam superPanelExtParam = this.superPanelExtParam;
                if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch1_name())) {
                    editSwitchName(this.superPanelExtParam.getSwitch1_name(), 1);
                    return;
                } else {
                    editSwitchName(NameRepository.getSmartPanelS4KeyName(this)[0], 1);
                    return;
                }
            case R.id.layout_switch_2 /* 2131297669 */:
                SuperPanelExtParam superPanelExtParam2 = this.superPanelExtParam;
                if (superPanelExtParam2 != null && !TextUtils.isEmpty(superPanelExtParam2.getSwitch2_name())) {
                    editSwitchName(this.superPanelExtParam.getSwitch2_name(), 2);
                    return;
                } else {
                    editSwitchName(NameRepository.getSmartPanelS4KeyName(this)[1], 2);
                    return;
                }
            case R.id.layout_switch_3 /* 2131297670 */:
                SuperPanelExtParam superPanelExtParam3 = this.superPanelExtParam;
                if (superPanelExtParam3 != null && !TextUtils.isEmpty(superPanelExtParam3.getSwitch3_name())) {
                    editSwitchName(this.superPanelExtParam.getSwitch3_name(), 3);
                    return;
                } else {
                    editSwitchName(NameRepository.getSmartPanelS4KeyName(this)[2], 3);
                    return;
                }
            case R.id.layout_switch_4 /* 2131297671 */:
                SuperPanelExtParam superPanelExtParam4 = this.superPanelExtParam;
                if (superPanelExtParam4 != null && !TextUtils.isEmpty(superPanelExtParam4.getSwitch4_name())) {
                    editSwitchName(this.superPanelExtParam.getSwitch4_name(), 4);
                    return;
                } else {
                    editSwitchName(NameRepository.getSmartPanelS4KeyName(this)[3], 4);
                    return;
                }
            case R.id.rl_serial_number /* 2131298041 */:
                ((ActSuperPanelSettingVM) this.mViewModel).copyText(((ActSuperPanelSettingBinding) this.mViewBinding).tvSerialNumber.getText().toString());
                break;
            default:
                return;
        }
        NavUtils.destination(ActSpUpgrade.class).withInt(Constants.COMMAND_TYPE, 0).withLong(Constants.CONTROL_ID, ((ActSuperPanelSettingVM) this.mViewModel).controlDevice.getValue().getId()).withString("version", ((ActSuperPanelSettingVM) this.mViewModel).firmwareversion.getValue()).withString(Constants.NEW_VERSION, ((ActSuperPanelSettingVM) this.mViewModel).lastFirmwareVersion).navigation(this);
    }

    private void showUploadLogDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.app_str_upload_device_log), getString(R.string.app_str_upload_device_log_tip)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.app_str_upload_log), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUploadLogDialog$1;
                lambda$showUploadLogDialog$1 = ActSuperPanelSetting.this.lambda$showUploadLogDialog$1(baseDialog, view);
                return lambda$showUploadLogDialog$1;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUploadLogDialog$1(BaseDialog baseDialog, View view) {
        ((ActSuperPanelSettingVM) this.mViewModel).uploadLog();
        return false;
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSuperPanelSettingBinding) this.mViewBinding).tvSerialNumber.setText(getIntent().getStringExtra(Constants.SERIAL_NUMBER));
        ((ActSuperPanelSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActSuperPanelSettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelSetting.this.lambda$startObserve$2((Device) obj);
            }
        });
        ((ActSuperPanelSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelSetting.this.lambda$startObserve$4((Device) obj);
            }
        });
        ((ActSuperPanelSettingVM) this.mViewModel).isContinous_mode.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).getCurrentPlace().isManager()) {
                    ((ActSuperPanelSettingBinding) ActSuperPanelSetting.this.mViewBinding).tvTalkState.setText(aBoolean.booleanValue() ? R.string.function_open : R.string.function_close);
                } else {
                    ((ActSuperPanelSettingBinding) ActSuperPanelSetting.this.mViewBinding).tvTalkState.setText(aBoolean.booleanValue() ? R.string.app_str_turned_on : R.string.app_str_turned_off);
                }
            }
        });
        ((ActSuperPanelSettingVM) this.mViewModel).isDirectVoice.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                ((ActSuperPanelSettingBinding) ActSuperPanelSetting.this.mViewBinding).layoutDirectVoice.setVisibility(0);
                if (((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).getCurrentPlace().isManager()) {
                    ((ActSuperPanelSettingBinding) ActSuperPanelSetting.this.mViewBinding).tvDirectVoiceState.setText(aBoolean.booleanValue() ? R.string.function_open : R.string.function_close);
                } else {
                    ((ActSuperPanelSettingBinding) ActSuperPanelSetting.this.mViewBinding).tvDirectVoiceState.setText(aBoolean.booleanValue() ? R.string.app_str_turned_on : R.string.app_str_turned_off);
                }
            }
        });
        ((ActSuperPanelSettingVM) this.mViewModel).isNearbyWakeup.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                ((ActSuperPanelSettingBinding) ActSuperPanelSetting.this.mViewBinding).layoutNearbyWakeup.setVisibility(0);
                if (((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).getCurrentPlace().isManager()) {
                    ((ActSuperPanelSettingBinding) ActSuperPanelSetting.this.mViewBinding).tvNearbyWakeupState.setText(aBoolean.booleanValue() ? R.string.function_open : R.string.function_close);
                } else {
                    ((ActSuperPanelSettingBinding) ActSuperPanelSetting.this.mViewBinding).tvNearbyWakeupState.setText(aBoolean.booleanValue() ? R.string.app_str_turned_on : R.string.app_str_turned_off);
                }
            }
        });
        ((ActSuperPanelSettingVM) this.mViewModel).setVoiceControlRangeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelSetting.this.lambda$startObserve$5((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        ((ActSuperPanelSettingVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(final Device device) {
        String floorName;
        final SuperPanelBleParam superPanelBleParam;
        if (ProductRepository.isDcaSuperPanel(device.getProductId())) {
            ((ActSuperPanelSettingBinding) this.mViewBinding).layoutSwitch.setVisibility(0);
            ((ActSuperPanelSettingBinding) this.mViewBinding).layoutMemorizePower.setVisibility(0);
            ((ActSuperPanelSettingBinding) this.mViewBinding).layoutMore.setVisibility(8);
            if (ApiConstants.isChinaNode() && device.getOauthtype() == 2) {
                ((ActSuperPanelSettingBinding) this.mViewBinding).layoutVoiceControlRange.setVisibility(0);
            } else {
                ((ActSuperPanelSettingBinding) this.mViewBinding).layoutVoiceControlRange.setVisibility(8);
            }
            if ((((ActSuperPanelSettingVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActSuperPanelSettingVM) this.mViewModel).getCurrentPlace().isManager()) && !ProductId.ID_ANDROID_SUPER_PANEL_MINI.equals(device.getProductId())) {
                ((ActSuperPanelSettingBinding) this.mViewBinding).layoutDeviceReplace.setVisibility(0);
            }
            if ((((ActSuperPanelSettingVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActSuperPanelSettingVM) this.mViewModel).getCurrentPlace().isManager()) && !ProductId.ID_ANDROID_SUPER_PANEL_MINI.equals(device.getProductId())) {
                ((ActSuperPanelSettingBinding) this.mViewBinding).layoutDeviceMcu.setVisibility(0);
            }
            String[] smartPanelS4KeyName = NameRepository.getSmartPanelS4KeyName(this);
            SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
            if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch1_name())) {
                smartPanelS4KeyName[0] = superPanelExtParam.getSwitch1_name();
            }
            if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch2_name())) {
                smartPanelS4KeyName[1] = superPanelExtParam.getSwitch2_name();
            }
            ((ActSuperPanelSettingBinding) this.mViewBinding).tvSwitchName1.setText(smartPanelS4KeyName[0]);
            ((ActSuperPanelSettingBinding) this.mViewBinding).tvSwitchName2.setText(smartPanelS4KeyName[1]);
            if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                ((ActSuperPanelSettingBinding) this.mViewBinding).layoutSwitch3.setVisibility(0);
                ((ActSuperPanelSettingBinding) this.mViewBinding).layoutSwitch4.setVisibility(0);
                ((ActSuperPanelSettingBinding) this.mViewBinding).layoutMore.setVisibility(0);
                if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch3_name())) {
                    smartPanelS4KeyName[2] = superPanelExtParam.getSwitch3_name();
                }
                if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch4_name())) {
                    smartPanelS4KeyName[3] = superPanelExtParam.getSwitch4_name();
                }
                ((ActSuperPanelSettingBinding) this.mViewBinding).tvSwitchName3.setText(smartPanelS4KeyName[2]);
                ((ActSuperPanelSettingBinding) this.mViewBinding).tvSwitchName4.setText(smartPanelS4KeyName[3]);
            } else if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                ((ActSuperPanelSettingBinding) this.mViewBinding).layoutMore.setVisibility(0);
            }
            ((ActSuperPanelSettingBinding) this.mViewBinding).layoutMore.setVisibility(0);
            queryVoiceControlRange(device);
        }
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        LHomeLog.i(ActSuperPanel.class, "controlId=" + ((ActSuperPanelSettingVM) this.mViewModel).controlId + "__device=" + device.toString() + "floor=" + floor + "__room=" + room);
        AppCompatTextView appCompatTextView = ((ActSuperPanelSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActSuperPanelSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        if (!TextUtils.isEmpty(device.getFirmwareversion())) {
            ((ActSuperPanelSettingVM) this.mViewModel).firmwareversion.setValue(device.getFirmwareversion());
            ((ActSuperPanelSettingVM) this.mViewModel).nextIcon.setValue(true);
        } else {
            ((ActSuperPanelSettingVM) this.mViewModel).newVersion.setValue(false);
            ((ActSuperPanelSettingVM) this.mViewModel).nextIcon.setValue(false);
            ((ActSuperPanelSettingBinding) this.mViewBinding).layoutFirmwareUpgrade.setVisibility(8);
        }
        if (TextUtils.isEmpty(device.getMcuversion())) {
            ((ActSuperPanelSettingVM) this.mViewModel).newVersion.setValue(false);
            ((ActSuperPanelSettingVM) this.mViewModel).nextIcon.setValue(false);
            ((ActSuperPanelSettingBinding) this.mViewBinding).layoutApplicationUpgrade.setVisibility(8);
        } else {
            ((ActSuperPanelSettingVM) this.mViewModel).mcuversion.setValue(device.getMcuversion());
            ((ActSuperPanelSettingVM) this.mViewModel).nextIcon.setValue(true);
        }
        if (device.getParam() != null && (superPanelBleParam = (SuperPanelBleParam) device.getParam(SuperPanelBleParam.class)) != null) {
            String currentZone = superPanelBleParam.getCurrentZone();
            if (superPanelBleParam.getTimeZone() == null || !superPanelBleParam.getTimeZone().equals(currentZone)) {
                superPanelBleParam.setTimeZone();
                ((ObservableSubscribeProxy) Injection.net().updateParam(device.getDeviceId(), GsonUtils.toJson(superPanelBleParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda6
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSuperPanelSetting.lambda$startObserve$3(Device.this, superPanelBleParam, obj);
                    }
                });
            }
        }
        if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
            ((ActSuperPanelSettingVM) this.mViewModel).getDeviceDetail(device.getDevicesn());
        }
        if (device.getExtParam() != null) {
            this.superPanelExtParam.fillMapWithString(device.getExtParam());
            SuperPanelExtParam superPanelExtParam2 = this.superPanelExtParam;
            ((ActSuperPanelSettingVM) this.mViewModel).autoUpgrade.setValue(Boolean.valueOf(superPanelExtParam2.getApkAutoUpdate() == 1));
            ((ActSuperPanelSettingVM) this.mViewModel).engravedText.setValue(Boolean.valueOf(superPanelExtParam2.getSwitchWordMode() == 1));
            if (superPanelExtParam2.getSwitchWordMode() == 1) {
                this.superPanelExtParam.setSwitchWordMode(0);
                ((ActSuperPanelSettingVM) this.mViewModel).updateSuperPanelExt(this.superPanelExtParam);
            }
            ((ActSuperPanelSettingVM) this.mViewModel).nightMode.setValue(Boolean.valueOf(superPanelExtParam2.getSwitchNightMode() == 1));
            if (superPanelExtParam2.getSwitchNightMode() == 1 && this.mViewBinding != 0) {
                ((ActSuperPanelSettingBinding) this.mViewBinding).tvStartTime.setText(String.format(Locale.US, "%02d:%02d", Integer.valueOf(superPanelExtParam2.getSwitchNightModeStartH()), Integer.valueOf(superPanelExtParam2.getSwitchNightModeStartM())));
                ((ActSuperPanelSettingBinding) this.mViewBinding).tvEndTime.setText(String.format(Locale.US, "%02d:%02d", Integer.valueOf(superPanelExtParam2.getSwitchNightModeEndH()), Integer.valueOf(superPanelExtParam2.getSwitchNightModeEndM())));
            }
            if (ProductRepository.isDcaSuperPanel(device.getProductId())) {
                ((ActSuperPanelSettingVM) this.mViewModel).isContinous_mode.setValue(Boolean.valueOf(superPanelExtParam2.getContinous_mode() == 1));
                ((ActSuperPanelSettingVM) this.mViewModel).isDirectVoice.setValue(Boolean.valueOf(superPanelExtParam2.getQuick_cmd() == 1));
                ((ActSuperPanelSettingVM) this.mViewModel).memorizePowerOff.setValue(Boolean.valueOf(superPanelExtParam2.getSwitch_memory() != 0));
            }
            if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                ((ActSuperPanelSettingVM) this.mViewModel).isNearbyWakeup.setValue(Boolean.valueOf(superPanelExtParam2.getNear_wake_up() == 1));
            }
        } else {
            if (ProductRepository.isDcaSuperPanel(device.getProductId())) {
                ((ActSuperPanelSettingVM) this.mViewModel).isContinous_mode.setValue(false);
                ((ActSuperPanelSettingVM) this.mViewModel).isDirectVoice.setValue(false);
                ((ActSuperPanelSettingVM) this.mViewModel).memorizePowerOff.setValue(true);
            }
            if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                ((ActSuperPanelSettingVM) this.mViewModel).isNearbyWakeup.setValue(false);
            }
        }
        Injection.limiter().reset(Injection.keyCreator().superPanelInfoKey(device.getDeviceId()));
        handleData(Injection.repo().device().getSuperPanelInfo(this, device.getDeviceId()), new IAction<List<SuperPanelInfo>>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.6
            @Override // com.ltech.smarthome.base.IAction
            public void act(List<SuperPanelInfo> superPanelInfos) {
                if (superPanelInfos == null || superPanelInfos.isEmpty()) {
                    return;
                }
                SuperPanelInfo superPanelInfo = superPanelInfos.get(0);
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).lastFirmwareVersion = superPanelInfo.getLastfirmwareversion();
                ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).lastMcuVersion = superPanelInfo.getLastmcuversion();
                if (((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).firmwareversion.getValue() != null) {
                    ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).newVersion.setValue(Boolean.valueOf(((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).lastFirmwareVersion.compareTo(((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).firmwareversion.getValue()) > 0));
                }
                if (((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).mcuversion.getValue() != null) {
                    ((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).newMucVersion.setValue(Boolean.valueOf(((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).lastMcuVersion.compareTo(((ActSuperPanelSettingVM) ActSuperPanelSetting.this.mViewModel).mcuversion.getValue()) > 0));
                }
            }
        });
    }

    static /* synthetic */ void lambda$startObserve$3(Device device, SuperPanelBleParam superPanelBleParam, Object obj) throws Exception {
        device.setParam(superPanelBleParam);
        Injection.repo().device().saveDevice(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r8) {
        long[] jArr = new long[this.deviceIdList.size()];
        int size = this.deviceIdList.size();
        for (int i = 0; i < size; i++) {
            jArr[i] = this.deviceIdList.get(i).longValue();
        }
        long[] jArr2 = new long[this.groupIdList.size()];
        int size2 = this.groupIdList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            jArr2[i2] = this.groupIdList.get(i2).longValue();
        }
        long[] jArr3 = new long[this.sceneIdList.size()];
        int size3 = this.sceneIdList.size();
        for (int i3 = 0; i3 < size3; i3++) {
            jArr3[i3] = this.sceneIdList.get(i3).longValue();
        }
        ((ActSuperPanelSettingVM) this.mViewModel).navigation(NavUtils.destination(ActSuperPanelVoiceControlRange.class).withLong(Constants.CONTROL_ID, ((ActSuperPanelSettingVM) this.mViewModel).controlDevice.getValue().getId()).withInt(Constants.VOICE_CONTROL_TYPE, this.voiceControlType).withLongArray(Constants.DEVICE_ID_ARRAY, jArr).withLongArray(Constants.GROUP_ID_ARRAY, jArr2).withLongArray(Constants.SCENE_ID_ARRAY, jArr3).withRequestCode(6003));
    }

    private void queryVoiceControlRange(Device device) {
        ((ObservableSubscribeProxy) Injection.net().querySuperPanelVoiceControlRange(device.getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelSetting.this.lambda$queryVoiceControlRange$6((QuerySuperPanelVoiceControlRangeResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting.10
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActSuperPanelSetting.this.changeVoiceControlState(1);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryVoiceControlRange$6(QuerySuperPanelVoiceControlRangeResponse querySuperPanelVoiceControlRangeResponse) throws Exception {
        this.deviceIdList = querySuperPanelVoiceControlRangeResponse.getDevices();
        this.groupIdList = querySuperPanelVoiceControlRangeResponse.getGroups();
        this.sceneIdList = querySuperPanelVoiceControlRangeResponse.getScenes();
        int type = querySuperPanelVoiceControlRangeResponse.getType();
        this.voiceControlType = type;
        changeVoiceControlState(type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeVoiceControlState(int voiceControlType) {
        if (voiceControlType == 1) {
            ((ActSuperPanelSettingBinding) this.mViewBinding).tvTalkVoiceControlState.setText(R.string.type_all);
            return;
        }
        if (voiceControlType == 2) {
            ((ActSuperPanelSettingBinding) this.mViewBinding).tvTalkVoiceControlState.setText(R.string.voice_control_part);
        } else if (voiceControlType == 3) {
            ((ActSuperPanelSettingBinding) this.mViewBinding).tvTalkVoiceControlState.setText(R.string.mode_diy);
        } else {
            ((ActSuperPanelSettingBinding) this.mViewBinding).tvTalkVoiceControlState.setText(R.string.type_all);
        }
    }

    private void editSwitchName(String content, final int position) {
        EditDialog.asDefault().setContent(content).setTitle(getString(R.string.rename)).setInputEmptyTip(getString(R.string.device_name_empty)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelSetting.this.lambda$editSwitchName$7(position, (String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editSwitchName$7(int i, String str) {
        if (i == 1) {
            this.superPanelExtParam.setSwitch1_name(str);
            this.superPanelExtParam.getRelateInfoMap(1).name = str;
        } else if (i == 2) {
            this.superPanelExtParam.setSwitch2_name(str);
            this.superPanelExtParam.getRelateInfoMap(2).name = str;
        } else if (i == 3) {
            this.superPanelExtParam.setSwitch3_name(str);
            this.superPanelExtParam.getRelateInfoMap(3).name = str;
        } else if (i == 4) {
            this.superPanelExtParam.setSwitch4_name(str);
            this.superPanelExtParam.getRelateInfoMap(4).name = str;
        }
        ((ActSuperPanelSettingVM) this.mViewModel).updateSuperPanelExt(this.superPanelExtParam);
    }

    private void editNightTime(final boolean isStartTime) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        SuperPanelExtParam superPanelExtParam = this.superPanelExtParam;
        this.selectHour = isStartTime ? superPanelExtParam.getSwitchNightModeStartH() : superPanelExtParam.getSwitchNightModeEndH();
        SuperPanelExtParam superPanelExtParam2 = this.superPanelExtParam;
        this.selectMin = isStartTime ? superPanelExtParam2.getSwitchNightModeStartM() : superPanelExtParam2.getSwitchNightModeEndM();
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(false).setSelectMinPosition(this.selectHour).setSelectSecPosition(this.selectMin).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActSuperPanelSetting.this.lambda$editNightTime$8(isStartTime, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editNightTime$8(boolean z, int i, int i2) {
        if (this.selectHour == i && this.selectMin == i2) {
            return;
        }
        this.selectHour = i;
        this.selectMin = i2;
        if (z) {
            int switchNightModeEndH = ((this.superPanelExtParam.getSwitchNightModeEndH() * 60) + this.superPanelExtParam.getSwitchNightModeEndM()) - ((i * 60) + i2);
            if (switchNightModeEndH > 0 && switchNightModeEndH < 30) {
                SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 30));
                return;
            } else {
                this.superPanelExtParam.setSwitchNightModeStartH(this.selectHour);
                this.superPanelExtParam.setSwitchNightModeStartM(this.selectMin);
            }
        } else {
            int switchNightModeStartH = ((i * 60) + i2) - ((this.superPanelExtParam.getSwitchNightModeStartH() * 60) + this.superPanelExtParam.getSwitchNightModeStartM());
            if (switchNightModeStartH > 0 && switchNightModeStartH < 30) {
                SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 30));
                return;
            } else {
                this.superPanelExtParam.setSwitchNightModeEndH(this.selectHour);
                this.superPanelExtParam.setSwitchNightModeEndM(this.selectMin);
            }
        }
        ((ActSuperPanelSettingVM) this.mViewModel).updateSuperPanelExt(this.superPanelExtParam);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 6003 || resultCode != -1 || data == null) {
            if (resultCode != -1 || data == null) {
                return;
            }
            boolean booleanExtra = data.getBooleanExtra("continous_back", false);
            ((ActSuperPanelSettingVM) this.mViewModel).isContinous_mode.setValue(Boolean.valueOf(booleanExtra));
            ((ActSuperPanelSettingBinding) this.mViewBinding).tvTalkState.setText(booleanExtra ? R.string.function_open : R.string.function_close);
            return;
        }
        int intExtra = data.getIntExtra("voice_control_range_back", 0);
        if (intExtra != 0) {
            this.voiceControlType = intExtra;
        }
        changeVoiceControlState(intExtra);
        if (((ActSuperPanelSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            queryVoiceControlRange(((ActSuperPanelSettingVM) this.mViewModel).controlDevice.getValue());
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void showEnginnerDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.setting)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.app_str_change_device_send_times));
        arrayList.add(getString(R.string.app_str_change_device_ttl));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelSetting.this.lambda$showEnginnerDialog$9((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEnginnerDialog$9(Integer num) {
        if (num.intValue() == 0) {
            showEditTimesDialog();
        } else if (num.intValue() == 1) {
            showEditTTLDialog();
        } else if (num.intValue() == 2) {
            cleanDeviceCache();
        }
    }
}