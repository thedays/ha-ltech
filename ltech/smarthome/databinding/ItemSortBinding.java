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
public abstract class ItemSortBinding extends ViewDataBinding {
    public final AppCompatImageView ivSort;
    public final AppCompatTextView tvName;

    protected ItemSortBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSort, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSort = ivSort;
        this.tvName = tvName;
    }

    public static ItemSortBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSortBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSortBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sort, root, attachToRoot, component);
    }

    public static ItemSortBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSortBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSortBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sort, null, false, component);
    }

    public static ItemSortBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSortBinding bind(View view, Object component) {
        return (ItemSortBinding) bind(component, view, R.layout.item_sort);
    }
}