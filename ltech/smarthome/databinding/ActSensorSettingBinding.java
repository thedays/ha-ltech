package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.setting.ActSensorSettingVM;

/* loaded from: classes3.dex */
public abstract class ActSensorSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivBatteryNameGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivIntervalNameGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSensitivityNameGo;
    public final ConstraintLayout layoutBattery;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceName;
    public final ConstraintLayout layoutInterval;
    public final ConstraintLayout layoutSensitivity;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSensorSettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBatteryName;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvIntervalName;
    public final AppCompatTextView tvIntervalTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSensitivityName;
    public final AppCompatTextView tvSensitivityTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSensorSettingVM viewmodel);

    protected ActSensorSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBatteryNameGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivIntervalNameGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSensitivityNameGo, ConstraintLayout layoutBattery, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceName, ConstraintLayout layoutInterval, ConstraintLayout layoutSensitivity, LayoutTitleDefaultBinding title, AppCompatTextView tvBatteryName, AppCompatTextView tvBatteryTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceName, AppCompatTextView tvIntervalName, AppCompatTextView tvIntervalTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSensitivityName, AppCompatTextView tvSensitivityTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBatteryNameGo = ivBatteryNameGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivIntervalNameGo = ivIntervalNameGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSensitivityNameGo = ivSensitivityNameGo;
        this.layoutBattery = layoutBattery;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutInterval = layoutInterval;
        this.layoutSensitivity = layoutSensitivity;
        this.title = title;
        this.tvBatteryName = tvBatteryName;
        this.tvBatteryTip = tvBatteryTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceName = tvDeviceName;
        this.tvIntervalName = tvIntervalName;
        this.tvIntervalTip = tvIntervalTip;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSensitivityName = tvSensitivityName;
        this.tvSensitivityTip = tvSensitivityTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSensorSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSensorSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSensorSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSensorSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sensor_setting, root, attachToRoot, component);
    }

    public static ActSensorSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSensorSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSensorSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sensor_setting, null, false, component);
    }

    public static ActSensorSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSensorSettingBinding bind(View view, Object component) {
        return (ActSensorSettingBinding) bind(component, view, R.layout.act_sensor_setting);
    }
}