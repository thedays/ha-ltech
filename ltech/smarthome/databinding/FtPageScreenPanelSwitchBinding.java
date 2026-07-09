package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtPageScreenPanelSwitchBinding extends ViewDataBinding {
    public final LinearLayout llBottom;
    public final RecyclerView rv;
    public final TextView tvTipMessage;
    public final TextView tvTipTitle;

    protected FtPageScreenPanelSwitchBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout llBottom, RecyclerView rv, TextView tvTipMessage, TextView tvTipTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.llBottom = llBottom;
        this.rv = rv;
        this.tvTipMessage = tvTipMessage;
        this.tvTipTitle = tvTipTitle;
    }

    public static FtPageScreenPanelSwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtPageScreenPanelSwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtPageScreenPanelSwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_page_screen_panel_switch, root, attachToRoot, component);
    }

    public static FtPageScreenPanelSwitchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtPageScreenPanelSwitchBinding inflate(LayoutInflater inflater, Object component) {
        return (FtPageScreenPanelSwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_page_screen_panel_switch, null, false, component);
    }

    public static FtPageScreenPanelSwitchBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtPageScreenPanelSwitchBinding bind(View view, Object component) {
        return (FtPageScreenPanelSwitchBinding) bind(component, view, R.layout.ft_page_screen_panel_switch);
    }
}