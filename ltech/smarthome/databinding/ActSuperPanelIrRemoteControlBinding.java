package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayVM;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelIrRemoteControlBinding extends ViewDataBinding {
    public final LinearLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActMeshGatewayVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActMeshGatewayVM viewmodel);

    protected ActSuperPanelIrRemoteControlBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutLoad, RecyclerView rvContent, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActMeshGatewayVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSuperPanelIrRemoteControlBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelIrRemoteControlBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelIrRemoteControlBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_ir_remote_control, root, attachToRoot, component);
    }

    public static ActSuperPanelIrRemoteControlBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelIrRemoteControlBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelIrRemoteControlBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_ir_remote_control, null, false, component);
    }

    public static ActSuperPanelIrRemoteControlBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelIrRemoteControlBinding bind(View view, Object component) {
        return (ActSuperPanelIrRemoteControlBinding) bind(component, view, R.layout.act_super_panel_ir_remote_control);
    }
}