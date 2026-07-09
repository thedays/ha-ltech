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
import com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSettingVM;

/* loaded from: classes3.dex */
public abstract class ActBleTrigSceneSettingBinding extends ViewDataBinding {
    public final AppCompatImageView deviceLogGo;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivControlModeGo;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutControlMode;
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
    protected ActBleTrigSceneSettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvControlModeName;
    public final AppCompatTextView tvControlModeTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActBleTrigSceneSettingVM viewmodel);

    protected ActBleTrigSceneSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView deviceLogGo, AppCompatImageView deviceReplaceGo, AppCompatImageView iv, AppCompatImageView ivControlModeGo, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutControlMode, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, RelativeLayout layoutMacAddress, RelativeLayout layoutProductName, RelativeLayout layoutUpgrade, LayoutTitleDefaultBinding title, AppCompatTextView tvControlModeName, AppCompatTextView tvControlModeTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.deviceLogGo = deviceLogGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.iv = iv;
        this.ivControlModeGo = ivControlModeGo;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutControlMode = layoutControlMode;
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

    public ActBleTrigSceneSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActBleTrigSceneSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleTrigSceneSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBleTrigSceneSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_trig_scene_setting, root, attachToRoot, component);
    }

    public static ActBleTrigSceneSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleTrigSceneSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBleTrigSceneSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_trig_scene_setting, null, false, component);
    }

    public static ActBleTrigSceneSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleTrigSceneSettingBinding bind(View view, Object component) {
        return (ActBleTrigSceneSettingBinding) bind(component, view, R.layout.act_ble_trig_scene_setting);
    }
}