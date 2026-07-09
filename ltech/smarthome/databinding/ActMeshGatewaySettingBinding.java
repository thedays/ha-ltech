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
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewaySettingVM;

/* loaded from: classes3.dex */
public abstract class ActMeshGatewaySettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivGoUpgrade;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivTipDot;
    public final RelativeLayout layoutAtmosphereLampSetting;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutSetNetwork;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActMeshGatewaySettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAtmosphereLampTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActMeshGatewaySettingVM viewmodel);

    protected ActMeshGatewaySettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivGoUpgrade, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivTipDot, RelativeLayout layoutAtmosphereLampSetting, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceName, RelativeLayout layoutProductName, RelativeLayout layoutSetNetwork, RelativeLayout layoutUpgrade, LayoutTitleDefaultBinding title, AppCompatTextView tvAtmosphereLampTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceName, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivGoUpgrade = ivGoUpgrade;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivTipDot = ivTipDot;
        this.layoutAtmosphereLampSetting = layoutAtmosphereLampSetting;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutProductName = layoutProductName;
        this.layoutSetNetwork = layoutSetNetwork;
        this.layoutUpgrade = layoutUpgrade;
        this.title = title;
        this.tvAtmosphereLampTip = tvAtmosphereLampTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceName = tvDeviceName;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActMeshGatewaySettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActMeshGatewaySettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshGatewaySettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMeshGatewaySettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_gateway_setting, root, attachToRoot, component);
    }

    public static ActMeshGatewaySettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshGatewaySettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMeshGatewaySettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_gateway_setting, null, false, component);
    }

    public static ActMeshGatewaySettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshGatewaySettingBinding bind(View view, Object component) {
        return (ActMeshGatewaySettingBinding) bind(component, view, R.layout.act_mesh_gateway_setting);
    }
}