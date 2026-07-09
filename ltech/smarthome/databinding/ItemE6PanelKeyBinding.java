package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemE6PanelKeyBinding extends ViewDataBinding {
    public final ConstraintLayout layoutItemBg;

    protected ItemE6PanelKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutItemBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutItemBg = layoutItemBg;
    }

    public static ItemE6PanelKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6PanelKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemE6PanelKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_e6_panel_key, root, attachToRoot, component);
    }

    public static ItemE6PanelKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6PanelKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemE6PanelKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_e6_panel_key, null, false, component);
    }

    public static ItemE6PanelKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6PanelKeyBinding bind(View view, Object component) {
        return (ItemE6PanelKeyBinding) bind(component, view, R.layout.item_e6_panel_key);
    }
}