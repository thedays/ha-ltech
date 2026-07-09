package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewDeviceManageBottomBinding extends ViewDataBinding {
    public final LinearLayout btnCancel;
    public final LinearLayout btnChangeRoom;
    public final LinearLayout btnFindDevice;
    public final LinearLayout btnStartLocate;
    public final AppCompatImageView ivChangeRoom;
    public final AppCompatImageView ivStartLocate;
    public final ConstraintLayout layoutDeviceManage;
    public final AppCompatTextView tvChangeRoom;
    public final AppCompatTextView tvStartLocate;

    protected ViewDeviceManageBottomBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout btnCancel, LinearLayout btnChangeRoom, LinearLayout btnFindDevice, LinearLayout btnStartLocate, AppCompatImageView ivChangeRoom, AppCompatImageView ivStartLocate, ConstraintLayout layoutDeviceManage, AppCompatTextView tvChangeRoom, AppCompatTextView tvStartLocate) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnCancel = btnCancel;
        this.btnChangeRoom = btnChangeRoom;
        this.btnFindDevice = btnFindDevice;
        this.btnStartLocate = btnStartLocate;
        this.ivChangeRoom = ivChangeRoom;
        this.ivStartLocate = ivStartLocate;
        this.layoutDeviceManage = layoutDeviceManage;
        this.tvChangeRoom = tvChangeRoom;
        this.tvStartLocate = tvStartLocate;
    }

    public static ViewDeviceManageBottomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDeviceManageBottomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewDeviceManageBottomBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_device_manage_bottom, root, attachToRoot, component);
    }

    public static ViewDeviceManageBottomBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDeviceManageBottomBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewDeviceManageBottomBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_device_manage_bottom, null, false, component);
    }

    public static ViewDeviceManageBottomBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDeviceManageBottomBinding bind(View view, Object component) {
        return (ViewDeviceManageBottomBinding) bind(component, view, R.layout.view_device_manage_bottom);
    }
}