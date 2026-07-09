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
import com.ltech.smarthome.ui.device.aspanel.ActAsPanelSettingVM;

/* loaded from: classes3.dex */
public abstract class ActAsPanelSettingBinding extends ViewDataBinding {
    public final AppCompatImageView iv;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceId;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutOnOffTime;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutSetDmxType;
    public final RelativeLayout layoutSetOnState;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAsPanelSettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAsPanelSettingVM viewmodel);

    protected ActAsPanelSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView iv, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceId, ConstraintLayout layoutDeviceName, RelativeLayout layoutMacAddress, RelativeLayout layoutOnOffTime, RelativeLayout layoutProductName, RelativeLayout layoutSetDmxType, RelativeLayout layoutSetOnState, RelativeLayout layoutUpgrade, LayoutTitleDefaultBinding title, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.iv = iv;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutOnOffTime = layoutOnOffTime;
        this.layoutProductName = layoutProductName;
        this.layoutSetDmxType = layoutSetDmxType;
        this.layoutSetOnState = layoutSetOnState;
        this.layoutUpgrade = layoutUpgrade;
        this.title = title;
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

    public ActAsPanelSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAsPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAsPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAsPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_as_panel_setting, root, attachToRoot, component);
    }

    public static ActAsPanelSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAsPanelSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAsPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_as_panel_setting, null, false, component);
    }

    public static ActAsPanelSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAsPanelSettingBinding bind(View view, Object component) {
        return (ActAsPanelSettingBinding) bind(component, view, R.layout.act_as_panel_setting);
    }
}