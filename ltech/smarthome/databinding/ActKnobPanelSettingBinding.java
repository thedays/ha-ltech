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
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActKnobPanelSettingBinding extends ViewDataBinding {
    public final AppCompatImageView batteryGo;
    public final AppCompatButton btnAdjust;
    public final AppCompatImageView elderlyModeGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivEndTimeGo;
    public final AppCompatImageView ivIconGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivSensitivityGo;
    public final AppCompatImageView ivStartTimeGo;
    public final AppCompatImageView languageGo;
    public final RelativeLayout layoutBattery;
    public final RelativeLayout layoutChangeLanguage;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceId;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutElderlyMode;
    public final ConstraintLayout layoutEndTimeKnob;
    public final RelativeLayout layoutIconUpgrade;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutModeMemorize;
    public final RelativeLayout layoutModeOrder;
    public final LinearLayout layoutMore;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutSceneAndAutomation;
    public final RelativeLayout layoutSensitivity;
    public final RelativeLayout layoutSetKRange;
    public final ConstraintLayout layoutStartTimeKnob;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActknobPanelSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final SwitchButton sbAutoTurnOff;
    public final SwitchButton sbEngravedText;
    public final SwitchButton sbModeMemorize;
    public final SwitchButton sbNightMode;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvElderlyModeTip;
    public final AppCompatTextView tvEndTime;
    public final AppCompatTextView tvEndTimeTip;
    public final AppCompatTextView tvIconTip;
    public final AppCompatTextView tvLanguageTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSensitivity;
    public final AppCompatTextView tvStartTime;
    public final AppCompatTextView tvStartTimeTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActknobPanelSettingVM viewmodel);

    protected ActKnobPanelSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView batteryGo, AppCompatButton btnAdjust, AppCompatImageView elderlyModeGo, AppCompatImageView iv, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivEndTimeGo, AppCompatImageView ivIconGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivSensitivityGo, AppCompatImageView ivStartTimeGo, AppCompatImageView languageGo, RelativeLayout layoutBattery, RelativeLayout layoutChangeLanguage, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceId, ConstraintLayout layoutDeviceName, RelativeLayout layoutElderlyMode, ConstraintLayout layoutEndTimeKnob, RelativeLayout layoutIconUpgrade, RelativeLayout layoutMacAddress, RelativeLayout layoutModeMemorize, RelativeLayout layoutModeOrder, LinearLayout layoutMore, RelativeLayout layoutProductName, RelativeLayout layoutSceneAndAutomation, RelativeLayout layoutSensitivity, RelativeLayout layoutSetKRange, ConstraintLayout layoutStartTimeKnob, RelativeLayout layoutUpgrade, SmartRefreshLayout refreshLayout, SwitchButton sbAutoTurnOff, SwitchButton sbEngravedText, SwitchButton sbModeMemorize, SwitchButton sbNightMode, LayoutTitleDefaultBinding title, AppCompatTextView tvBatteryTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvElderlyModeTip, AppCompatTextView tvEndTime, AppCompatTextView tvEndTimeTip, AppCompatTextView tvIconTip, AppCompatTextView tvLanguageTip, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSensitivity, AppCompatTextView tvStartTime, AppCompatTextView tvStartTimeTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.batteryGo = batteryGo;
        this.btnAdjust = btnAdjust;
        this.elderlyModeGo = elderlyModeGo;
        this.iv = iv;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivEndTimeGo = ivEndTimeGo;
        this.ivIconGo = ivIconGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivSensitivityGo = ivSensitivityGo;
        this.ivStartTimeGo = ivStartTimeGo;
        this.languageGo = languageGo;
        this.layoutBattery = layoutBattery;
        this.layoutChangeLanguage = layoutChangeLanguage;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutElderlyMode = layoutElderlyMode;
        this.layoutEndTimeKnob = layoutEndTimeKnob;
        this.layoutIconUpgrade = layoutIconUpgrade;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutModeMemorize = layoutModeMemorize;
        this.layoutModeOrder = layoutModeOrder;
        this.layoutMore = layoutMore;
        this.layoutProductName = layoutProductName;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSensitivity = layoutSensitivity;
        this.layoutSetKRange = layoutSetKRange;
        this.layoutStartTimeKnob = layoutStartTimeKnob;
        this.layoutUpgrade = layoutUpgrade;
        this.refreshLayout = refreshLayout;
        this.sbAutoTurnOff = sbAutoTurnOff;
        this.sbEngravedText = sbEngravedText;
        this.sbModeMemorize = sbModeMemorize;
        this.sbNightMode = sbNightMode;
        this.title = title;
        this.tvBatteryTip = tvBatteryTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvElderlyModeTip = tvElderlyModeTip;
        this.tvEndTime = tvEndTime;
        this.tvEndTimeTip = tvEndTimeTip;
        this.tvIconTip = tvIconTip;
        this.tvLanguageTip = tvLanguageTip;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSensitivity = tvSensitivity;
        this.tvStartTime = tvStartTime;
        this.tvStartTimeTip = tvStartTimeTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActknobPanelSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActKnobPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKnobPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActKnobPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_knob_panel_setting, root, attachToRoot, component);
    }

    public static ActKnobPanelSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKnobPanelSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActKnobPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_knob_panel_setting, null, false, component);
    }

    public static ActKnobPanelSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKnobPanelSettingBinding bind(View view, Object component) {
        return (ActKnobPanelSettingBinding) bind(component, view, R.layout.act_knob_panel_setting);
    }
}