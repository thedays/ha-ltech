package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;

/* loaded from: classes3.dex */
public abstract class ItemDeviceManageBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivIcon;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Device mDevice;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected String mPlaceInfo;
    public final AppCompatTextView tvDali;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvPlaceInfo;
    public final AppCompatTextView tvVirtual;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDevice(Device device);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setPlaceInfo(String placeInfo);

    protected ItemDeviceManageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatImageView ivIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvDali, AppCompatTextView tvDeviceName, AppCompatTextView tvPlaceInfo, AppCompatTextView tvVirtual) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.ivIcon = ivIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvDali = tvDali;
        this.tvDeviceName = tvDeviceName;
        this.tvPlaceInfo = tvPlaceInfo;
        this.tvVirtual = tvVirtual;
    }

    public Device getDevice() {
        return this.mDevice;
    }

    public String getPlaceInfo() {
        return this.mPlaceInfo;
    }

    public Integer getIconRes() {
        return this.mIconRes;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemDeviceManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDeviceManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_manage, root, attachToRoot, component);
    }

    public static ItemDeviceManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDeviceManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_manage, null, false, component);
    }

    public static ItemDeviceManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceManageBinding bind(View view, Object component) {
        return (ItemDeviceManageBinding) bind(component, view, R.layout.item_device_manage);
    }
}