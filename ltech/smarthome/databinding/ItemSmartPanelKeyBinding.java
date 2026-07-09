package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSmartPanelKeyBinding extends ViewDataBinding {
    public final LinearLayout layoutContent;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvSubText;

    protected ItemSmartPanelKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutContent, ConstraintLayout layoutItemBg, AppCompatTextView tvDeviceName, AppCompatTextView tvSubText) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutContent = layoutContent;
        this.layoutItemBg = layoutItemBg;
        this.tvDeviceName = tvDeviceName;
        this.tvSubText = tvSubText;
    }

    public static ItemSmartPanelKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartPanelKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSmartPanelKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_smart_panel_key, root, attachToRoot, component);
    }

    public static ItemSmartPanelKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartPanelKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSmartPanelKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_smart_panel_key, null, false, component);
    }

    public static ItemSmartPanelKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartPanelKeyBinding bind(View view, Object component) {
        return (ItemSmartPanelKeyBinding) bind(component, view, R.layout.item_smart_panel_key);
    }
}