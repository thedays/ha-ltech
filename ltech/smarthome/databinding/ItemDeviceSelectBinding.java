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
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;

/* loaded from: classes3.dex */
public abstract class ItemDeviceSelectBinding extends ViewDataBinding {
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivIcon;
    public final RelativeLayout layoutEdit;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Device mDevice;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected String mPlaceInfo;

    @Bindable
    protected Boolean mSelect;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvPlaceInfo;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDevice(Device device);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setPlaceInfo(String placeInfo);

    public abstract void setSelect(Boolean select);

    protected ItemDeviceSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEdit, AppCompatImageView ivGo, AppCompatImageView ivIcon, RelativeLayout layoutEdit, ConstraintLayout layoutItemBg, AppCompatTextView tvDeviceName, AppCompatTextView tvPlaceInfo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEdit = ivEdit;
        this.ivGo = ivGo;
        this.ivIcon = ivIcon;
        this.layoutEdit = layoutEdit;
        this.layoutItemBg = layoutItemBg;
        this.tvDeviceName = tvDeviceName;
        this.tvPlaceInfo = tvPlaceInfo;
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

    public Boolean getSelect() {
        return this.mSelect;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemDeviceSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDeviceSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_select, root, attachToRoot, component);
    }

    public static ItemDeviceSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDeviceSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_select, null, false, component);
    }

    public static ItemDeviceSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceSelectBinding bind(View view, Object component) {
        return (ItemDeviceSelectBinding) bind(component, view, R.layout.item_device_select);
    }
}