package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtSpiPlayListBinding extends ViewDataBinding {
    public final RecyclerView rvContent;

    protected FtSpiPlayListBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
    }

    public static FtSpiPlayListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSpiPlayListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSpiPlayListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_spi_play_list, root, attachToRoot, component);
    }

    public static FtSpiPlayListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSpiPlayListBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSpiPlayListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_spi_play_list, null, false, component);
    }

    public static FtSpiPlayListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSpiPlayListBinding bind(View view, Object component) {
        return (FtSpiPlayListBinding) bind(component, view, R.layout.ft_spi_play_list);
    }
}