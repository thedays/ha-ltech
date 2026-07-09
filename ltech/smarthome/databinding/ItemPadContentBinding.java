package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemPadContentBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatTextView tvContent;
    public final AppCompatTextView tvTip;

    protected ItemPadContentBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatTextView tvContent, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.tvContent = tvContent;
        this.tvTip = tvTip;
    }

    public static ItemPadContentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPadContentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemPadContentBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_pad_content, root, attachToRoot, component);
    }

    public static ItemPadContentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPadContentBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemPadContentBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_pad_content, null, false, component);
    }

    public static ItemPadContentBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPadContentBinding bind(View view, Object component) {
        return (ItemPadContentBinding) bind(component, view, R.layout.item_pad_content);
    }
}