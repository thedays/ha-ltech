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
public abstract class ItemCurtainOpenDirSelectBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final AppCompatTextView tvMain;

    protected ItemCurtainOpenDirSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivSelect, AppCompatTextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.tvMain = tvMain;
    }

    public static ItemCurtainOpenDirSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCurtainOpenDirSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemCurtainOpenDirSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_curtain_open_dir_select, root, attachToRoot, component);
    }

    public static ItemCurtainOpenDirSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCurtainOpenDirSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemCurtainOpenDirSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_curtain_open_dir_select, null, false, component);
    }

    public static ItemCurtainOpenDirSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCurtainOpenDirSelectBinding bind(View view, Object component) {
        return (ItemCurtainOpenDirSelectBinding) bind(component, view, R.layout.item_curtain_open_dir_select);
    }
}