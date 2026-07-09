package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtSpiModeBinding extends ViewDataBinding {
    public final RecyclerView rvContent;

    protected FtSpiModeBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
    }

    public static FtSpiModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSpiModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSpiModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_spi_mode, root, attachToRoot, component);
    }

    public static FtSpiModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSpiModeBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSpiModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_spi_mode, null, false, component);
    }

    public static FtSpiModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSpiModeBinding bind(View view, Object component) {
        return (FtSpiModeBinding) bind(component, view, R.layout.ft_spi_mode);
    }
}