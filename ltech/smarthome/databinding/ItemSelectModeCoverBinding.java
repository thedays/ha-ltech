package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.selectedCountryLib.demo.RoundImageView;

/* loaded from: classes3.dex */
public abstract class ItemSelectModeCoverBinding extends ViewDataBinding {
    public final RoundImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;

    protected ItemSelectModeCoverBinding(Object _bindingComponent, View _root, int _localFieldCount, RoundImageView ivIcon, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
    }

    public static ItemSelectModeCoverBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectModeCoverBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectModeCoverBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_mode_cover, root, attachToRoot, component);
    }

    public static ItemSelectModeCoverBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectModeCoverBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectModeCoverBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_mode_cover, null, false, component);
    }

    public static ItemSelectModeCoverBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectModeCoverBinding bind(View view, Object component) {
        return (ItemSelectModeCoverBinding) bind(component, view, R.layout.item_select_mode_cover);
    }
}