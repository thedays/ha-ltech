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
public abstract class ItemAutoConditionBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivHint;
    public final AppCompatImageView ivIcon;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvConditionTip;
    public final AppCompatTextView tvContent;
    public final AppCompatTextView tvName;

    protected ItemAutoConditionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatImageView ivHint, AppCompatImageView ivIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvConditionTip, AppCompatTextView tvContent, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.ivHint = ivHint;
        this.ivIcon = ivIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvConditionTip = tvConditionTip;
        this.tvContent = tvContent;
        this.tvName = tvName;
    }

    public static ItemAutoConditionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAutoConditionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemAutoConditionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_auto_condition, root, attachToRoot, component);
    }

    public static ItemAutoConditionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAutoConditionBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemAutoConditionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_auto_condition, null, false, component);
    }

    public static ItemAutoConditionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAutoConditionBinding bind(View view, Object component) {
        return (ItemAutoConditionBinding) bind(component, view, R.layout.item_auto_condition);
    }
}