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
public abstract class ItemTrigKeyBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final LinearLayout layoutBg;
    public final AppCompatTextView tvName;

    protected ItemTrigKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, LinearLayout layoutBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.layoutBg = layoutBg;
        this.tvName = tvName;
    }

    public static ItemTrigKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTrigKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTrigKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_trig_key, root, attachToRoot, component);
    }

    public static ItemTrigKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTrigKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTrigKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_trig_key, null, false, component);
    }

    public static ItemTrigKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTrigKeyBinding bind(View view, Object component) {
        return (ItemTrigKeyBinding) bind(component, view, R.layout.item_trig_key);
    }
}