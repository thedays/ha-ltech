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
public abstract class ItemDeviceCentralAirGateBinding extends ViewDataBinding {
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
    protected Boolean mHasMeshNetworkFun;

    @Bindable
    protected Integer mIconRes;
    public final AppCompatTextView tvOnline;
    public final View vFavorite;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDevice(Device device);

    public abstract void setHasMeshNetworkFun(Boolean hasMeshNetworkFun);

    public abstract void setIconRes(Integer iconRes);

    protected ItemDeviceCentralAirGateBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView9, AppCompatTextView appCompatTextView15, AppCompatTextView appCompatTextView16, AppCompatTextView appCompatTextView17, LinearLayoutCompat bg, AppCompatImageView ivDeviceMore, AppCompatImageView ivFavorite, ConstraintLayout layoutItemBg, AppCompatTextView tvOnline, View vFavorite) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView9 = appCompatImageView9;
        this.appCompatTextView15 = appCompatTextView15;
        this.appCompatTextView16 = appCompatTextView16;
        this.appCompatTextView17 = appCompatTextView17;
        this.bg = bg;
        this.ivDeviceMore = ivDeviceMore;
        this.ivFavorite = ivFavorite;
        this.layoutItemBg = layoutItemBg;
        this.tvOnline = tvOnline;
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

    public Boolean getHasMeshNetworkFun() {
        return this.mHasMeshNetworkFun;
    }

    public static ItemDeviceCentralAirGateBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceCentralAirGateBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDeviceCentralAirGateBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_central_air_gate, root, attachToRoot, component);
    }

    public static ItemDeviceCentralAirGateBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceCentralAirGateBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDeviceCentralAirGateBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_central_air_gate, null, false, component);
    }

    public static ItemDeviceCentralAirGateBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceCentralAirGateBinding bind(View view, Object component) {
        return (ItemDeviceCentralAirGateBinding) bind(component, view, R.layout.item_device_central_air_gate);
    }
}