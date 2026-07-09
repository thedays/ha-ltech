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
public abstract class ItemSuperPanelFunBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvFunName;
    public final AppCompatTextView tvFunTip;

    protected ItemSuperPanelFunBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvFunName, AppCompatTextView tvFunTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvFunName = tvFunName;
        this.tvFunTip = tvFunTip;
    }

    public static ItemSuperPanelFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSuperPanelFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSuperPanelFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_super_panel_fun, root, attachToRoot, component);
    }

    public static ItemSuperPanelFunBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSuperPanelFunBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSuperPanelFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_super_panel_fun, null, false, component);
    }

    public static ItemSuperPanelFunBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSuperPanelFunBinding bind(View view, Object component) {
        return (ItemSuperPanelFunBinding) bind(component, view, R.layout.item_super_panel_fun);
    }
}