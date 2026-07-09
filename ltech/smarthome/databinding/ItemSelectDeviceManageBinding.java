package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSelectDeviceManageBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatTextView tvName;

    protected ItemSelectDeviceManageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.tvName = tvName;
    }

    public static ItemSelectDeviceManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectDeviceManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_manage, root, attachToRoot, component);
    }

    public static ItemSelectDeviceManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectDeviceManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_manage, null, false, component);
    }

    public static ItemSelectDeviceManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceManageBinding bind(View view, Object component) {
        return (ItemSelectDeviceManageBinding) bind(component, view, R.layout.item_select_device_manage);
    }
}