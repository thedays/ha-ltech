package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.selectedCountryLib.demo.RoundImageView;

/* loaded from: classes3.dex */
public abstract class ItemGeneralModeBinding extends ViewDataBinding {
    public final RoundImageView ivIcon;
    public final AppCompatTextView ivMore;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvTimes;
    public final View vMoreClick;
    public final View view25;

    protected ItemGeneralModeBinding(Object _bindingComponent, View _root, int _localFieldCount, RoundImageView ivIcon, AppCompatTextView ivMore, ConstraintLayout layoutItemBg, AppCompatTextView tvName, AppCompatTextView tvTimes, View vMoreClick, View view25) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivMore = ivMore;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
        this.tvTimes = tvTimes;
        this.vMoreClick = vMoreClick;
        this.view25 = view25;
    }

    public static ItemGeneralModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGeneralModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemGeneralModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_general_mode, root, attachToRoot, component);
    }

    public static ItemGeneralModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGeneralModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemGeneralModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_general_mode, null, false, component);
    }

    public static ItemGeneralModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGeneralModeBinding bind(View view, Object component) {
        return (ItemGeneralModeBinding) bind(component, view, R.layout.item_general_mode);
    }
}