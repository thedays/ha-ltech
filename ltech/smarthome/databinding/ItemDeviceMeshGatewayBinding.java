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
public abstract class ItemDeviceMeshGatewayBinding extends ViewDataBinding {
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

    @Bindable
    protected String mOnlineString;

    @Bindable
    protected Integer mOnlineTextBgRes;
    public final AppCompatTextView tvVirtual;
    public final AppCompatTextView tvWrite;
    public final View vFavorite;
    public final View view10;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDevice(Device device);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setOnlineString(String onlineString);

    public abstract void setOnlineTextBgRes(Integer onlineTextBgRes);

    protected ItemDeviceMeshGatewayBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView9, AppCompatTextView appCompatTextView15, AppCompatTextView appCompatTextView16, AppCompatTextView appCompatTextView17, LinearLayoutCompat bg, AppCompatImageView ivDeviceMore, AppCompatImageView ivFavorite, ConstraintLayout layoutItemBg, AppCompatTextView tvVirtual, AppCompatTextView tvWrite, View vFavorite, View view10) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView9 = appCompatImageView9;
        this.appCompatTextView15 = appCompatTextView15;
        this.appCompatTextView16 = appCompatTextView16;
        this.appCompatTextView17 = appCompatTextView17;
        this.bg = bg;
        this.ivDeviceMore = ivDeviceMore;
        this.ivFavorite = ivFavorite;
        this.layoutItemBg = layoutItemBg;
        this.tvVirtual = tvVirtual;
        this.tvWrite = tvWrite;
        this.vFavorite = vFavorite;
        this.view10 = view10;
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

    public String getOnlineString() {
        return this.mOnlineString;
    }

    public Integer getOnlineTextBgRes() {
        return this.mOnlineTextBgRes;
    }

    public static ItemDeviceMeshGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceMeshGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDeviceMeshGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_mesh_gateway, root, attachToRoot, component);
    }

    public static ItemDeviceMeshGatewayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceMeshGatewayBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDeviceMeshGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_mesh_gateway, null, false, component);
    }

    public static ItemDeviceMeshGatewayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceMeshGatewayBinding bind(View view, Object component) {
        return (ItemDeviceMeshGatewayBinding) bind(component, view, R.layout.item_device_mesh_gateway);
    }
}