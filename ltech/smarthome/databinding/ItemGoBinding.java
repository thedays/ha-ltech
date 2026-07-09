package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.GoItem;

/* loaded from: classes3.dex */
public abstract class ItemGoBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivTipDot;
    public final AppCompatTextView ivTipText;

    @Bindable
    protected GoItem mItem;
    public final AppCompatTextView tvMain;

    public abstract void setItem(GoItem item);

    protected ItemGoBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatImageView ivIcon, AppCompatImageView ivTipDot, AppCompatTextView ivTipText, AppCompatTextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.ivIcon = ivIcon;
        this.ivTipDot = ivTipDot;
        this.ivTipText = ivTipText;
        this.tvMain = tvMain;
    }

    public GoItem getItem() {
        return this.mItem;
    }

    public static ItemGoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemGoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_go, root, attachToRoot, component);
    }

    public static ItemGoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGoBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemGoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_go, null, false, component);
    }

    public static ItemGoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGoBinding bind(View view, Object component) {
        return (ItemGoBinding) bind(component, view, R.layout.item_go);
    }
}