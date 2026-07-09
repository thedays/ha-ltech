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
public abstract class ItemDeviceLogBinding extends ViewDataBinding {
    public final AppCompatImageView dotView;
    public final View timeLine;
    public final AppCompatTextView tvLog;
    public final AppCompatTextView tvTime;

    protected ItemDeviceLogBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView dotView, View timeLine, AppCompatTextView tvLog, AppCompatTextView tvTime) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dotView = dotView;
        this.timeLine = timeLine;
        this.tvLog = tvLog;
        this.tvTime = tvTime;
    }

    public static ItemDeviceLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDeviceLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_log, root, attachToRoot, component);
    }

    public static ItemDeviceLogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceLogBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDeviceLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_device_log, null, false, component);
    }

    public static ItemDeviceLogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDeviceLogBinding bind(View view, Object component) {
        return (ItemDeviceLogBinding) bind(component, view, R.layout.item_device_log);
    }
}