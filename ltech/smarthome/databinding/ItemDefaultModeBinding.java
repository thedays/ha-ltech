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
public abstract class ItemDefaultModeBinding extends ViewDataBinding {
    public final AppCompatImageView ivCover;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvModeContent;
    public final AppCompatTextView tvModeName;

    protected ItemDefaultModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivCover, ConstraintLayout layoutItemBg, AppCompatTextView tvModeContent, AppCompatTextView tvModeName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivCover = ivCover;
        this.layoutItemBg = layoutItemBg;
        this.tvModeContent = tvModeContent;
        this.tvModeName = tvModeName;
    }

    public static ItemDefaultModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDefaultModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDefaultModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_default_mode, root, attachToRoot, component);
    }

    public static ItemDefaultModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDefaultModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDefaultModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_default_mode, null, false, component);
    }

    public static ItemDefaultModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDefaultModeBinding bind(View view, Object component) {
        return (ItemDefaultModeBinding) bind(component, view, R.layout.item_default_mode);
    }
}