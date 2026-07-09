package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtDeviceAndGroupBinding extends ViewDataBinding {
    public final RecyclerView rvDevice;

    protected FtDeviceAndGroupBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvDevice) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvDevice = rvDevice;
    }

    public static FtDeviceAndGroupBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDeviceAndGroupBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtDeviceAndGroupBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_device_and_group, root, attachToRoot, component);
    }

    public static FtDeviceAndGroupBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDeviceAndGroupBinding inflate(LayoutInflater inflater, Object component) {
        return (FtDeviceAndGroupBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_device_and_group, null, false, component);
    }

    public static FtDeviceAndGroupBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDeviceAndGroupBinding bind(View view, Object component) {
        return (FtDeviceAndGroupBinding) bind(component, view, R.layout.ft_device_and_group);
    }
}