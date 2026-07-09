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
public abstract class ItemE6dActionBinding extends ViewDataBinding {
    public final AppCompatImageView ivMore;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvTip;

    protected ItemE6dActionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMore, AppCompatTextView tvName, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMore = ivMore;
        this.tvName = tvName;
        this.tvTip = tvTip;
    }

    public static ItemE6dActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6dActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemE6dActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_e6d_action, root, attachToRoot, component);
    }

    public static ItemE6dActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6dActionBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemE6dActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_e6d_action, null, false, component);
    }

    public static ItemE6dActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6dActionBinding bind(View view, Object component) {
        return (ItemE6dActionBinding) bind(component, view, R.layout.item_e6d_action);
    }
}