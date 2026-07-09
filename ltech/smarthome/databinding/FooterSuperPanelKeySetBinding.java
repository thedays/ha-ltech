package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FooterSuperPanelKeySetBinding extends ViewDataBinding {
    public final View view31;

    protected FooterSuperPanelKeySetBinding(Object _bindingComponent, View _root, int _localFieldCount, View view31) {
        super(_bindingComponent, _root, _localFieldCount);
        this.view31 = view31;
    }

    public static FooterSuperPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FooterSuperPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FooterSuperPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.footer_super_panel_key_set, root, attachToRoot, component);
    }

    public static FooterSuperPanelKeySetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FooterSuperPanelKeySetBinding inflate(LayoutInflater inflater, Object component) {
        return (FooterSuperPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.footer_super_panel_key_set, null, false, component);
    }

    public static FooterSuperPanelKeySetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FooterSuperPanelKeySetBinding bind(View view, Object component) {
        return (FooterSuperPanelKeySetBinding) bind(component, view, R.layout.footer_super_panel_key_set);
    }
}