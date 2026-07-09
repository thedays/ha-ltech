package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtListBinding extends ViewDataBinding {
    public final FrameLayout layoutLoad;
    public final RecyclerView rv;

    protected FtListBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutLoad, RecyclerView rv) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.rv = rv;
    }

    public static FtListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_list, root, attachToRoot, component);
    }

    public static FtListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtListBinding inflate(LayoutInflater inflater, Object component) {
        return (FtListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_list, null, false, component);
    }

    public static FtListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtListBinding bind(View view, Object component) {
        return (FtListBinding) bind(component, view, R.layout.ft_list);
    }
}