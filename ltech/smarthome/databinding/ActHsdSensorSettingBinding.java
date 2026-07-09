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
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActHsdSensorSettingBinding extends ViewDataBinding {
    public final AppCompatImageView batteryGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivDeviceId;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final RelativeLayout layoutBattery;
    public final ConstraintLayout layoutChangeRoom;
    public final RelativeLayout layoutDeviceId;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutSceneAndAutomation;
    public final RelativeLayout layoutSenseSetting;
    public final ConstraintLayout layoutTestMode;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected BaseDeviceSetViewModel mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(BaseDeviceSetViewModel viewmodel);

    protected ActHsdSensorSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView batteryGo, AppCompatImageView iv, AppCompatImageView ivDeviceId, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, RelativeLayout layoutBattery, ConstraintLayout layoutChangeRoom, RelativeLayout layoutDeviceId, ConstraintLayout layoutDeviceName, RelativeLayout layoutMacAddress, RelativeLayout layoutProductName, RelativeLayout layoutSceneAndAutomation, RelativeLayout layoutSenseSetting, ConstraintLayout layoutTestMode, RelativeLayout layoutUpgrade, SmartRefreshLayout refreshLayout, LayoutTitleDefaultBinding title, AppCompatTextView tvBatteryTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceName, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.batteryGo = batteryGo;
        this.iv = iv;
        this.ivDeviceId = ivDeviceId;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.layoutBattery = layoutBattery;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutProductName = layoutProductName;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSenseSetting = layoutSenseSetting;
        this.layoutTestMode = layoutTestMode;
        this.layoutUpgrade = layoutUpgrade;
        this.refreshLayout = refreshLayout;
        this.title = title;
        this.tvBatteryTip = tvBatteryTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceName = tvDeviceName;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BaseDeviceSetViewModel getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActHsdSensorSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHsdSensorSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHsdSensorSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_hsd_sensor_setting, root, attachToRoot, component);
    }

    public static ActHsdSensorSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHsdSensorSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHsdSensorSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_hsd_sensor_setting, null, false, component);
    }

    public static ActHsdSensorSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHsdSensorSettingBinding bind(View view, Object component) {
        return (ActHsdSensorSettingBinding) bind(component, view, R.layout.act_hsd_sensor_setting);
    }
}