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
public abstract class ItemSelectListWhiteBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final AppCompatTextView tvName;

    protected ItemSelectListWhiteBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.tvName = tvName;
    }

    public static ItemSelectListWhiteBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectListWhiteBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectListWhiteBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_list_white, root, attachToRoot, component);
    }

    public static ItemSelectListWhiteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectListWhiteBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectListWhiteBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_list_white, null, false, component);
    }

    public static ItemSelectListWhiteBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectListWhiteBinding bind(View view, Object component) {
        return (ItemSelectListWhiteBinding) bind(component, view, R.layout.item_select_list_white);
    }
}