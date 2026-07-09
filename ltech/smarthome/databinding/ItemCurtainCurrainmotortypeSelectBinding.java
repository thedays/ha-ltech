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
public abstract class ItemCurtainCurrainmotortypeSelectBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final AppCompatTextView tvMain;

    protected ItemCurtainCurrainmotortypeSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivSelect, AppCompatTextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.tvMain = tvMain;
    }

    public static ItemCurtainCurrainmotortypeSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCurtainCurrainmotortypeSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemCurtainCurrainmotortypeSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_curtain_currainmotortype_select, root, attachToRoot, component);
    }

    public static ItemCurtainCurrainmotortypeSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCurtainCurrainmotortypeSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemCurtainCurrainmotortypeSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_curtain_currainmotortype_select, null, false, component);
    }

    public static ItemCurtainCurrainmotortypeSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCurtainCurrainmotortypeSelectBinding bind(View view, Object component) {
        return (ItemCurtainCurrainmotortypeSelectBinding) bind(component, view, R.layout.item_curtain_currainmotortype_select);
    }
}