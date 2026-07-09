package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemEmptyFoot15Binding extends ViewDataBinding {
    public final ConstraintLayout layoutItemBg;

    protected ItemEmptyFoot15Binding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutItemBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutItemBg = layoutItemBg;
    }

    public static ItemEmptyFoot15Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEmptyFoot15Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemEmptyFoot15Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_empty_foot_15, root, attachToRoot, component);
    }

    public static ItemEmptyFoot15Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEmptyFoot15Binding inflate(LayoutInflater inflater, Object component) {
        return (ItemEmptyFoot15Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_empty_foot_15, null, false, component);
    }

    public static ItemEmptyFoot15Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEmptyFoot15Binding bind(View view, Object component) {
        return (ItemEmptyFoot15Binding) bind(component, view, R.layout.item_empty_foot_15);
    }
}