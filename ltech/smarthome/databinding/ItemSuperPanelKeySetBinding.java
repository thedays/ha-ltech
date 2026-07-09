package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSuperPanelKeySetBinding extends ViewDataBinding {
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvKeyFun;
    public final AppCompatTextView tvKeyName;
    public final View vBottom;
    public final View vLeft;
    public final View vRight;

    protected ItemSuperPanelKeySetBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutItemBg, AppCompatTextView tvKeyFun, AppCompatTextView tvKeyName, View vBottom, View vLeft, View vRight) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutItemBg = layoutItemBg;
        this.tvKeyFun = tvKeyFun;
        this.tvKeyName = tvKeyName;
        this.vBottom = vBottom;
        this.vLeft = vLeft;
        this.vRight = vRight;
    }

    public static ItemSuperPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSuperPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSuperPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_super_panel_key_set, root, attachToRoot, component);
    }

    public static ItemSuperPanelKeySetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSuperPanelKeySetBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSuperPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_super_panel_key_set, null, false, component);
    }

    public static ItemSuperPanelKeySetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSuperPanelKeySetBinding bind(View view, Object component) {
        return (ItemSuperPanelKeySetBinding) bind(component, view, R.layout.item_super_panel_key_set);
    }
}