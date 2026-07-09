package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.NumberSetView;

/* loaded from: classes3.dex */
public abstract class ItemE6dAddressBinding extends ViewDataBinding {
    public final NumberSetView addSetView;
    public final AppCompatImageView ivMore;
    public final RelativeLayout layoutAddress;
    public final RelativeLayout layoutName;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvTitle;
    public final View viewDivider;

    protected ItemE6dAddressBinding(Object _bindingComponent, View _root, int _localFieldCount, NumberSetView addSetView, AppCompatImageView ivMore, RelativeLayout layoutAddress, RelativeLayout layoutName, AppCompatTextView tvName, AppCompatTextView tvTitle, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.addSetView = addSetView;
        this.ivMore = ivMore;
        this.layoutAddress = layoutAddress;
        this.layoutName = layoutName;
        this.tvName = tvName;
        this.tvTitle = tvTitle;
        this.viewDivider = viewDivider;
    }

    public static ItemE6dAddressBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6dAddressBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemE6dAddressBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_e6d_address, root, attachToRoot, component);
    }

    public static ItemE6dAddressBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6dAddressBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemE6dAddressBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_e6d_address, null, false, component);
    }

    public static ItemE6dAddressBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemE6dAddressBinding bind(View view, Object component) {
        return (ItemE6dAddressBinding) bind(component, view, R.layout.item_e6d_address);
    }
}