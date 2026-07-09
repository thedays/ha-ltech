package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewEurTipZoneBinding extends ViewDataBinding {
    protected ViewEurTipZoneBinding(Object _bindingComponent, View _root, int _localFieldCount) {
        super(_bindingComponent, _root, _localFieldCount);
    }

    public static ViewEurTipZoneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEurTipZoneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewEurTipZoneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_eur_tip_zone, root, attachToRoot, component);
    }

    public static ViewEurTipZoneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEurTipZoneBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewEurTipZoneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_eur_tip_zone, null, false, component);
    }

    public static ViewEurTipZoneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEurTipZoneBinding bind(View view, Object component) {
        return (ViewEurTipZoneBinding) bind(component, view, R.layout.view_eur_tip_zone);
    }
}