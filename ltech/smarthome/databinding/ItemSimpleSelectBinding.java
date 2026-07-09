package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSimpleSelectBinding extends ViewDataBinding {
    public final AppCompatTextView tvName;

    protected ItemSimpleSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvName = tvName;
    }

    public static ItemSimpleSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSimpleSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSimpleSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_simple_select, root, attachToRoot, component);
    }

    public static ItemSimpleSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSimpleSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSimpleSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_simple_select, null, false, component);
    }

    public static ItemSimpleSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSimpleSelectBinding bind(View view, Object component) {
        return (ItemSimpleSelectBinding) bind(component, view, R.layout.item_simple_select);
    }
}