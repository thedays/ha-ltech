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
import com.google.android.material.imageview.ShapeableImageView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;

/* loaded from: classes3.dex */
public abstract class ItemDeviceCameraBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView9;
    public final AppCompatTextView appCompatTextView15;
    public final ShapeableImageView ivCamera;
    public final ShapeableImageView ivCamera2;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivEmpty;
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

    @Bindable
    protected Integer mOnlineTextColor;
    public final AppCompatTextView tvEmpty;
    public final View vFavorite;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDevice(Device device);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setOnlineString(String onlineString);

    public abstract void setOnlineTextBgRes(Integer onlineTextBgRes);

    public abstract void setOnlineTextColor(Integer onlineTextColor);

    protected ItemDeviceCameraBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView9, AppCompatTextView appCompatTextView15, ShapeableImageView ivCamera, ShapeableImageView ivCamera2, AppCompatImageView ivDeviceMore, AppCompatImageView ivEmpty, AppCompatImageView ivFavorite, ConstraintLayout layoutItemBg, AppCompatTextView tvEmpty, View vFavorite) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView9 = appCompatImageView9;
        this.appCompatTextView15 = appCompatTextView15;
        this.ivCamera = ivCamera;
        this.ivCamera2 = ivCamera2;
        this.ivDeviceMore = ivDeviceMore;
        this.ivEmpty = ivEmpty;
        this.ivFavorite = ivFavorite;
        this.layoutItemBg = layoutItemBg;
        this.tvEmpty = tvEmpty;
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

    public String getOnlineString() {
        return this.mOnlineString;
    }

    public Integer getOnlineTextBgRes() {
        return this.mOnlineTextBgRes;
    }

    public Integer getOnlineTextColor() {
        return this.mOnlineTextColor;
    }

    public static ItemDeviceCameraBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceCameraBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDeviceCameraBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_camera, root, attachToRoot, component);
    }

    public static ItemDeviceCameraBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceCameraBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDeviceCameraBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_camera, null, false, component);
    }

    public static ItemDeviceCameraBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceCameraBinding bind(View view, Object component) {
        return (ItemDeviceCameraBinding) bind(component, view, R.layout.item_device_camera);
    }
}