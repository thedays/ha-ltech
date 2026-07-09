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
public abstract class ItemDefaultModeSelectBinding extends ViewDataBinding {
    public final AppCompatImageView ivCover;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvModeContent;
    public final AppCompatTextView tvModeName;

    protected ItemDefaultModeSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivCover, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg, AppCompatTextView tvModeContent, AppCompatTextView tvModeName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivCover = ivCover;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
        this.tvModeContent = tvModeContent;
        this.tvModeName = tvModeName;
    }

    public static ItemDefaultModeSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDefaultModeSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDefaultModeSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_default_mode_select, root, attachToRoot, component);
    }

    public static ItemDefaultModeSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDefaultModeSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDefaultModeSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_default_mode_select, null, false, component);
    }

    public static ItemDefaultModeSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDefaultModeSelectBinding bind(View view, Object component) {
        return (ItemDefaultModeSelectBinding) bind(component, view, R.layout.item_default_mode_select);
    }
}