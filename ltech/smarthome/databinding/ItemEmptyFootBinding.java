package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemEmptyFootBinding extends ViewDataBinding {
    public final ConstraintLayout layoutItemBg;

    protected ItemEmptyFootBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutItemBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutItemBg = layoutItemBg;
    }

    public static ItemEmptyFootBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEmptyFootBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemEmptyFootBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_empty_foot, root, attachToRoot, component);
    }

    public static ItemEmptyFootBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEmptyFootBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemEmptyFootBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_empty_foot, null, false, component);
    }

    public static ItemEmptyFootBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEmptyFootBinding bind(View view, Object component) {
        return (ItemEmptyFootBinding) bind(component, view, R.layout.item_empty_foot);
    }
}