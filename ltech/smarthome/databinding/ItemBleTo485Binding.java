package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemBleTo485Binding extends ViewDataBinding {
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvCmd;
    public final AppCompatTextView tvName;

    protected ItemBleTo485Binding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEdit, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg, AppCompatTextView tvCmd, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEdit = ivEdit;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
        this.tvCmd = tvCmd;
        this.tvName = tvName;
    }

    public static ItemBleTo485Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBleTo485Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemBleTo485Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_ble_to_485, root, attachToRoot, component);
    }

    public static ItemBleTo485Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBleTo485Binding inflate(LayoutInflater inflater, Object component) {
        return (ItemBleTo485Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_ble_to_485, null, false, component);
    }

    public static ItemBleTo485Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBleTo485Binding bind(View view, Object component) {
        return (ItemBleTo485Binding) bind(component, view, R.layout.item_ble_to_485);
    }
}