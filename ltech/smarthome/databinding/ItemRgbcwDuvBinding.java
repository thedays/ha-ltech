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
public abstract class ItemRgbcwDuvBinding extends ViewDataBinding {
    public final AppCompatTextView ivApply;
    public final AppCompatImageView ivSelect;
    public final AppCompatImageView ivSort;
    public final AppCompatTextView tvName;
    public final View viewDivider;

    protected ItemRgbcwDuvBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView ivApply, AppCompatImageView ivSelect, AppCompatImageView ivSort, AppCompatTextView tvName, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivApply = ivApply;
        this.ivSelect = ivSelect;
        this.ivSort = ivSort;
        this.tvName = tvName;
        this.viewDivider = viewDivider;
    }

    public static ItemRgbcwDuvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRgbcwDuvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemRgbcwDuvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_rgbcw_duv, root, attachToRoot, component);
    }

    public static ItemRgbcwDuvBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRgbcwDuvBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemRgbcwDuvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_rgbcw_duv, null, false, component);
    }

    public static ItemRgbcwDuvBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRgbcwDuvBinding bind(View view, Object component) {
        return (ItemRgbcwDuvBinding) bind(component, view, R.layout.item_rgbcw_duv);
    }
}