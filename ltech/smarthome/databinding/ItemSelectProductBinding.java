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
public abstract class ItemSelectProductBinding extends ViewDataBinding {
    public final AppCompatImageView ivProductIcon;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvProductName;

    protected ItemSelectProductBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivProductIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvProductName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivProductIcon = ivProductIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvProductName = tvProductName;
    }

    public static ItemSelectProductBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectProductBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectProductBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_product, root, attachToRoot, component);
    }

    public static ItemSelectProductBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectProductBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectProductBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_product, null, false, component);
    }

    public static ItemSelectProductBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectProductBinding bind(View view, Object component) {
        return (ItemSelectProductBinding) bind(component, view, R.layout.item_select_product);
    }
}