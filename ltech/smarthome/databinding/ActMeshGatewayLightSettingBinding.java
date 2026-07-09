package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSettingVM;
import com.ltech.smarthome.view.AnnularColorPickView;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ActMeshGatewayLightSettingBinding extends ViewDataBinding {
    public final AnnularColorPickView annularColorPickView;
    public final CircleImageView civColor;
    public final Group groupBrt;
    public final Group groupOpen;
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivOpen;
    public final ConstraintLayout layoutContent;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActMeshGatewayLightSettingVM mViewmodel;
    public final RecyclerView rvColor;
    public final LightBrtBar sbBrt;
    public final SwitchButton sbOn;
    public final Toolbar title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvTitle;
    public final View vOpen;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActMeshGatewayLightSettingVM viewmodel);

    protected ActMeshGatewayLightSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AnnularColorPickView annularColorPickView, CircleImageView civColor, Group groupBrt, Group groupOpen, AppCompatImageView ivBack, AppCompatImageView ivOpen, ConstraintLayout layoutContent, RecyclerView rvColor, LightBrtBar sbBrt, SwitchButton sbOn, Toolbar title, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvTitle, View vOpen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.annularColorPickView = annularColorPickView;
        this.civColor = civColor;
        this.groupBrt = groupBrt;
        this.groupOpen = groupOpen;
        this.ivBack = ivBack;
        this.ivOpen = ivOpen;
        this.layoutContent = layoutContent;
        this.rvColor = rvColor;
        this.sbBrt = sbBrt;
        this.sbOn = sbOn;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvTitle = tvTitle;
        this.vOpen = vOpen;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActMeshGatewayLightSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActMeshGatewayLightSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshGatewayLightSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMeshGatewayLightSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_gateway_light_setting, root, attachToRoot, component);
    }

    public static ActMeshGatewayLightSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshGatewayLightSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMeshGatewayLightSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_gateway_light_setting, null, false, component);
    }

    public static ActMeshGatewayLightSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshGatewayLightSettingBinding bind(View view, Object component) {
        return (ActMeshGatewayLightSettingBinding) bind(component, view, R.layout.act_mesh_gateway_light_setting);
    }
}