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
public abstract class ItemLightActionBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatTextView tvContent;

    protected ItemLightActionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatTextView tvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.tvContent = tvContent;
    }

    public static ItemLightActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemLightActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_light_action, root, attachToRoot, component);
    }

    public static ItemLightActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightActionBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemLightActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_light_action, null, false, component);
    }

    public static ItemLightActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightActionBinding bind(View view, Object component) {
        return (ItemLightActionBinding) bind(component, view, R.layout.item_light_action);
    }
}