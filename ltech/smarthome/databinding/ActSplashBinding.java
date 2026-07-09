package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ActSplashBinding extends ViewDataBinding {
    protected ActSplashBinding(Object _bindingComponent, View _root, int _localFieldCount) {
        super(_bindingComponent, _root, _localFieldCount);
    }

    public static ActSplashBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSplashBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSplashBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_splash, root, attachToRoot, component);
    }

    public static ActSplashBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSplashBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSplashBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_splash, null, false, component);
    }

    public static ActSplashBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSplashBinding bind(View view, Object component) {
        return (ActSplashBinding) bind(component, view, R.layout.act_splash);
    }
}