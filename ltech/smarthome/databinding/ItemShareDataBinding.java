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
public abstract class ItemShareDataBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvSub;
    public final View viewDivider;

    protected ItemShareDataBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, AppCompatTextView tvMain, AppCompatTextView tvSub, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
        this.viewDivider = viewDivider;
    }

    public static ItemShareDataBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemShareDataBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemShareDataBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_share_data, root, attachToRoot, component);
    }

    public static ItemShareDataBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemShareDataBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemShareDataBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_share_data, null, false, component);
    }

    public static ItemShareDataBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemShareDataBinding bind(View view, Object component) {
        return (ItemShareDataBinding) bind(component, view, R.layout.item_share_data);
    }
}