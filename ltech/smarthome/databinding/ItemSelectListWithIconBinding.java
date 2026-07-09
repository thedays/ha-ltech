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
public abstract class ItemSelectListWithIconBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final AppCompatTextView tvName;

    protected ItemSelectListWithIconBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivSelect, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.tvName = tvName;
    }

    public static ItemSelectListWithIconBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectListWithIconBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectListWithIconBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_list_with_icon, root, attachToRoot, component);
    }

    public static ItemSelectListWithIconBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectListWithIconBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectListWithIconBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_list_with_icon, null, false, component);
    }

    public static ItemSelectListWithIconBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectListWithIconBinding bind(View view, Object component) {
        return (ItemSelectListWithIconBinding) bind(component, view, R.layout.item_select_list_with_icon);
    }
}