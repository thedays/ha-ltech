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
public abstract class ItemGroupSwitchPatternBinding extends ViewDataBinding {
    public final AppCompatImageView ivPattern;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvPattern;

    protected ItemGroupSwitchPatternBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivPattern, ConstraintLayout layoutItemBg, AppCompatTextView tvPattern) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivPattern = ivPattern;
        this.layoutItemBg = layoutItemBg;
        this.tvPattern = tvPattern;
    }

    public static ItemGroupSwitchPatternBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupSwitchPatternBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemGroupSwitchPatternBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_switch_pattern, root, attachToRoot, component);
    }

    public static ItemGroupSwitchPatternBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupSwitchPatternBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemGroupSwitchPatternBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_switch_pattern, null, false, component);
    }

    public static ItemGroupSwitchPatternBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupSwitchPatternBinding bind(View view, Object component) {
        return (ItemGroupSwitchPatternBinding) bind(component, view, R.layout.item_group_switch_pattern);
    }
}