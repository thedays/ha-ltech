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
public abstract class ItemE6dDefaultBinding extends ViewDataBinding {
    public final AppCompatImageView ivMore;
    public final AppCompatTextView tvName;

    protected ItemE6dDefaultBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMore, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMore = ivMore;
        this.tvName = tvName;
    }

    public static ItemE6dDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6dDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemE6dDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_e6d_default, root, attachToRoot, component);
    }

    public static ItemE6dDefaultBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6dDefaultBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemE6dDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_e6d_default, null, false, component);
    }

    public static ItemE6dDefaultBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6dDefaultBinding bind(View view, Object component) {
        return (ItemE6dDefaultBinding) bind(component, view, R.layout.item_e6d_default);
    }
}