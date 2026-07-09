package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ActWelcomeBinding extends ViewDataBinding {
    protected ActWelcomeBinding(Object _bindingComponent, View _root, int _localFieldCount) {
        super(_bindingComponent, _root, _localFieldCount);
    }

    public static ActWelcomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWelcomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActWelcomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_welcome, root, attachToRoot, component);
    }

    public static ActWelcomeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWelcomeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActWelcomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_welcome, null, false, component);
    }

    public static ActWelcomeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWelcomeBinding bind(View view, Object component) {
        return (ActWelcomeBinding) bind(component, view, R.layout.act_welcome);
    }
}