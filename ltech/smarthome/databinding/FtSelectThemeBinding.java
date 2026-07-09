package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtSelectThemeBinding extends ViewDataBinding {
    public final RecyclerView rvContent;

    protected FtSelectThemeBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
    }

    public static FtSelectThemeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSelectThemeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSelectThemeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_select_theme, root, attachToRoot, component);
    }

    public static FtSelectThemeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSelectThemeBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSelectThemeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_select_theme, null, false, component);
    }

    public static FtSelectThemeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSelectThemeBinding bind(View view, Object component) {
        return (FtSelectThemeBinding) bind(component, view, R.layout.ft_select_theme);
    }
}