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
public abstract class ItemAddGroupBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivTick;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvName;

    protected ItemAddGroupBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivTick, ConstraintLayout layoutItemBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivTick = ivTick;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
    }

    public static ItemAddGroupBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAddGroupBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemAddGroupBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_add_group, root, attachToRoot, component);
    }

    public static ItemAddGroupBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAddGroupBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemAddGroupBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_add_group, null, false, component);
    }

    public static ItemAddGroupBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAddGroupBinding bind(View view, Object component) {
        return (ItemAddGroupBinding) bind(component, view, R.layout.item_add_group);
    }
}