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
import com.ltech.smarthome.ui.device.cg485.ActCg485SettingVM;

/* loaded from: classes3.dex */
public abstract class ActCg485SettingBinding extends ViewDataBinding {
    public final AppCompatImageView deviceLogGo;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivDeviceId;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivSerialGo;
    public final ConstraintLayout layoutChangeRoom;
    public final RelativeLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceLog;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutSceneAndAutomation;
    public final ConstraintLayout layoutSerialParam;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActCg485SettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSerialTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActCg485SettingVM viewmodel);

    protected ActCg485SettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView deviceLogGo, AppCompatImageView deviceReplaceGo, AppCompatImageView iv, AppCompatImageView ivDeviceId, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivSerialGo, ConstraintLayout layoutChangeRoom, RelativeLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, RelativeLayout layoutMacAddress, RelativeLayout layoutProductName, RelativeLayout layoutSceneAndAutomation, ConstraintLayout layoutSerialParam, RelativeLayout layoutUpgrade, LayoutTitleDefaultBinding title, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceName, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSerialTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.deviceLogGo = deviceLogGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.iv = iv;
        this.ivDeviceId = ivDeviceId;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivSerialGo = ivSerialGo;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceLog = layoutDeviceLog;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutProductName = layoutProductName;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSerialParam = layoutSerialParam;
        this.layoutUpgrade = layoutUpgrade;
        this.title = title;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceName = tvDeviceName;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSerialTip = tvSerialTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActCg485SettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActCg485SettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485SettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCg485SettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cg_485_setting, root, attachToRoot, component);
    }

    public static ActCg485SettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485SettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCg485SettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cg_485_setting, null, false, component);
    }

    public static ActCg485SettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485SettingBinding bind(View view, Object component) {
        return (ActCg485SettingBinding) bind(component, view, R.layout.act_cg_485_setting);
    }
}