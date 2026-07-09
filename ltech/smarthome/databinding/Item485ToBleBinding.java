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
public abstract class Item485ToBleBinding extends ViewDataBinding {
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivIcon;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvAction;
    public final AppCompatTextView tvCmd;
    public final AppCompatTextView tvName;

    protected Item485ToBleBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEdit, AppCompatImageView ivIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvAction, AppCompatTextView tvCmd, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEdit = ivEdit;
        this.ivIcon = ivIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvAction = tvAction;
        this.tvCmd = tvCmd;
        this.tvName = tvName;
    }

    public static Item485ToBleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static Item485ToBleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (Item485ToBleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_485_to_ble, root, attachToRoot, component);
    }

    public static Item485ToBleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static Item485ToBleBinding inflate(LayoutInflater inflater, Object component) {
        return (Item485ToBleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_485_to_ble, null, false, component);
    }

    public static Item485ToBleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static Item485ToBleBinding bind(View view, Object component) {
        return (Item485ToBleBinding) bind(component, view, R.layout.item_485_to_ble);
    }
}