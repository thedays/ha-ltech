package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemNumPanelSwitchPositionSetBinding extends ViewDataBinding {
    public final AppCompatImageView iv;
    public final TextView tv1;

    protected ItemNumPanelSwitchPositionSetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView iv, TextView tv1) {
        super(_bindingComponent, _root, _localFieldCount);
        this.iv = iv;
        this.tv1 = tv1;
    }

    public static ItemNumPanelSwitchPositionSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemNumPanelSwitchPositionSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemNumPanelSwitchPositionSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_num_panel_switch_position_set, root, attachToRoot, component);
    }

    public static ItemNumPanelSwitchPositionSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemNumPanelSwitchPositionSetBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemNumPanelSwitchPositionSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_num_panel_switch_position_set, null, false, component);
    }

    public static ItemNumPanelSwitchPositionSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemNumPanelSwitchPositionSetBinding bind(View view, Object component) {
        return (ItemNumPanelSwitchPositionSetBinding) bind(component, view, R.layout.item_num_panel_switch_position_set);
    }
}