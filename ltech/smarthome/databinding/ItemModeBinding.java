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
import com.ltech.smarthome.utils.selectedCountryLib.demo.RoundImageView;

/* loaded from: classes3.dex */
public abstract class ItemModeBinding extends ViewDataBinding {
    public final RoundImageView ivIcon;
    public final AppCompatTextView ivMore;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvTimes;
    public final View vMoreClick;
    public final View view25;

    protected ItemModeBinding(Object _bindingComponent, View _root, int _localFieldCount, RoundImageView ivIcon, AppCompatTextView ivMore, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg, AppCompatTextView tvName, AppCompatTextView tvTimes, View vMoreClick, View view25) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivMore = ivMore;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
        this.tvTimes = tvTimes;
        this.vMoreClick = vMoreClick;
        this.view25 = view25;
    }

    public static ItemModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mode, root, attachToRoot, component);
    }

    public static ItemModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mode, null, false, component);
    }

    public static ItemModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemModeBinding bind(View view, Object component) {
        return (ItemModeBinding) bind(component, view, R.layout.item_mode);
    }
}