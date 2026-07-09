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
public abstract class ItemLightOrderBinding extends ViewDataBinding {
    public final View divider;
    public final AppCompatImageView ivSort;
    public final AppCompatTextView tvOutput;
    public final AppCompatTextView tvPort;

    protected ItemLightOrderBinding(Object _bindingComponent, View _root, int _localFieldCount, View divider, AppCompatImageView ivSort, AppCompatTextView tvOutput, AppCompatTextView tvPort) {
        super(_bindingComponent, _root, _localFieldCount);
        this.divider = divider;
        this.ivSort = ivSort;
        this.tvOutput = tvOutput;
        this.tvPort = tvPort;
    }

    public static ItemLightOrderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightOrderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemLightOrderBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_light_order, root, attachToRoot, component);
    }

    public static ItemLightOrderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightOrderBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemLightOrderBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_light_order, null, false, component);
    }

    public static ItemLightOrderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightOrderBinding bind(View view, Object component) {
        return (ItemLightOrderBinding) bind(component, view, R.layout.item_light_order);
    }
}