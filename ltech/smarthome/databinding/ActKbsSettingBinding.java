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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActKbsSettingBinding extends ViewDataBinding {
    public final AppCompatImageView deviceLogGo;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivControlTypeGo;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivLineGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivPowerOffSceneDelayGo;
    public final AppCompatImageView ivPowerOffSceneGo;
    public final AppCompatImageView ivPowerOnSceneDelayGo;
    public final AppCompatImageView ivPowerOnSceneGo;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivStateGo;
    public final RelativeLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutControlType;
    public final RelativeLayout layoutCreateGroup;
    public final ConstraintLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceLog;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final RelativeLayout layoutDuv;
    public final RelativeLayout layoutLineSet;
    public final RelativeLayout layoutMacAddress;
    public final ConstraintLayout layoutPowerOffScene;
    public final ConstraintLayout layoutPowerOffSceneDelay;
    public final ConstraintLayout layoutPowerOnScene;
    public final ConstraintLayout layoutPowerOnSceneDelay;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutRestoreFactory;
    public final ConstraintLayout layoutSetOnState;
    public final RelativeLayout layoutUpgrade;
    public final RelativeLayout layoutWhiteBalance;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActKbsSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvLightSetting;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvControlType;
    public final AppCompatTextView tvControlTypeState;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvLightOnState;
    public final AppCompatTextView tvLine;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvPowerOffScene;
    public final AppCompatTextView tvPowerOffSceneDelay;
    public final AppCompatTextView tvPowerOffSceneName;
    public final AppCompatTextView tvPowerOffSceneNameDelay;
    public final AppCompatTextView tvPowerOnScene;
    public final AppCompatTextView tvPowerOnSceneDelay;
    public final AppCompatTextView tvPowerOnSceneName;
    public final AppCompatTextView tvPowerOnSceneNameDelay;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActKbsSettingVM viewmodel);

    protected ActKbsSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView deviceLogGo, AppCompatImageView deviceReplaceGo, AppCompatImageView iv, AppCompatImageView ivControlTypeGo, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivIcon, AppCompatImageView ivLineGo, AppCompatImageView ivMacAddress, AppCompatImageView ivPowerOffSceneDelayGo, AppCompatImageView ivPowerOffSceneGo, AppCompatImageView ivPowerOnSceneDelayGo, AppCompatImageView ivPowerOnSceneGo, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivStateGo, RelativeLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutControlType, RelativeLayout layoutCreateGroup, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, RelativeLayout layoutDuv, RelativeLayout layoutLineSet, RelativeLayout layoutMacAddress, ConstraintLayout layoutPowerOffScene, ConstraintLayout layoutPowerOffSceneDelay, ConstraintLayout layoutPowerOnScene, ConstraintLayout layoutPowerOnSceneDelay, RelativeLayout layoutProductName, RelativeLayout layoutRestoreFactory, ConstraintLayout layoutSetOnState, RelativeLayout layoutUpgrade, RelativeLayout layoutWhiteBalance, SmartRefreshLayout refreshLayout, RecyclerView rvLightSetting, LayoutTitleDefaultBinding title, AppCompatTextView tvControlType, AppCompatTextView tvControlTypeState, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvLightOnState, AppCompatTextView tvLine, AppCompatTextView tvNameTip, AppCompatTextView tvPowerOffScene, AppCompatTextView tvPowerOffSceneDelay, AppCompatTextView tvPowerOffSceneName, AppCompatTextView tvPowerOffSceneNameDelay, AppCompatTextView tvPowerOnScene, AppCompatTextView tvPowerOnSceneDelay, AppCompatTextView tvPowerOnSceneName, AppCompatTextView tvPowerOnSceneNameDelay, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvState, AppCompatTextView tvTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.deviceLogGo = deviceLogGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.iv = iv;
        this.ivControlTypeGo = ivControlTypeGo;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivIcon = ivIcon;
        this.ivLineGo = ivLineGo;
        this.ivMacAddress = ivMacAddress;
        this.ivPowerOffSceneDelayGo = ivPowerOffSceneDelayGo;
        this.ivPowerOffSceneGo = ivPowerOffSceneGo;
        this.ivPowerOnSceneDelayGo = ivPowerOnSceneDelayGo;
        this.ivPowerOnSceneGo = ivPowerOnSceneGo;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivStateGo = ivStateGo;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutControlType = layoutControlType;
        this.layoutCreateGroup = layoutCreateGroup;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceLog = layoutDeviceLog;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutDuv = layoutDuv;
        this.layoutLineSet = layoutLineSet;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutPowerOffScene = layoutPowerOffScene;
        this.layoutPowerOffSceneDelay = layoutPowerOffSceneDelay;
        this.layoutPowerOnScene = layoutPowerOnScene;
        this.layoutPowerOnSceneDelay = layoutPowerOnSceneDelay;
        this.layoutProductName = layoutProductName;
        this.layoutRestoreFactory = layoutRestoreFactory;
        this.layoutSetOnState = layoutSetOnState;
        this.layoutUpgrade = layoutUpgrade;
        this.layoutWhiteBalance = layoutWhiteBalance;
        this.refreshLayout = refreshLayout;
        this.rvLightSetting = rvLightSetting;
        this.title = title;
        this.tvControlType = tvControlType;
        this.tvControlTypeState = tvControlTypeState;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvLightOnState = tvLightOnState;
        this.tvLine = tvLine;
        this.tvNameTip = tvNameTip;
        this.tvPowerOffScene = tvPowerOffScene;
        this.tvPowerOffSceneDelay = tvPowerOffSceneDelay;
        this.tvPowerOffSceneName = tvPowerOffSceneName;
        this.tvPowerOffSceneNameDelay = tvPowerOffSceneNameDelay;
        this.tvPowerOnScene = tvPowerOnScene;
        this.tvPowerOnSceneDelay = tvPowerOnSceneDelay;
        this.tvPowerOnSceneName = tvPowerOnSceneName;
        this.tvPowerOnSceneNameDelay = tvPowerOnSceneNameDelay;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvState = tvState;
        this.tvTip = tvTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActKbsSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActKbsSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKbsSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActKbsSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_kbs_setting, root, attachToRoot, component);
    }

    public static ActKbsSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKbsSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActKbsSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_kbs_setting, null, false, component);
    }

    public static ActKbsSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKbsSettingBinding bind(View view, Object component) {
        return (ActKbsSettingBinding) bind(component, view, R.layout.act_kbs_setting);
    }
}