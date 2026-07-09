package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class HeadSuperPanelKeySetBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView16;

    protected HeadSuperPanelKeySetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView16) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView16 = appCompatImageView16;
    }

    public static HeadSuperPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HeadSuperPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (HeadSuperPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.head_super_panel_key_set, root, attachToRoot, component);
    }

    public static HeadSuperPanelKeySetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HeadSuperPanelKeySetBinding inflate(LayoutInflater inflater, Object component) {
        return (HeadSuperPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.head_super_panel_key_set, null, false, component);
    }

    public static HeadSuperPanelKeySetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HeadSuperPanelKeySetBinding bind(View view, Object component) {
        return (HeadSuperPanelKeySetBinding) bind(component, view, R.layout.head_super_panel_key_set);
    }
}