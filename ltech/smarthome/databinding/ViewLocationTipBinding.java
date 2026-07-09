package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewLocationTipBinding extends ViewDataBinding {
    protected ViewLocationTipBinding(Object _bindingComponent, View _root, int _localFieldCount) {
        super(_bindingComponent, _root, _localFieldCount);
    }

    public static ViewLocationTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewLocationTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewLocationTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_location_tip, root, attachToRoot, component);
    }

    public static ViewLocationTipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewLocationTipBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewLocationTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_location_tip, null, false, component);
    }

    public static ViewLocationTipBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewLocationTipBinding bind(View view, Object component) {
        return (ViewLocationTipBinding) bind(component, view, R.layout.view_location_tip);
    }
}