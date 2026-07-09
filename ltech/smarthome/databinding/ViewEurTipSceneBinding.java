package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewEurTipSceneBinding extends ViewDataBinding {
    protected ViewEurTipSceneBinding(Object _bindingComponent, View _root, int _localFieldCount) {
        super(_bindingComponent, _root, _localFieldCount);
    }

    public static ViewEurTipSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEurTipSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewEurTipSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_eur_tip_scene, root, attachToRoot, component);
    }

    public static ViewEurTipSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEurTipSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewEurTipSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_eur_tip_scene, null, false, component);
    }

    public static ViewEurTipSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEurTipSceneBinding bind(View view, Object component) {
        return (ViewEurTipSceneBinding) bind(component, view, R.layout.view_eur_tip_scene);
    }
}