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
import com.ltech.smarthome.ui.device.light.ActLightSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.VoisePlayingIcon;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActLightSettingNewBinding extends ViewDataBinding {
    public final AppCompatImageView deviceLogGo;
    public final AppCompatImageView deviceReplaceGo;
    public final LinearLayout editPlanTimeLayout;
    public final TextView endTimeTv;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivBatchGo;
    public final AppCompatImageView ivControlTypeGo;
    public final AppCompatImageView ivCurrentGo;
    public final AppCompatImageView ivDepthGo;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivDmxGo;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivLineGo;
    public final AppCompatImageView ivMacAddress;
    public final VoisePlayingIcon ivPlayAnim;
    public final AppCompatImageView ivPowerOffSceneDelayGo;
    public final AppCompatImageView ivPowerOffSceneGo;
    public final AppCompatImageView ivPowerOnSceneDelayGo;
    public final AppCompatImageView ivPowerOnSceneGo;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivPwmGo;
    public final AppCompatImageView ivRangeGo;
    public final AppCompatImageView ivRhythmsGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSelected1;
    public final AppCompatImageView ivSelected2;
    public final AppCompatImageView ivSelected3;
    public final AppCompatImageView ivStateGo;
    public final AppCompatTextView kRangeLabel;
    public final RelativeLayout layoutBatchSet;
    public final RelativeLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final RelativeLayout layoutConstantPower;
    public final ConstraintLayout layoutControlType;
    public final RelativeLayout layoutCreateGroup;
    public final ConstraintLayout layoutCurrent;
    public final ConstraintLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceLog;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final ConstraintLayout layoutDimDepth;
    public final LinearLayout layoutDimRange;
    public final RelativeLayout layoutDuv;
    public final RelativeLayout layoutEndTime;
    public final LinearLayout layoutKRange;
    public final RelativeLayout layoutLineSet;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutOutput;
    public final RelativeLayout layoutPlan;
    public final RelativeLayout layoutPlanTime;
    public final ConstraintLayout layoutPowerOffScene;
    public final ConstraintLayout layoutPowerOffSceneDelay;
    public final ConstraintLayout layoutPowerOnScene;
    public final ConstraintLayout layoutPowerOnSceneDelay;
    public final RelativeLayout layoutProductName;
    public final ConstraintLayout layoutPwmFrequency;
    public final RelativeLayout layoutRepeatDate;
    public final RelativeLayout layoutRestoreFactory;
    public final RelativeLayout layoutRhythms;
    public final RelativeLayout layoutRhythmsState;
    public final LinearLayout layoutRhythmsSwitch;
    public final RelativeLayout layoutSetDmxType;
    public final ConstraintLayout layoutSetOnState;
    public final RelativeLayout layoutStartTime;
    public final RelativeLayout layoutSunset;
    public final RelativeLayout layoutUpgrade;
    public final RelativeLayout layoutWhiteBalance;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActLightSettingVM mViewmodel;
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
    public final SwitchButton sbConstantPower;
    public final SwitchButton sbRhythmsText;
    public final TextView startTimeTv;
    public final AppCompatTextView sunsetLabel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvConstantPower;
    public final AppCompatTextView tvControlType;
    public final AppCompatTextView tvControlTypeState;
    public final AppCompatTextView tvCurrent;
    public final AppCompatTextView tvDbatchSet;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDimDepth;
    public final AppCompatTextView tvDimRange;
    public final AppCompatTextView tvDmxType;
    public final AppCompatTextView tvKRange;
    public final AppCompatTextView tvLightOnState;
    public final AppCompatTextView tvLine;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvPlan;
    public final AppCompatTextView tvPlanTime;
    public final AppCompatTextView tvPowerOffScene;
    public final AppCompatTextView tvPowerOffSceneDelay;
    public final AppCompatTextView tvPowerOffSceneName;
    public final AppCompatTextView tvPowerOffSceneNameDelay;
    public final AppCompatTextView tvPowerOnScene;
    public final AppCompatTextView tvPowerOnSceneDelay;
    public final AppCompatTextView tvPowerOnSceneName;
    public final AppCompatTextView tvPowerOnSceneNameDelay;
    public final AppCompatTextView tvPwmFrequency;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActLightSettingVM viewmodel);

    protected ActLightSettingNewBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView deviceLogGo, AppCompatImageView deviceReplaceGo, LinearLayout editPlanTimeLayout, TextView endTimeTv, AppCompatImageView iv, AppCompatImageView ivBatchGo, AppCompatImageView ivControlTypeGo, AppCompatImageView ivCurrentGo, AppCompatImageView ivDepthGo, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivDmxGo, AppCompatImageView ivIcon, AppCompatImageView ivLineGo, AppCompatImageView ivMacAddress, VoisePlayingIcon ivPlayAnim, AppCompatImageView ivPowerOffSceneDelayGo, AppCompatImageView ivPowerOffSceneGo, AppCompatImageView ivPowerOnSceneDelayGo, AppCompatImageView ivPowerOnSceneGo, AppCompatImageView ivProductName, AppCompatImageView ivPwmGo, AppCompatImageView ivRangeGo, AppCompatImageView ivRhythmsGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSelected1, AppCompatImageView ivSelected2, AppCompatImageView ivSelected3, AppCompatImageView ivStateGo, AppCompatTextView kRangeLabel, RelativeLayout layoutBatchSet, RelativeLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, RelativeLayout layoutConstantPower, ConstraintLayout layoutControlType, RelativeLayout layoutCreateGroup, ConstraintLayout layoutCurrent, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, ConstraintLayout layoutDimDepth, LinearLayout layoutDimRange, RelativeLayout layoutDuv, RelativeLayout layoutEndTime, LinearLayout layoutKRange, RelativeLayout layoutLineSet, RelativeLayout layoutMacAddress, RelativeLayout layoutOutput, RelativeLayout layoutPlan, RelativeLayout layoutPlanTime, ConstraintLayout layoutPowerOffScene, ConstraintLayout layoutPowerOffSceneDelay, ConstraintLayout layoutPowerOnScene, ConstraintLayout layoutPowerOnSceneDelay, RelativeLayout layoutProductName, ConstraintLayout layoutPwmFrequency, RelativeLayout layoutRepeatDate, RelativeLayout layoutRestoreFactory, RelativeLayout layoutRhythms, RelativeLayout layoutRhythmsState, LinearLayout layoutRhythmsSwitch, RelativeLayout layoutSetDmxType, ConstraintLayout layoutSetOnState, RelativeLayout layoutStartTime, RelativeLayout layoutSunset, RelativeLayout layoutUpgrade, RelativeLayout layoutWhiteBalance, AppCompatTextView planLabel, AppCompatTextView planTimeLabel, AppCompatImageView playIv, SmartRefreshLayout refreshLayout, AppCompatTextView repeatDateLabel, AppCompatTextView repeatWeekTv, AppCompatTextView rhythmsLabelTv, LinearLayout rhythmsSettingLayout, AppCompatTextView rhythmsStateLabel, RecyclerView rvLightSetting, SwitchButton sbConstantPower, SwitchButton sbRhythmsText, TextView startTimeTv, AppCompatTextView sunsetLabel, LayoutTitleDefaultBinding title, AppCompatTextView tvConstantPower, AppCompatTextView tvControlType, AppCompatTextView tvControlTypeState, AppCompatTextView tvCurrent, AppCompatTextView tvDbatchSet, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvDimDepth, AppCompatTextView tvDimRange, AppCompatTextView tvDmxType, AppCompatTextView tvKRange, AppCompatTextView tvLightOnState, AppCompatTextView tvLine, AppCompatTextView tvNameTip, AppCompatTextView tvPlan, AppCompatTextView tvPlanTime, AppCompatTextView tvPowerOffScene, AppCompatTextView tvPowerOffSceneDelay, AppCompatTextView tvPowerOffSceneName, AppCompatTextView tvPowerOffSceneNameDelay, AppCompatTextView tvPowerOnScene, AppCompatTextView tvPowerOnSceneDelay, AppCompatTextView tvPowerOnSceneName, AppCompatTextView tvPowerOnSceneNameDelay, AppCompatTextView tvPwmFrequency, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvState, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.deviceLogGo = deviceLogGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.editPlanTimeLayout = editPlanTimeLayout;
        this.endTimeTv = endTimeTv;
        this.iv = iv;
        this.ivBatchGo = ivBatchGo;
        this.ivControlTypeGo = ivControlTypeGo;
        this.ivCurrentGo = ivCurrentGo;
        this.ivDepthGo = ivDepthGo;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivDmxGo = ivDmxGo;
        this.ivIcon = ivIcon;
        this.ivLineGo = ivLineGo;
        this.ivMacAddress = ivMacAddress;
        this.ivPlayAnim = ivPlayAnim;
        this.ivPowerOffSceneDelayGo = ivPowerOffSceneDelayGo;
        this.ivPowerOffSceneGo = ivPowerOffSceneGo;
        this.ivPowerOnSceneDelayGo = ivPowerOnSceneDelayGo;
        this.ivPowerOnSceneGo = ivPowerOnSceneGo;
        this.ivProductName = ivProductName;
        this.ivPwmGo = ivPwmGo;
        this.ivRangeGo = ivRangeGo;
        this.ivRhythmsGo = ivRhythmsGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSelected1 = ivSelected1;
        this.ivSelected2 = ivSelected2;
        this.ivSelected3 = ivSelected3;
        this.ivStateGo = ivStateGo;
        this.kRangeLabel = kRangeLabel;
        this.layoutBatchSet = layoutBatchSet;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutConstantPower = layoutConstantPower;
        this.layoutControlType = layoutControlType;
        this.layoutCreateGroup = layoutCreateGroup;
        this.layoutCurrent = layoutCurrent;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceLog = layoutDeviceLog;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutDimDepth = layoutDimDepth;
        this.layoutDimRange = layoutDimRange;
        this.layoutDuv = layoutDuv;
        this.layoutEndTime = layoutEndTime;
        this.layoutKRange = layoutKRange;
        this.layoutLineSet = layoutLineSet;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutOutput = layoutOutput;
        this.layoutPlan = layoutPlan;
        this.layoutPlanTime = layoutPlanTime;
        this.layoutPowerOffScene = layoutPowerOffScene;
        this.layoutPowerOffSceneDelay = layoutPowerOffSceneDelay;
        this.layoutPowerOnScene = layoutPowerOnScene;
        this.layoutPowerOnSceneDelay = layoutPowerOnSceneDelay;
        this.layoutProductName = layoutProductName;
        this.layoutPwmFrequency = layoutPwmFrequency;
        this.layoutRepeatDate = layoutRepeatDate;
        this.layoutRestoreFactory = layoutRestoreFactory;
        this.layoutRhythms = layoutRhythms;
        this.layoutRhythmsState = layoutRhythmsState;
        this.layoutRhythmsSwitch = layoutRhythmsSwitch;
        this.layoutSetDmxType = layoutSetDmxType;
        this.layoutSetOnState = layoutSetOnState;
        this.layoutStartTime = layoutStartTime;
        this.layoutSunset = layoutSunset;
        this.layoutUpgrade = layoutUpgrade;
        this.layoutWhiteBalance = layoutWhiteBalance;
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
        this.sbConstantPower = sbConstantPower;
        this.sbRhythmsText = sbRhythmsText;
        this.startTimeTv = startTimeTv;
        this.sunsetLabel = sunsetLabel;
        this.title = title;
        this.tvConstantPower = tvConstantPower;
        this.tvControlType = tvControlType;
        this.tvControlTypeState = tvControlTypeState;
        this.tvCurrent = tvCurrent;
        this.tvDbatchSet = tvDbatchSet;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvDimDepth = tvDimDepth;
        this.tvDimRange = tvDimRange;
        this.tvDmxType = tvDmxType;
        this.tvKRange = tvKRange;
        this.tvLightOnState = tvLightOnState;
        this.tvLine = tvLine;
        this.tvNameTip = tvNameTip;
        this.tvPlan = tvPlan;
        this.tvPlanTime = tvPlanTime;
        this.tvPowerOffScene = tvPowerOffScene;
        this.tvPowerOffSceneDelay = tvPowerOffSceneDelay;
        this.tvPowerOffSceneName = tvPowerOffSceneName;
        this.tvPowerOffSceneNameDelay = tvPowerOffSceneNameDelay;
        this.tvPowerOnScene = tvPowerOnScene;
        this.tvPowerOnSceneDelay = tvPowerOnSceneDelay;
        this.tvPowerOnSceneName = tvPowerOnSceneName;
        this.tvPowerOnSceneNameDelay = tvPowerOnSceneNameDelay;
        this.tvPwmFrequency = tvPwmFrequency;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvState = tvState;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActLightSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActLightSettingNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightSettingNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActLightSettingNewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_setting_new, root, attachToRoot, component);
    }

    public static ActLightSettingNewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightSettingNewBinding inflate(LayoutInflater inflater, Object component) {
        return (ActLightSettingNewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_setting_new, null, false, component);
    }

    public static ActLightSettingNewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightSettingNewBinding bind(View view, Object component) {
        return (ActLightSettingNewBinding) bind(component, view, R.layout.act_light_setting_new);
    }
}