package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemPanelSwitchPositionSetBinding extends ViewDataBinding {
    public final AppCompatImageView iv;
    public final TextView tv1;
    public final AppCompatTextView tv2;

    protected ItemPanelSwitchPositionSetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView iv, TextView tv1, AppCompatTextView tv2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.iv = iv;
        this.tv1 = tv1;
        this.tv2 = tv2;
    }

    public static ItemPanelSwitchPositionSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPanelSwitchPositionSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemPanelSwitchPositionSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_panel_switch_position_set, root, attachToRoot, component);
    }

    public static ItemPanelSwitchPositionSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPanelSwitchPositionSetBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemPanelSwitchPositionSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_panel_switch_position_set, null, false, component);
    }

    public static ItemPanelSwitchPositionSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPanelSwitchPositionSetBinding bind(View view, Object component) {
        return (ItemPanelSwitchPositionSetBinding) bind(component, view, R.layout.item_panel_switch_position_set);
    }
}