package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSettingVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActWaveSensorSettingBinding extends ViewDataBinding {
    public final AppCompatImageView batteryGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivCtGo;
    public final AppCompatImageView ivDelayGo;
    public final AppCompatImageView ivDeviceId;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivIlluminanceGo;
    public final AppCompatImageView ivLuxGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivSensitivityGo;
    public final RelativeLayout layoutAutomationDelay;
    public final RelativeLayout layoutBattery;
    public final ConstraintLayout layoutChangeRoom;
    public final RelativeLayout layoutCreateGroup;
    public final RelativeLayout layoutDeviceId;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutSceneAndAutomation;
    public final RelativeLayout layoutSenseSetting;
    public final ConstraintLayout layoutSetCt;
    public final ConstraintLayout layoutSetIlluminance;
    public final ConstraintLayout layoutSetLux;
    public final ConstraintLayout layoutSetSensitivity;
    public final ConstraintLayout layoutTestMode;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActWaveSensorSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvCtValue;
    public final AppCompatTextView tvDelay;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvIlluminance;
    public final AppCompatTextView tvLuxValue;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSensitivity;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActWaveSensorSettingVM viewmodel);

    protected ActWaveSensorSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView batteryGo, AppCompatImageView iv, AppCompatImageView ivCtGo, AppCompatImageView ivDelayGo, AppCompatImageView ivDeviceId, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivIlluminanceGo, AppCompatImageView ivLuxGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivSensitivityGo, RelativeLayout layoutAutomationDelay, RelativeLayout layoutBattery, ConstraintLayout layoutChangeRoom, RelativeLayout layoutCreateGroup, RelativeLayout layoutDeviceId, ConstraintLayout layoutDeviceName, RelativeLayout layoutMacAddress, RelativeLayout layoutProductName, RelativeLayout layoutSceneAndAutomation, RelativeLayout layoutSenseSetting, ConstraintLayout layoutSetCt, ConstraintLayout layoutSetIlluminance, ConstraintLayout layoutSetLux, ConstraintLayout layoutSetSensitivity, ConstraintLayout layoutTestMode, RelativeLayout layoutUpgrade, SmartRefreshLayout refreshLayout, LayoutTitleDefaultBinding title, AppCompatTextView tvBatteryTip, AppCompatTextView tvCtValue, AppCompatTextView tvDelay, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceName, AppCompatTextView tvIlluminance, AppCompatTextView tvLuxValue, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSensitivity, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.batteryGo = batteryGo;
        this.iv = iv;
        this.ivCtGo = ivCtGo;
        this.ivDelayGo = ivDelayGo;
        this.ivDeviceId = ivDeviceId;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivIlluminanceGo = ivIlluminanceGo;
        this.ivLuxGo = ivLuxGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivSensitivityGo = ivSensitivityGo;
        this.layoutAutomationDelay = layoutAutomationDelay;
        this.layoutBattery = layoutBattery;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutCreateGroup = layoutCreateGroup;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutProductName = layoutProductName;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSenseSetting = layoutSenseSetting;
        this.layoutSetCt = layoutSetCt;
        this.layoutSetIlluminance = layoutSetIlluminance;
        this.layoutSetLux = layoutSetLux;
        this.layoutSetSensitivity = layoutSetSensitivity;
        this.layoutTestMode = layoutTestMode;
        this.layoutUpgrade = layoutUpgrade;
        this.refreshLayout = refreshLayout;
        this.title = title;
        this.tvBatteryTip = tvBatteryTip;
        this.tvCtValue = tvCtValue;
        this.tvDelay = tvDelay;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceName = tvDeviceName;
        this.tvIlluminance = tvIlluminance;
        this.tvLuxValue = tvLuxValue;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSensitivity = tvSensitivity;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActWaveSensorSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActWaveSensorSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActWaveSensorSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_wave_sensor_setting, root, attachToRoot, component);
    }

    public static ActWaveSensorSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActWaveSensorSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_wave_sensor_setting, null, false, component);
    }

    public static ActWaveSensorSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorSettingBinding bind(View view, Object component) {
        return (ActWaveSensorSettingBinding) bind(component, view, R.layout.act_wave_sensor_setting);
    }
}