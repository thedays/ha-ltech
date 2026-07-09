package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSelectDeviceIconBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvName;

    protected ItemSelectDeviceIconBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
    }

    public static ItemSelectDeviceIconBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceIconBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectDeviceIconBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_icon, root, attachToRoot, component);
    }

    public static ItemSelectDeviceIconBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceIconBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectDeviceIconBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_icon, null, false, component);
    }

    public static ItemSelectDeviceIconBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceIconBinding bind(View view, Object component) {
        return (ItemSelectDeviceIconBinding) bind(component, view, R.layout.item_select_device_icon);
    }
}