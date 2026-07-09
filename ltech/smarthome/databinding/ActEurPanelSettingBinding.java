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
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActEurPanelSettingBinding extends ViewDataBinding {
    public final AppCompatImageView batteryGo;
    public final AppCompatButton btnAdjust;
    public final AppCompatImageView deviceLogGo;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivControlTypeStateGo;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivDmxGo;
    public final AppCompatImageView ivFunctionStateGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivSensitivityGo;
    public final AppCompatImageView ivStateGo;
    public final AppCompatImageView ivZoneControlStateGo;
    public final RelativeLayout layoutBattery;
    public final RelativeLayout layoutBrtButton;
    public final RelativeLayout layoutBuzzer;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutControlType;
    public final RelativeLayout layoutCreateGroup;
    public final ConstraintLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceLog;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final ConstraintLayout layoutFunction;
    public final RelativeLayout layoutKeySave;
    public final LinearLayout layoutKnobSetting;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutModeMemorize;
    public final RelativeLayout layoutModeOrder;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutSceneAndAutomation;
    public final ConstraintLayout layoutSensitivity;
    public final RelativeLayout layoutSetDmxType;
    public final RelativeLayout layoutSetKRange;
    public final ConstraintLayout layoutSetOnState;
    public final RelativeLayout layoutUpgrade;
    public final ConstraintLayout layoutZoneControl;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActEurPanelSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvLightSetting;
    public final SwitchButton sbBuzzer;
    public final SwitchButton sbKeySave;
    public final SwitchButton sbModeMemorize;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvControlType;
    public final AppCompatTextView tvControlTypeState;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDmxType;
    public final AppCompatTextView tvEurFunctionTips;
    public final AppCompatTextView tvFunction;
    public final AppCompatTextView tvFunctionState;
    public final AppCompatTextView tvKeySaveTip;
    public final AppCompatTextView tvLightOnState;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRelatedTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSensitivity;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvUpgradeTip;
    public final AppCompatTextView tvZoneControl;
    public final AppCompatTextView tvZoneControlState;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActEurPanelSettingVM viewmodel);

    protected ActEurPanelSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView batteryGo, AppCompatButton btnAdjust, AppCompatImageView deviceLogGo, AppCompatImageView deviceReplaceGo, AppCompatImageView iv, AppCompatImageView ivControlTypeStateGo, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivDmxGo, AppCompatImageView ivFunctionStateGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivSensitivityGo, AppCompatImageView ivStateGo, AppCompatImageView ivZoneControlStateGo, RelativeLayout layoutBattery, RelativeLayout layoutBrtButton, RelativeLayout layoutBuzzer, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutControlType, RelativeLayout layoutCreateGroup, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, ConstraintLayout layoutFunction, RelativeLayout layoutKeySave, LinearLayout layoutKnobSetting, RelativeLayout layoutMacAddress, RelativeLayout layoutModeMemorize, RelativeLayout layoutModeOrder, RelativeLayout layoutProductName, RelativeLayout layoutSceneAndAutomation, ConstraintLayout layoutSensitivity, RelativeLayout layoutSetDmxType, RelativeLayout layoutSetKRange, ConstraintLayout layoutSetOnState, RelativeLayout layoutUpgrade, ConstraintLayout layoutZoneControl, SmartRefreshLayout refreshLayout, RecyclerView rvLightSetting, SwitchButton sbBuzzer, SwitchButton sbKeySave, SwitchButton sbModeMemorize, LayoutTitleDefaultBinding title, AppCompatTextView tvBatteryTip, AppCompatTextView tvControlType, AppCompatTextView tvControlTypeState, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvDmxType, AppCompatTextView tvEurFunctionTips, AppCompatTextView tvFunction, AppCompatTextView tvFunctionState, AppCompatTextView tvKeySaveTip, AppCompatTextView tvLightOnState, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRelatedTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSensitivity, AppCompatTextView tvState, AppCompatTextView tvUpgradeTip, AppCompatTextView tvZoneControl, AppCompatTextView tvZoneControlState) {
        super(_bindingComponent, _root, _localFieldCount);
        this.batteryGo = batteryGo;
        this.btnAdjust = btnAdjust;
        this.deviceLogGo = deviceLogGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.iv = iv;
        this.ivControlTypeStateGo = ivControlTypeStateGo;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivDmxGo = ivDmxGo;
        this.ivFunctionStateGo = ivFunctionStateGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivSensitivityGo = ivSensitivityGo;
        this.ivStateGo = ivStateGo;
        this.ivZoneControlStateGo = ivZoneControlStateGo;
        this.layoutBattery = layoutBattery;
        this.layoutBrtButton = layoutBrtButton;
        this.layoutBuzzer = layoutBuzzer;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutControlType = layoutControlType;
        this.layoutCreateGroup = layoutCreateGroup;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceLog = layoutDeviceLog;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutFunction = layoutFunction;
        this.layoutKeySave = layoutKeySave;
        this.layoutKnobSetting = layoutKnobSetting;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutModeMemorize = layoutModeMemorize;
        this.layoutModeOrder = layoutModeOrder;
        this.layoutProductName = layoutProductName;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSensitivity = layoutSensitivity;
        this.layoutSetDmxType = layoutSetDmxType;
        this.layoutSetKRange = layoutSetKRange;
        this.layoutSetOnState = layoutSetOnState;
        this.layoutUpgrade = layoutUpgrade;
        this.layoutZoneControl = layoutZoneControl;
        this.refreshLayout = refreshLayout;
        this.rvLightSetting = rvLightSetting;
        this.sbBuzzer = sbBuzzer;
        this.sbKeySave = sbKeySave;
        this.sbModeMemorize = sbModeMemorize;
        this.title = title;
        this.tvBatteryTip = tvBatteryTip;
        this.tvControlType = tvControlType;
        this.tvControlTypeState = tvControlTypeState;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvDmxType = tvDmxType;
        this.tvEurFunctionTips = tvEurFunctionTips;
        this.tvFunction = tvFunction;
        this.tvFunctionState = tvFunctionState;
        this.tvKeySaveTip = tvKeySaveTip;
        this.tvLightOnState = tvLightOnState;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRelatedTip = tvRelatedTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSensitivity = tvSensitivity;
        this.tvState = tvState;
        this.tvUpgradeTip = tvUpgradeTip;
        this.tvZoneControl = tvZoneControl;
        this.tvZoneControlState = tvZoneControlState;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActEurPanelSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActEurPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEurPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEurPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_eur_panel_setting, root, attachToRoot, component);
    }

    public static ActEurPanelSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEurPanelSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEurPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_eur_panel_setting, null, false, component);
    }

    public static ActEurPanelSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEurPanelSettingBinding bind(View view, Object component) {
        return (ActEurPanelSettingBinding) bind(component, view, R.layout.act_eur_panel_setting);
    }
}