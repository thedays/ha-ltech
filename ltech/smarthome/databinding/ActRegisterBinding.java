package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentContainerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ActRegisterBinding extends ViewDataBinding {
    public final FragmentContainerView fragmentContainer;

    protected ActRegisterBinding(Object _bindingComponent, View _root, int _localFieldCount, FragmentContainerView fragmentContainer) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
    }

    public static ActRegisterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRegisterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRegisterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_register, root, attachToRoot, component);
    }

    public static ActRegisterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRegisterBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRegisterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_register, null, false, component);
    }

    public static ActRegisterBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRegisterBinding bind(View view, Object component) {
        return (ActRegisterBinding) bind(component, view, R.layout.act_register);
    }
}