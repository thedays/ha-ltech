package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewRemoteTipBinding extends ViewDataBinding {
    protected ViewRemoteTipBinding(Object _bindingComponent, View _root, int _localFieldCount) {
        super(_bindingComponent, _root, _localFieldCount);
    }

    public static ViewRemoteTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewRemoteTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewRemoteTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_remote_tip, root, attachToRoot, component);
    }

    public static ViewRemoteTipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewRemoteTipBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewRemoteTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_remote_tip, null, false, component);
    }

    public static ViewRemoteTipBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewRemoteTipBinding bind(View view, Object component) {
        return (ViewRemoteTipBinding) bind(component, view, R.layout.view_remote_tip);
    }
}