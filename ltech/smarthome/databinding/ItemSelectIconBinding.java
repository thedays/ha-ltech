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
public abstract class ItemSelectIconBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvName;

    protected ItemSelectIconBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
    }

    public static ItemSelectIconBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectIconBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectIconBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_icon, root, attachToRoot, component);
    }

    public static ItemSelectIconBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectIconBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectIconBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_icon, null, false, component);
    }

    public static ItemSelectIconBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectIconBinding bind(View view, Object component) {
        return (ItemSelectIconBinding) bind(component, view, R.layout.item_select_icon);
    }
}