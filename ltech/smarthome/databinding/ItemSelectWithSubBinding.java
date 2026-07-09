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
public abstract class ItemSelectWithSubBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSub;

    protected ItemSelectWithSubBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, AppCompatTextView tvName, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.tvName = tvName;
        this.tvSub = tvSub;
    }

    public static ItemSelectWithSubBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectWithSubBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectWithSubBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_with_sub, root, attachToRoot, component);
    }

    public static ItemSelectWithSubBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectWithSubBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectWithSubBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_with_sub, null, false, component);
    }

    public static ItemSelectWithSubBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectWithSubBinding bind(View view, Object component) {
        return (ItemSelectWithSubBinding) bind(component, view, R.layout.item_select_with_sub);
    }
}