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
public abstract class ItemPanelBindBinding extends ViewDataBinding {
    public final ConstraintLayout itemPanel;
    public final AppCompatImageView ivIcon;
    public final AppCompatTextView tvBind;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvPlaceInfo;

    protected ItemPanelBindBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout itemPanel, AppCompatImageView ivIcon, AppCompatTextView tvBind, AppCompatTextView tvName, AppCompatTextView tvPlaceInfo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.itemPanel = itemPanel;
        this.ivIcon = ivIcon;
        this.tvBind = tvBind;
        this.tvName = tvName;
        this.tvPlaceInfo = tvPlaceInfo;
    }

    public static ItemPanelBindBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPanelBindBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemPanelBindBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_panel_bind, root, attachToRoot, component);
    }

    public static ItemPanelBindBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPanelBindBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemPanelBindBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_panel_bind, null, false, component);
    }

    public static ItemPanelBindBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPanelBindBinding bind(View view, Object component) {
        return (ItemPanelBindBinding) bind(component, view, R.layout.item_panel_bind);
    }
}