package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtTvExtFunBinding extends ViewDataBinding {
    public final RecyclerView rvContent;

    protected FtTvExtFunBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
    }

    public static FtTvExtFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtTvExtFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtTvExtFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_tv_ext_fun, root, attachToRoot, component);
    }

    public static FtTvExtFunBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtTvExtFunBinding inflate(LayoutInflater inflater, Object component) {
        return (FtTvExtFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_tv_ext_fun, null, false, component);
    }

    public static FtTvExtFunBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtTvExtFunBinding bind(View view, Object component) {
        return (FtTvExtFunBinding) bind(component, view, R.layout.ft_tv_ext_fun);
    }
}