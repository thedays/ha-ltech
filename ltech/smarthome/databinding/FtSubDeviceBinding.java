package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtSubDeviceBinding extends ViewDataBinding {
    public final RecyclerView rvDevice;
    public final TextView tvCount;

    protected FtSubDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvDevice, TextView tvCount) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvDevice = rvDevice;
        this.tvCount = tvCount;
    }

    public static FtSubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_sub_device, root, attachToRoot, component);
    }

    public static FtSubDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSubDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_sub_device, null, false, component);
    }

    public static FtSubDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSubDeviceBinding bind(View view, Object component) {
        return (FtSubDeviceBinding) bind(component, view, R.layout.ft_sub_device);
    }
}