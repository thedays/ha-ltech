package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.e6knob.ActE6PanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.VoisePlayingIcon;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActE6PanelSettingBinding extends ViewDataBinding {
    public final AppCompatImageView deviceReplaceGo;
    public final LinearLayout editPlanTimeLayout;
    public final TextView endTimeTv;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivControlTypeGo;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivDimSignalGo;
    public final AppCompatImageView ivFirstAddGo;
    public final AppCompatImageView ivMacAddress;
    public final VoisePlayingIcon ivPlayAnim;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRangeGo;
    public final AppCompatImageView ivRgbInterfaceGo;
    public final AppCompatImageView ivRhythmsGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivSelected1;
    public final AppCompatImageView ivSelected2;
    public final AppCompatImageView ivSelected3;
    public final AppCompatImageView ivSensitivityGo;
    public final AppCompatImageView ivStateGo;
    public final AppCompatTextView kRangeLabel;
    public final RelativeLayout layoutBusPower;
    public final RelativeLayout layoutBuzzer;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutControlType;
    public final RelativeLayout layoutCreateGroup;
    public final ConstraintLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceLog;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final ConstraintLayout layoutDimSignal;
    public final ConstraintLayout layoutE6mAdd;
    public final RelativeLayout layoutEndTime;
    public final RelativeLayout layoutIndicator;
    public final LinearLayout layoutKRange;
    public final LinearLayout layoutKnobSetting;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutModeMemorize;
    public final RelativeLayout layoutModeOrder;
    public final RelativeLayout layoutPlan;
    public final RelativeLayout layoutPlanTime;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutRepeatDate;
    public final RelativeLayout layoutRestoreFactory;
    public final ConstraintLayout layoutRgbInterface;
    public final RelativeLayout layoutRhythms;
    public final RelativeLayout layoutRhythmsState;
    public final LinearLayout layoutRhythmsSwitch;
    public final RelativeLayout layoutSceneAndAutomation;
    public final ConstraintLayout layoutSensitivity;
    public final ConstraintLayout layoutSetOnState;
    public final RelativeLayout layoutStartTime;
    public final RelativeLayout layoutSunset;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActE6PanelSettingVM mViewmodel;
    public final AppCompatTextView planLabel;
    public final AppCompatTextView planTimeLabel;
    public final AppCompatImageView playIv;
    public final SmartRefreshLayout refreshLayout;
    public final AppCompatTextView repeatDateLabel;
    public final AppCompatTextView repeatWeekTv;
    public final AppCompatTextView rhythmsLabelTv;
    public final LinearLayout rhythmsSettingLayout;
    public final AppCompatTextView rhythmsStateLabel;
    public final RecyclerView rvLightSetting;
    public final SwitchButton sbBusPower;
    public final SwitchButton sbBuzzer;
    public final SwitchButton sbIndicator;
    public final SwitchButton sbModeMemorize;
    public final SwitchButton sbRhythmsText;
    public final TextView startTimeTv;
    public final AppCompatTextView sunsetLabel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvControlType;
    public final AppCompatTextView tvControlTypeState;
    public final AppCompatTextView tvControlTypeTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDimSignal;
    public final AppCompatTextView tvFirstAdd;
    public final AppCompatTextView tvKRange;
    public final AppCompatTextView tvLightOnState;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvPlan;
    public final AppCompatTextView tvPlanTime;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRelatedTip;
    public final AppCompatTextView tvRgbInterface;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSensitivity;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActE6PanelSettingVM viewmodel);

    protected ActE6PanelSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView deviceReplaceGo, LinearLayout editPlanTimeLayout, TextView endTimeTv, AppCompatImageView iv, AppCompatImageView ivControlTypeGo, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivDimSignalGo, AppCompatImageView ivFirstAddGo, AppCompatImageView ivMacAddress, VoisePlayingIcon ivPlayAnim, AppCompatImageView ivProductName, AppCompatImageView ivRangeGo, AppCompatImageView ivRgbInterfaceGo, AppCompatImageView ivRhythmsGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivSelected1, AppCompatImageView ivSelected2, AppCompatImageView ivSelected3, AppCompatImageView ivSensitivityGo, AppCompatImageView ivStateGo, AppCompatTextView kRangeLabel, RelativeLayout layoutBusPower, RelativeLayout layoutBuzzer, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutControlType, RelativeLayout layoutCreateGroup, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, ConstraintLayout layoutDimSignal, ConstraintLayout layoutE6mAdd, RelativeLayout layoutEndTime, RelativeLayout layoutIndicator, LinearLayout layoutKRange, LinearLayout layoutKnobSetting, RelativeLayout layoutMacAddress, RelativeLayout layoutModeMemorize, RelativeLayout layoutModeOrder, RelativeLayout layoutPlan, RelativeLayout layoutPlanTime, RelativeLayout layoutProductName, RelativeLayout layoutRepeatDate, RelativeLayout layoutRestoreFactory, ConstraintLayout layoutRgbInterface, RelativeLayout layoutRhythms, RelativeLayout layoutRhythmsState, LinearLayout layoutRhythmsSwitch, RelativeLayout layoutSceneAndAutomation, ConstraintLayout layoutSensitivity, ConstraintLayout layoutSetOnState, RelativeLayout layoutStartTime, RelativeLayout layoutSunset, RelativeLayout layoutUpgrade, AppCompatTextView planLabel, AppCompatTextView planTimeLabel, AppCompatImageView playIv, SmartRefreshLayout refreshLayout, AppCompatTextView repeatDateLabel, AppCompatTextView repeatWeekTv, AppCompatTextView rhythmsLabelTv, LinearLayout rhythmsSettingLayout, AppCompatTextView rhythmsStateLabel, RecyclerView rvLightSetting, SwitchButton sbBusPower, SwitchButton sbBuzzer, SwitchButton sbIndicator, SwitchButton sbModeMemorize, SwitchButton sbRhythmsText, TextView startTimeTv, AppCompatTextView sunsetLabel, LayoutTitleDefaultBinding title, AppCompatTextView tvControlType, AppCompatTextView tvControlTypeState, AppCompatTextView tvControlTypeTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvDimSignal, AppCompatTextView tvFirstAdd, AppCompatTextView tvKRange, AppCompatTextView tvLightOnState, AppCompatTextView tvNameTip, AppCompatTextView tvPlan, AppCompatTextView tvPlanTime, AppCompatTextView tvRelatedNum, AppCompatTextView tvRelatedTip, AppCompatTextView tvRgbInterface, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSensitivity, AppCompatTextView tvState, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.deviceReplaceGo = deviceReplaceGo;
        this.editPlanTimeLayout = editPlanTimeLayout;
        this.endTimeTv = endTimeTv;
        this.iv = iv;
        this.ivControlTypeGo = ivControlTypeGo;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivDimSignalGo = ivDimSignalGo;
        this.ivFirstAddGo = ivFirstAddGo;
        this.ivMacAddress = ivMacAddress;
        this.ivPlayAnim = ivPlayAnim;
        this.ivProductName = ivProductName;
        this.ivRangeGo = ivRangeGo;
        this.ivRgbInterfaceGo = ivRgbInterfaceGo;
        this.ivRhythmsGo = ivRhythmsGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivSelected1 = ivSelected1;
        this.ivSelected2 = ivSelected2;
        this.ivSelected3 = ivSelected3;
        this.ivSensitivityGo = ivSensitivityGo;
        this.ivStateGo = ivStateGo;
        this.kRangeLabel = kRangeLabel;
        this.layoutBusPower = layoutBusPower;
        this.layoutBuzzer = layoutBuzzer;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutControlType = layoutControlType;
        this.layoutCreateGroup = layoutCreateGroup;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceLog = layoutDeviceLog;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutDimSignal = layoutDimSignal;
        this.layoutE6mAdd = layoutE6mAdd;
        this.layoutEndTime = layoutEndTime;
        this.layoutIndicator = layoutIndicator;
        this.layoutKRange = layoutKRange;
        this.layoutKnobSetting = layoutKnobSetting;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutModeMemorize = layoutModeMemorize;
        this.layoutModeOrder = layoutModeOrder;
        this.layoutPlan = layoutPlan;
        this.layoutPlanTime = layoutPlanTime;
        this.layoutProductName = layoutProductName;
        this.layoutRepeatDate = layoutRepeatDate;
        this.layoutRestoreFactory = layoutRestoreFactory;
        this.layoutRgbInterface = layoutRgbInterface;
        this.layoutRhythms = layoutRhythms;
        this.layoutRhythmsState = layoutRhythmsState;
        this.layoutRhythmsSwitch = layoutRhythmsSwitch;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSensitivity = layoutSensitivity;
        this.layoutSetOnState = layoutSetOnState;
        this.layoutStartTime = layoutStartTime;
        this.layoutSunset = layoutSunset;
        this.layoutUpgrade = layoutUpgrade;
        this.planLabel = planLabel;
        this.planTimeLabel = planTimeLabel;
        this.playIv = playIv;
        this.refreshLayout = refreshLayout;
        this.repeatDateLabel = repeatDateLabel;
        this.repeatWeekTv = repeatWeekTv;
        this.rhythmsLabelTv = rhythmsLabelTv;
        this.rhythmsSettingLayout = rhythmsSettingLayout;
        this.rhythmsStateLabel = rhythmsStateLabel;
        this.rvLightSetting = rvLightSetting;
        this.sbBusPower = sbBusPower;
        this.sbBuzzer = sbBuzzer;
        this.sbIndicator = sbIndicator;
        this.sbModeMemorize = sbModeMemorize;
        this.sbRhythmsText = sbRhythmsText;
        this.startTimeTv = startTimeTv;
        this.sunsetLabel = sunsetLabel;
        this.title = title;
        this.tvControlType = tvControlType;
        this.tvControlTypeState = tvControlTypeState;
        this.tvControlTypeTip = tvControlTypeTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvDimSignal = tvDimSignal;
        this.tvFirstAdd = tvFirstAdd;
        this.tvKRange = tvKRange;
        this.tvLightOnState = tvLightOnState;
        this.tvNameTip = tvNameTip;
        this.tvPlan = tvPlan;
        this.tvPlanTime = tvPlanTime;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRelatedTip = tvRelatedTip;
        this.tvRgbInterface = tvRgbInterface;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSensitivity = tvSensitivity;
        this.tvState = tvState;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActE6PanelSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActE6PanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActE6PanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActE6PanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_e6_panel_setting, root, attachToRoot, component);
    }

    public static ActE6PanelSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActE6PanelSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActE6PanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_e6_panel_setting, null, false, component);
    }

    public static ActE6PanelSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActE6PanelSettingBinding bind(View view, Object component) {
        return (ActE6PanelSettingBinding) bind(component, view, R.layout.act_e6_panel_setting);
    }
}