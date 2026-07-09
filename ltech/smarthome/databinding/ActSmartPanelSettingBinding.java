package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActSmartPanelSettingBinding extends ViewDataBinding {
    public final AppCompatImageView batteryGo;
    public final AppCompatButton btnAdjust;
    public final AppCompatImageView deviceLogGo;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView elderlyGetUpNightModeGo;
    public final AppCompatImageView elderlyModeGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivDistanceGo;
    public final AppCompatImageView ivEndTimeGo;
    public final AppCompatImageView ivIconGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivStartTimeGo;
    public final AppCompatImageView languageGo;
    public final RelativeLayout layoutBattery;
    public final RelativeLayout layoutBrtButton;
    public final RelativeLayout layoutChangeLanguage;
    public final ConstraintLayout layoutChangeRoom;
    public final RelativeLayout layoutCreateGroup;
    public final ConstraintLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceLog;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final RelativeLayout layoutElderlyMode;
    public final ConstraintLayout layoutEndTime;
    public final RelativeLayout layoutGetUpNightMode;
    public final RelativeLayout layoutIconUpgrade;
    public final RelativeLayout layoutMacAddress;
    public final LinearLayout layoutMore;
    public final RelativeLayout layoutPanelBack;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutRestoreFactory;
    public final RelativeLayout layoutSceneAndAutomation;
    public final RelativeLayout layoutSensitivity;
    public final RelativeLayout layoutSetKRange;
    public final ConstraintLayout layoutStartTime;
    public final RelativeLayout layoutSwitchPositionSetting;
    public final RelativeLayout layoutSwitchSetting;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected ProductId mProductId;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSmartPanelSettingVM mViewmodel;
    public final AppCompatImageView panelBackGo;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvKeys;
    public final SwitchButton sbAutoTurnOff;
    public final SwitchButton sbDistance;
    public final SwitchButton sbEngravedText;
    public final LinearLayout sbLayout;
    public final SwitchButton sbMemorizePowerOnTip;
    public final SwitchButton sbNightMode;
    public final SwitchButton sbTouchVolume;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvElderlyModeTip;
    public final AppCompatTextView tvEndTime;
    public final AppCompatTextView tvEndTimeTip;
    public final AppCompatTextView tvGetUpNightModeTip;
    public final AppCompatTextView tvIconTip;
    public final AppCompatTextView tvLanguageTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvNightModeTip;
    public final AppCompatTextView tvPanelBackTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvStartTime;
    public final AppCompatTextView tvStartTimeTip;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setProductId(ProductId productId);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSmartPanelSettingVM viewmodel);

    protected ActSmartPanelSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView batteryGo, AppCompatButton btnAdjust, AppCompatImageView deviceLogGo, AppCompatImageView deviceReplaceGo, AppCompatImageView elderlyGetUpNightModeGo, AppCompatImageView elderlyModeGo, AppCompatImageView iv, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivDistanceGo, AppCompatImageView ivEndTimeGo, AppCompatImageView ivIconGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivStartTimeGo, AppCompatImageView languageGo, RelativeLayout layoutBattery, RelativeLayout layoutBrtButton, RelativeLayout layoutChangeLanguage, ConstraintLayout layoutChangeRoom, RelativeLayout layoutCreateGroup, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, RelativeLayout layoutElderlyMode, ConstraintLayout layoutEndTime, RelativeLayout layoutGetUpNightMode, RelativeLayout layoutIconUpgrade, RelativeLayout layoutMacAddress, LinearLayout layoutMore, RelativeLayout layoutPanelBack, RelativeLayout layoutProductName, RelativeLayout layoutRestoreFactory, RelativeLayout layoutSceneAndAutomation, RelativeLayout layoutSensitivity, RelativeLayout layoutSetKRange, ConstraintLayout layoutStartTime, RelativeLayout layoutSwitchPositionSetting, RelativeLayout layoutSwitchSetting, RelativeLayout layoutUpgrade, AppCompatImageView panelBackGo, SmartRefreshLayout refreshLayout, RecyclerView rvKeys, SwitchButton sbAutoTurnOff, SwitchButton sbDistance, SwitchButton sbEngravedText, LinearLayout sbLayout, SwitchButton sbMemorizePowerOnTip, SwitchButton sbNightMode, SwitchButton sbTouchVolume, LayoutTitleDefaultBinding title, AppCompatTextView tvBatteryTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvElderlyModeTip, AppCompatTextView tvEndTime, AppCompatTextView tvEndTimeTip, AppCompatTextView tvGetUpNightModeTip, AppCompatTextView tvIconTip, AppCompatTextView tvLanguageTip, AppCompatTextView tvNameTip, AppCompatTextView tvNightModeTip, AppCompatTextView tvPanelBackTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvStartTime, AppCompatTextView tvStartTimeTip, AppCompatTextView tvTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.batteryGo = batteryGo;
        this.btnAdjust = btnAdjust;
        this.deviceLogGo = deviceLogGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.elderlyGetUpNightModeGo = elderlyGetUpNightModeGo;
        this.elderlyModeGo = elderlyModeGo;
        this.iv = iv;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivDistanceGo = ivDistanceGo;
        this.ivEndTimeGo = ivEndTimeGo;
        this.ivIconGo = ivIconGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivStartTimeGo = ivStartTimeGo;
        this.languageGo = languageGo;
        this.layoutBattery = layoutBattery;
        this.layoutBrtButton = layoutBrtButton;
        this.layoutChangeLanguage = layoutChangeLanguage;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutCreateGroup = layoutCreateGroup;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceLog = layoutDeviceLog;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutElderlyMode = layoutElderlyMode;
        this.layoutEndTime = layoutEndTime;
        this.layoutGetUpNightMode = layoutGetUpNightMode;
        this.layoutIconUpgrade = layoutIconUpgrade;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutMore = layoutMore;
        this.layoutPanelBack = layoutPanelBack;
        this.layoutProductName = layoutProductName;
        this.layoutRestoreFactory = layoutRestoreFactory;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSensitivity = layoutSensitivity;
        this.layoutSetKRange = layoutSetKRange;
        this.layoutStartTime = layoutStartTime;
        this.layoutSwitchPositionSetting = layoutSwitchPositionSetting;
        this.layoutSwitchSetting = layoutSwitchSetting;
        this.layoutUpgrade = layoutUpgrade;
        this.panelBackGo = panelBackGo;
        this.refreshLayout = refreshLayout;
        this.rvKeys = rvKeys;
        this.sbAutoTurnOff = sbAutoTurnOff;
        this.sbDistance = sbDistance;
        this.sbEngravedText = sbEngravedText;
        this.sbLayout = sbLayout;
        this.sbMemorizePowerOnTip = sbMemorizePowerOnTip;
        this.sbNightMode = sbNightMode;
        this.sbTouchVolume = sbTouchVolume;
        this.title = title;
        this.tvBatteryTip = tvBatteryTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvElderlyModeTip = tvElderlyModeTip;
        this.tvEndTime = tvEndTime;
        this.tvEndTimeTip = tvEndTimeTip;
        this.tvGetUpNightModeTip = tvGetUpNightModeTip;
        this.tvIconTip = tvIconTip;
        this.tvLanguageTip = tvLanguageTip;
        this.tvNameTip = tvNameTip;
        this.tvNightModeTip = tvNightModeTip;
        this.tvPanelBackTip = tvPanelBackTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvStartTime = tvStartTime;
        this.tvStartTimeTip = tvStartTimeTip;
        this.tvTip = tvTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSmartPanelSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public ProductId getProductId() {
        return this.mProductId;
    }

    public static ActSmartPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSmartPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_panel_setting, root, attachToRoot, component);
    }

    public static ActSmartPanelSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSmartPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_panel_setting, null, false, component);
    }

    public static ActSmartPanelSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelSettingBinding bind(View view, Object component) {
        return (ActSmartPanelSettingBinding) bind(component, view, R.layout.act_smart_panel_setting);
    }
}