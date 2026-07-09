package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;

/* loaded from: classes3.dex */
public abstract class ItemDeviceBleBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView9;
    public final AppCompatTextView appCompatTextView15;
    public final AppCompatTextView appCompatTextView16;
    public final AppCompatTextView appCompatTextView17;
    public final LinearLayoutCompat bg;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivFavorite;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Device mDevice;

    @Bindable
    protected Integer mIconRes;
    public final View vFavorite;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDevice(Device device);

    public abstract void setIconRes(Integer iconRes);

    protected ItemDeviceBleBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView9, AppCompatTextView appCompatTextView15, AppCompatTextView appCompatTextView16, AppCompatTextView appCompatTextView17, LinearLayoutCompat bg, AppCompatImageView ivDeviceMore, AppCompatImageView ivFavorite, ConstraintLayout layoutItemBg, View vFavorite) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView9 = appCompatImageView9;
        this.appCompatTextView15 = appCompatTextView15;
        this.appCompatTextView16 = appCompatTextView16;
        this.appCompatTextView17 = appCompatTextView17;
        this.bg = bg;
        this.ivDeviceMore = ivDeviceMore;
        this.ivFavorite = ivFavorite;
        this.layoutItemBg = layoutItemBg;
        this.vFavorite = vFavorite;
    }

    public Device getDevice() {
        return this.mDevice;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Integer getIconRes() {
        return this.mIconRes;
    }

    public static ItemDeviceBleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceBleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDeviceBleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_ble, root, attachToRoot, component);
    }

    public static ItemDeviceBleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceBleBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDeviceBleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_ble, null, false, component);
    }

    public static ItemDeviceBleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceBleBinding bind(View view, Object component) {
        return (ItemDeviceBleBinding) bind(component, view, R.layout.item_device_ble);
    }
}