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
public abstract class ItemGroupDeviceSortBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivLocation;
    public final AppCompatImageView ivSort;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Device mDevice;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected String mPlaceInfo;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvPlaceInfo;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDevice(Device device);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setPlaceInfo(String placeInfo);

    protected ItemGroupDeviceSortBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivLocation, AppCompatImageView ivSort, ConstraintLayout layoutItemBg, AppCompatTextView tvDeviceName, AppCompatTextView tvPlaceInfo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivLocation = ivLocation;
        this.ivSort = ivSort;
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

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemGroupDeviceSortBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupDeviceSortBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemGroupDeviceSortBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_device_sort, root, attachToRoot, component);
    }

    public static ItemGroupDeviceSortBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupDeviceSortBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemGroupDeviceSortBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_device_sort, null, false, component);
    }

    public static ItemGroupDeviceSortBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupDeviceSortBinding bind(View view, Object component) {
        return (ItemGroupDeviceSortBinding) bind(component, view, R.layout.item_group_device_sort);
    }
}