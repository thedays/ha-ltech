package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtSelectIconsBinding extends ViewDataBinding {
    public final RecyclerView rvContent;

    protected FtSelectIconsBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
    }

    public static FtSelectIconsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSelectIconsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSelectIconsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_select_icons, root, attachToRoot, component);
    }

    public static FtSelectIconsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSelectIconsBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSelectIconsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_select_icons, null, false, component);
    }

    public static FtSelectIconsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSelectIconsBinding bind(View view, Object component) {
        return (FtSelectIconsBinding) bind(component, view, R.layout.ft_select_icons);
    }
}