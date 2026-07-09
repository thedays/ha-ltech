package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ActGetPermissionBinding extends ViewDataBinding {
    protected ActGetPermissionBinding(Object _bindingComponent, View _root, int _localFieldCount) {
        super(_bindingComponent, _root, _localFieldCount);
    }

    public static ActGetPermissionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGetPermissionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActGetPermissionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_get_permission, root, attachToRoot, component);
    }

    public static ActGetPermissionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGetPermissionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActGetPermissionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_get_permission, null, false, component);
    }

    public static ActGetPermissionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGetPermissionBinding bind(View view, Object component) {
        return (ActGetPermissionBinding) bind(component, view, R.layout.act_get_permission);
    }
}