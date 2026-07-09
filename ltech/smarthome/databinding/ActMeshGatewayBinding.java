package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayVM;

/* loaded from: classes3.dex */
public abstract class ActMeshGatewayBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView38;
    public final AppCompatImageView ivAdd;
    public final LinearLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActMeshGatewayVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvNum;
    public final AppCompatImageView vTop;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActMeshGatewayVM viewmodel);

    protected ActMeshGatewayBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView38, AppCompatImageView ivAdd, LinearLayout layoutLoad, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvNum, AppCompatImageView vTop) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView38 = appCompatTextView38;
        this.ivAdd = ivAdd;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.title = title;
        this.tvNum = tvNum;
        this.vTop = vTop;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActMeshGatewayVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActMeshGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMeshGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_gateway, root, attachToRoot, component);
    }

    public static ActMeshGatewayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshGatewayBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMeshGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_gateway, null, false, component);
    }

    public static ActMeshGatewayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshGatewayBinding bind(View view, Object component) {
        return (ActMeshGatewayBinding) bind(component, view, R.layout.act_mesh_gateway);
    }
}