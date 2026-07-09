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
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefaultVM;

/* loaded from: classes3.dex */
public abstract class ActDeviceSettingDefaultBinding extends ViewDataBinding {
    public final AppCompatImageView batteryGo;
    public final AppCompatImageView deviceLogGo;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final RelativeLayout layoutBattery;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceLog;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutSceneAndAutomation;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDeviceSettingDefaultVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRelatedTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDeviceSettingDefaultVM viewmodel);

    protected ActDeviceSettingDefaultBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView batteryGo, AppCompatImageView deviceLogGo, AppCompatImageView deviceReplaceGo, AppCompatImageView iv, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, RelativeLayout layoutBattery, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, RelativeLayout layoutMacAddress, RelativeLayout layoutProductName, RelativeLayout layoutSceneAndAutomation, RelativeLayout layoutUpgrade, LayoutTitleDefaultBinding title, AppCompatTextView tvBatteryTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRelatedTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.batteryGo = batteryGo;
        this.deviceLogGo = deviceLogGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.iv = iv;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.layoutBattery = layoutBattery;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceLog = layoutDeviceLog;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutProductName = layoutProductName;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutUpgrade = layoutUpgrade;
        this.title = title;
        this.tvBatteryTip = tvBatteryTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRelatedTip = tvRelatedTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDeviceSettingDefaultVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDeviceSettingDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceSettingDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDeviceSettingDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_setting_default, root, attachToRoot, component);
    }

    public static ActDeviceSettingDefaultBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceSettingDefaultBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDeviceSettingDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_setting_default, null, false, component);
    }

    public static ActDeviceSettingDefaultBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceSettingDefaultBinding bind(View view, Object component) {
        return (ActDeviceSettingDefaultBinding) bind(component, view, R.layout.act_device_setting_default);
    }
}