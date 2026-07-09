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
import com.ltech.smarthome.ui.device.setting.ActSubDeviceSettingDefaultVM;

/* loaded from: classes3.dex */
public abstract class ActSubDeviceSettingDefaultBinding extends ViewDataBinding {
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSubDeviceNameGo;
    public final RelativeLayout layoutAcBrand;
    public final RelativeLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceName;
    public final ConstraintLayout layoutSubDevice;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSubDeviceSettingDefaultVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAcBrand;
    public final AppCompatTextView tvBrandTitle;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDeviceTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSubordinateDevice;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSubDeviceSettingDefaultVM viewmodel);

    protected ActSubDeviceSettingDefaultBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivIcon, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSubDeviceNameGo, RelativeLayout layoutAcBrand, RelativeLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceName, ConstraintLayout layoutSubDevice, LayoutTitleDefaultBinding title, AppCompatTextView tvAcBrand, AppCompatTextView tvBrandTitle, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceName, AppCompatTextView tvDeviceTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSubordinateDevice) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivIcon = ivIcon;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSubDeviceNameGo = ivSubDeviceNameGo;
        this.layoutAcBrand = layoutAcBrand;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutSubDevice = layoutSubDevice;
        this.title = title;
        this.tvAcBrand = tvAcBrand;
        this.tvBrandTitle = tvBrandTitle;
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

    public ActSubDeviceSettingDefaultVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSubDeviceSettingDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSubDeviceSettingDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSubDeviceSettingDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sub_device_setting_default, root, attachToRoot, component);
    }

    public static ActSubDeviceSettingDefaultBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSubDeviceSettingDefaultBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSubDeviceSettingDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sub_device_setting_default, null, false, component);
    }

    public static ActSubDeviceSettingDefaultBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSubDeviceSettingDefaultBinding bind(View view, Object component) {
        return (ActSubDeviceSettingDefaultBinding) bind(component, view, R.layout.act_sub_device_setting_default);
    }
}