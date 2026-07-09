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
import com.ltech.smarthome.ui.item.SettingItem;

/* loaded from: classes3.dex */
public abstract class ItemSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivTipDot;
    public final AppCompatTextView ivTipText;

    @Bindable
    protected SettingItem mItem;
    public final AppCompatTextView tvMain;

    public abstract void setItem(SettingItem item);

    protected ItemSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatImageView ivIcon, AppCompatImageView ivTipDot, AppCompatTextView ivTipText, AppCompatTextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.ivIcon = ivIcon;
        this.ivTipDot = ivTipDot;
        this.ivTipText = ivTipText;
        this.tvMain = tvMain;
    }

    public SettingItem getItem() {
        return this.mItem;
    }

    public static ItemSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_setting, root, attachToRoot, component);
    }

    public static ItemSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_setting, null, false, component);
    }

    public static ItemSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSettingBinding bind(View view, Object component) {
        return (ItemSettingBinding) bind(component, view, R.layout.item_setting);
    }
}