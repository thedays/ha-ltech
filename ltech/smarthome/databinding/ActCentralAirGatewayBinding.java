package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.central.air.ActCentralAirGatewayVM;

/* loaded from: classes3.dex */
public abstract class ActCentralAirGatewayBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView11;
    public final AppCompatButton btSearch;
    public final Guideline guideline2;
    public final AppCompatImageView ivEmpty;
    public final AppCompatImageView ivSearchAdd;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActCentralAirGatewayVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvEmptyTip;
    public final AppCompatTextView tvSearchSum;
    public final View vGuide;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActCentralAirGatewayVM viewmodel);

    protected ActCentralAirGatewayBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView11, AppCompatButton btSearch, Guideline guideline2, AppCompatImageView ivEmpty, AppCompatImageView ivSearchAdd, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvEmptyTip, AppCompatTextView tvSearchSum, View vGuide) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView11 = appCompatImageView11;
        this.btSearch = btSearch;
        this.guideline2 = guideline2;
        this.ivEmpty = ivEmpty;
        this.ivSearchAdd = ivSearchAdd;
        this.rvContent = rvContent;
        this.title = title;
        this.tvEmptyTip = tvEmptyTip;
        this.tvSearchSum = tvSearchSum;
        this.vGuide = vGuide;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActCentralAirGatewayVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActCentralAirGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCentralAirGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCentralAirGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_central_air_gateway, root, attachToRoot, component);
    }

    public static ActCentralAirGatewayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCentralAirGatewayBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCentralAirGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_central_air_gateway, null, false, component);
    }

    public static ActCentralAirGatewayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCentralAirGatewayBinding bind(View view, Object component) {
        return (ActCentralAirGatewayBinding) bind(component, view, R.layout.act_central_air_gateway);
    }
}