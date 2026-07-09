package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemFreshAirInfoBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final LinearLayout layoutBg;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvValue;

    protected ItemFreshAirInfoBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, LinearLayout layoutBg, AppCompatTextView tvName, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.layoutBg = layoutBg;
        this.tvName = tvName;
        this.tvValue = tvValue;
    }

    public static ItemFreshAirInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFreshAirInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemFreshAirInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_fresh_air_info, root, attachToRoot, component);
    }

    public static ItemFreshAirInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFreshAirInfoBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemFreshAirInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_fresh_air_info, null, false, component);
    }

    public static ItemFreshAirInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFreshAirInfoBinding bind(View view, Object component) {
        return (ItemFreshAirInfoBinding) bind(component, view, R.layout.item_fresh_air_info);
    }
}