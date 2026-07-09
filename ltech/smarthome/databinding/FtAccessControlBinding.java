package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtAccessControlBinding extends ViewDataBinding {
    public final RecyclerView rvDevice;

    protected FtAccessControlBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvDevice) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvDevice = rvDevice;
    }

    public static FtAccessControlBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAccessControlBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtAccessControlBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_access_control, root, attachToRoot, component);
    }

    public static FtAccessControlBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAccessControlBinding inflate(LayoutInflater inflater, Object component) {
        return (FtAccessControlBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_access_control, null, false, component);
    }

    public static FtAccessControlBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAccessControlBinding bind(View view, Object component) {
        return (FtAccessControlBinding) bind(component, view, R.layout.ft_access_control);
    }
}