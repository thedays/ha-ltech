package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemCg485ColorBinding extends ViewDataBinding {
    public final CardView card;
    public final AppCompatImageView ivSelect;

    protected ItemCg485ColorBinding(Object _bindingComponent, View _root, int _localFieldCount, CardView card, AppCompatImageView ivSelect) {
        super(_bindingComponent, _root, _localFieldCount);
        this.card = card;
        this.ivSelect = ivSelect;
    }

    public static ItemCg485ColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCg485ColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemCg485ColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_cg485_color, root, attachToRoot, component);
    }

    public static ItemCg485ColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCg485ColorBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemCg485ColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_cg485_color, null, false, component);
    }

    public static ItemCg485ColorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCg485ColorBinding bind(View view, Object component) {
        return (ItemCg485ColorBinding) bind(component, view, R.layout.item_cg485_color);
    }
}