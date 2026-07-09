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
import com.ltech.smarthome.ui.device.setting.ActBleTrigCurtainSettingVM;

/* loaded from: classes3.dex */
public abstract class ActBleTrigCurtainSettingBinding extends ViewDataBinding {
    public final AppCompatImageView deviceLogGo;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivControlModeGo;
    public final AppCompatImageView ivCurtainChannelSettingGo;
    public final AppCompatImageView ivCurtainControlSettingGo;
    public final AppCompatImageView ivCurtainOpenDirGo;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutControlMode;
    public final RelativeLayout layoutCreateGroup;
    public final ConstraintLayout layoutCurtainChannelSetting;
    public final ConstraintLayout layoutCurtainControlSetting;
    public final ConstraintLayout layoutCurtainOpenDir;
    public final ConstraintLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceLog;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActBleTrigCurtainSettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvControlModeName;
    public final AppCompatTextView tvControlModeTip;
    public final AppCompatTextView tvCurtainChannelSettingName;
    public final AppCompatTextView tvCurtainChannelSettingTip;
    public final AppCompatTextView tvCurtainControlSettingName;
    public final AppCompatTextView tvCurtainControlSettingTip;
    public final AppCompatTextView tvCurtainOpenDirName;
    public final AppCompatTextView tvCurtainOpenDirTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActBleTrigCurtainSettingVM viewmodel);

    protected ActBleTrigCurtainSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView deviceLogGo, AppCompatImageView deviceReplaceGo, AppCompatImageView iv, AppCompatImageView ivControlModeGo, AppCompatImageView ivCurtainChannelSettingGo, AppCompatImageView ivCurtainControlSettingGo, AppCompatImageView ivCurtainOpenDirGo, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutControlMode, RelativeLayout layoutCreateGroup, ConstraintLayout layoutCurtainChannelSetting, ConstraintLayout layoutCurtainControlSetting, ConstraintLayout layoutCurtainOpenDir, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, RelativeLayout layoutMacAddress, RelativeLayout layoutProductName, RelativeLayout layoutUpgrade, LayoutTitleDefaultBinding title, AppCompatTextView tvControlModeName, AppCompatTextView tvControlModeTip, AppCompatTextView tvCurtainChannelSettingName, AppCompatTextView tvCurtainChannelSettingTip, AppCompatTextView tvCurtainControlSettingName, AppCompatTextView tvCurtainControlSettingTip, AppCompatTextView tvCurtainOpenDirName, AppCompatTextView tvCurtainOpenDirTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.deviceLogGo = deviceLogGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.iv = iv;
        this.ivControlModeGo = ivControlModeGo;
        this.ivCurtainChannelSettingGo = ivCurtainChannelSettingGo;
        this.ivCurtainControlSettingGo = ivCurtainControlSettingGo;
        this.ivCurtainOpenDirGo = ivCurtainOpenDirGo;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutControlMode = layoutControlMode;
        this.layoutCreateGroup = layoutCreateGroup;
        this.layoutCurtainChannelSetting = layoutCurtainChannelSetting;
        this.layoutCurtainControlSetting = layoutCurtainControlSetting;
        this.layoutCurtainOpenDir = layoutCurtainOpenDir;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceLog = layoutDeviceLog;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutProductName = layoutProductName;
        this.layoutUpgrade = layoutUpgrade;
        this.title = title;
        this.tvControlModeName = tvControlModeName;
        this.tvControlModeTip = tvControlModeTip;
        this.tvCurtainChannelSettingName = tvCurtainChannelSettingName;
        this.tvCurtainChannelSettingTip = tvCurtainChannelSettingTip;
        this.tvCurtainControlSettingName = tvCurtainControlSettingName;
        this.tvCurtainControlSettingTip = tvCurtainControlSettingTip;
        this.tvCurtainOpenDirName = tvCurtainOpenDirName;
        this.tvCurtainOpenDirTip = tvCurtainOpenDirTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActBleTrigCurtainSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActBleTrigCurtainSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleTrigCurtainSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBleTrigCurtainSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_trig_curtain_setting, root, attachToRoot, component);
    }

    public static ActBleTrigCurtainSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleTrigCurtainSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBleTrigCurtainSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_trig_curtain_setting, null, false, component);
    }

    public static ActBleTrigCurtainSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleTrigCurtainSettingBinding bind(View view, Object component) {
        return (ActBleTrigCurtainSettingBinding) bind(component, view, R.layout.act_ble_trig_curtain_setting);
    }
}