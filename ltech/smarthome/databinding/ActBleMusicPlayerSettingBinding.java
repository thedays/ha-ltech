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
import com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSettingVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActBleMusicPlayerSettingBinding extends ViewDataBinding {
    public final AppCompatImageView iv;
    public final AppCompatImageView ivBluetoothStateGo;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivDeviceStatusAfterPowerOnGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final ConstraintLayout layoutBluetoothState;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceId;
    public final ConstraintLayout layoutDeviceName;
    public final ConstraintLayout layoutDeviceStatusAfterPowerOn;
    public final ConstraintLayout layoutIgnoreCurrentConnections;
    public final RelativeLayout layoutMacAddress;
    public final ConstraintLayout layoutManagerPlaylist;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActBleMusicPlayerSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBluetoothState;
    public final AppCompatTextView tvBluetoothStateTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDeviceStatusAfterPowerOn;
    public final AppCompatTextView tvDeviceStatusAfterPowerOnTip;
    public final AppCompatTextView tvIgnoreCurrentConnectionsTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActBleMusicPlayerSettingVM viewmodel);

    protected ActBleMusicPlayerSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView iv, AppCompatImageView ivBluetoothStateGo, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivDeviceStatusAfterPowerOnGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, ConstraintLayout layoutBluetoothState, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceId, ConstraintLayout layoutDeviceName, ConstraintLayout layoutDeviceStatusAfterPowerOn, ConstraintLayout layoutIgnoreCurrentConnections, RelativeLayout layoutMacAddress, ConstraintLayout layoutManagerPlaylist, RelativeLayout layoutProductName, RelativeLayout layoutUpgrade, SmartRefreshLayout refreshLayout, LayoutTitleDefaultBinding title, AppCompatTextView tvBluetoothState, AppCompatTextView tvBluetoothStateTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvDeviceStatusAfterPowerOn, AppCompatTextView tvDeviceStatusAfterPowerOnTip, AppCompatTextView tvIgnoreCurrentConnectionsTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.iv = iv;
        this.ivBluetoothStateGo = ivBluetoothStateGo;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivDeviceStatusAfterPowerOnGo = ivDeviceStatusAfterPowerOnGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.layoutBluetoothState = layoutBluetoothState;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceStatusAfterPowerOn = layoutDeviceStatusAfterPowerOn;
        this.layoutIgnoreCurrentConnections = layoutIgnoreCurrentConnections;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutManagerPlaylist = layoutManagerPlaylist;
        this.layoutProductName = layoutProductName;
        this.layoutUpgrade = layoutUpgrade;
        this.refreshLayout = refreshLayout;
        this.title = title;
        this.tvBluetoothState = tvBluetoothState;
        this.tvBluetoothStateTip = tvBluetoothStateTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvDeviceStatusAfterPowerOn = tvDeviceStatusAfterPowerOn;
        this.tvDeviceStatusAfterPowerOnTip = tvDeviceStatusAfterPowerOnTip;
        this.tvIgnoreCurrentConnectionsTip = tvIgnoreCurrentConnectionsTip;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActBleMusicPlayerSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActBleMusicPlayerSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMusicPlayerSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBleMusicPlayerSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_music_player_setting, root, attachToRoot, component);
    }

    public static ActBleMusicPlayerSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMusicPlayerSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBleMusicPlayerSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_music_player_setting, null, false, component);
    }

    public static ActBleMusicPlayerSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMusicPlayerSettingBinding bind(View view, Object component) {
        return (ActBleMusicPlayerSettingBinding) bind(component, view, R.layout.act_ble_music_player_setting);
    }
}