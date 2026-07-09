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
import com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSettingVM;

/* loaded from: classes3.dex */
public abstract class ActRs8SubDeviceSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivMoreGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSubDeviceNameGo;
    public final RelativeLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutMore;
    public final ConstraintLayout layoutSubDevice;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActRs8SubDeviceSettingVM mViewmodel;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDeviceTip;
    public final AppCompatTextView tvMore;
    public final AppCompatTextView tvMoreTitle;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSubordinateDevice;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActRs8SubDeviceSettingVM viewmodel);

    protected ActRs8SubDeviceSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivIcon, AppCompatImageView ivMoreGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSubDeviceNameGo, RelativeLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceName, RelativeLayout layoutMore, ConstraintLayout layoutSubDevice, RecyclerView rv, LayoutTitleDefaultBinding title, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceName, AppCompatTextView tvDeviceTip, AppCompatTextView tvMore, AppCompatTextView tvMoreTitle, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSubordinateDevice) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivIcon = ivIcon;
        this.ivMoreGo = ivMoreGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSubDeviceNameGo = ivSubDeviceNameGo;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutMore = layoutMore;
        this.layoutSubDevice = layoutSubDevice;
        this.rv = rv;
        this.title = title;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceName = tvDeviceName;
        this.tvDeviceTip = tvDeviceTip;
        this.tvMore = tvMore;
        this.tvMoreTitle = tvMoreTitle;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSubordinateDevice = tvSubordinateDevice;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActRs8SubDeviceSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActRs8SubDeviceSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8SubDeviceSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRs8SubDeviceSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rs8_sub_device_setting, root, attachToRoot, component);
    }

    public static ActRs8SubDeviceSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8SubDeviceSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRs8SubDeviceSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rs8_sub_device_setting, null, false, component);
    }

    public static ActRs8SubDeviceSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8SubDeviceSettingBinding bind(View view, Object component) {
        return (ActRs8SubDeviceSettingBinding) bind(component, view, R.layout.act_rs8_sub_device_setting);
    }
}