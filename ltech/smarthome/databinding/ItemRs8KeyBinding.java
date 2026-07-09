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
public abstract class ItemRs8KeyBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final LinearLayout layoutBg;
    public final AppCompatTextView tvName;

    protected ItemRs8KeyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, LinearLayout layoutBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.layoutBg = layoutBg;
        this.tvName = tvName;
    }

    public static ItemRs8KeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRs8KeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemRs8KeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_rs8_key, root, attachToRoot, component);
    }

    public static ItemRs8KeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRs8KeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemRs8KeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_rs8_key, null, false, component);
    }

    public static ItemRs8KeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRs8KeyBinding bind(View view, Object component) {
        return (ItemRs8KeyBinding) bind(component, view, R.layout.item_rs8_key);
    }
}