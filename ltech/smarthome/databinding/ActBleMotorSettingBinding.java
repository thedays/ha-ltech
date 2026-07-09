package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ltech.smarthome.ui.device.setting.ActBleMotorSettingVM;

/* loaded from: classes3.dex */
public abstract class ActBleMotorSettingBinding extends ViewDataBinding {
    public final AppCompatButton btnMotorDirection;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivMoreSettingsGo;
    public final AppCompatImageView ivMotorDirectionNameGo;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final ConstraintLayout layoutChangeRoom;
    public final RelativeLayout layoutCreateGroup;
    public final ConstraintLayout layoutDeviceId;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final RelativeLayout layoutMacAddress;
    public final ConstraintLayout layoutMoreSettings;
    public final ConstraintLayout layoutMotorDirection;
    public final RelativeLayout layoutMotorVersion;
    public final RelativeLayout layoutProductName;
    public final ConstraintLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActBleMotorSettingVM mViewmodel;
    public final RecyclerView rvModes;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvMoreSettingsTip;
    public final AppCompatTextView tvMotorDirectionTip;
    public final AppCompatTextView tvMotorVersionTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActBleMotorSettingVM viewmodel);

    protected ActBleMotorSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btnMotorDirection, AppCompatImageView deviceReplaceGo, AppCompatImageView iv, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivMacAddress, AppCompatImageView ivMoreSettingsGo, AppCompatImageView ivMotorDirectionNameGo, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, ConstraintLayout layoutChangeRoom, RelativeLayout layoutCreateGroup, ConstraintLayout layoutDeviceId, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, RelativeLayout layoutMacAddress, ConstraintLayout layoutMoreSettings, ConstraintLayout layoutMotorDirection, RelativeLayout layoutMotorVersion, RelativeLayout layoutProductName, ConstraintLayout layoutUpgrade, RecyclerView rvModes, LayoutTitleDefaultBinding title, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvMoreSettingsTip, AppCompatTextView tvMotorDirectionTip, AppCompatTextView tvMotorVersionTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnMotorDirection = btnMotorDirection;
        this.deviceReplaceGo = deviceReplaceGo;
        this.iv = iv;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivMacAddress = ivMacAddress;
        this.ivMoreSettingsGo = ivMoreSettingsGo;
        this.ivMotorDirectionNameGo = ivMotorDirectionNameGo;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutCreateGroup = layoutCreateGroup;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutMoreSettings = layoutMoreSettings;
        this.layoutMotorDirection = layoutMotorDirection;
        this.layoutMotorVersion = layoutMotorVersion;
        this.layoutProductName = layoutProductName;
        this.layoutUpgrade = layoutUpgrade;
        this.rvModes = rvModes;
        this.title = title;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvMoreSettingsTip = tvMoreSettingsTip;
        this.tvMotorDirectionTip = tvMotorDirectionTip;
        this.tvMotorVersionTip = tvMotorVersionTip;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvTip = tvTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActBleMotorSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActBleMotorSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMotorSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBleMotorSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_motor_setting, root, attachToRoot, component);
    }

    public static ActBleMotorSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMotorSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBleMotorSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_motor_setting, null, false, component);
    }

    public static ActBleMotorSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMotorSettingBinding bind(View view, Object component) {
        return (ActBleMotorSettingBinding) bind(component, view, R.layout.act_ble_motor_setting);
    }
}