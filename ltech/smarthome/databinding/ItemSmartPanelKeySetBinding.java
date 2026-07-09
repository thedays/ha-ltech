package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSmartPanelKeySetBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvSubText;

    protected ItemSmartPanelKeySetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatTextView tvMain, AppCompatTextView tvSubText) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.tvMain = tvMain;
        this.tvSubText = tvSubText;
    }

    public static ItemSmartPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSmartPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_smart_panel_key_set, root, attachToRoot, component);
    }

    public static ItemSmartPanelKeySetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartPanelKeySetBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSmartPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_smart_panel_key_set, null, false, component);
    }

    public static ItemSmartPanelKeySetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartPanelKeySetBinding bind(View view, Object component) {
        return (ItemSmartPanelKeySetBinding) bind(component, view, R.layout.item_smart_panel_key_set);
    }
}