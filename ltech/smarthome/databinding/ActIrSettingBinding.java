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
import com.ltech.smarthome.ui.device.ir.ActIrSettingVM;

/* loaded from: classes3.dex */
public abstract class ActIrSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSubDeviceNameGo;
    public final RelativeLayout layoutChangeIr;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceName;
    public final ConstraintLayout layoutSubDevice;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIrSettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDeviceTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSubordinateDevice;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIrSettingVM viewmodel);

    protected ActIrSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSubDeviceNameGo, RelativeLayout layoutChangeIr, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceName, ConstraintLayout layoutSubDevice, LayoutTitleDefaultBinding title, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceName, AppCompatTextView tvDeviceTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSubordinateDevice) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSubDeviceNameGo = ivSubDeviceNameGo;
        this.layoutChangeIr = layoutChangeIr;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutSubDevice = layoutSubDevice;
        this.title = title;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceName = tvDeviceName;
        this.tvDeviceTip = tvDeviceTip;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSubordinateDevice = tvSubordinateDevice;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActIrSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIrSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIrSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIrSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ir_setting, root, attachToRoot, component);
    }

    public static ActIrSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIrSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIrSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ir_setting, null, false, component);
    }

    public static ActIrSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIrSettingBinding bind(View view, Object component) {
        return (ActIrSettingBinding) bind(component, view, R.layout.act_ir_setting);
    }
}