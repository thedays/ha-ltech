package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM;

/* loaded from: classes3.dex */
public abstract class ActCentralAirProGatewayBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView11;
    public final Guideline guideline2;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActCentralAirProGatewayVM mViewmodel;
    public final TabLayout tabs;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSearchSum;
    public final View vGuide;
    public final ViewPager2 viewpager;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActCentralAirProGatewayVM viewmodel);

    protected ActCentralAirProGatewayBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView11, Guideline guideline2, TabLayout tabs, LayoutTitleDefaultBinding title, AppCompatTextView tvSearchSum, View vGuide, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView11 = appCompatImageView11;
        this.guideline2 = guideline2;
        this.tabs = tabs;
        this.title = title;
        this.tvSearchSum = tvSearchSum;
        this.vGuide = vGuide;
        this.viewpager = viewpager;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActCentralAirProGatewayVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActCentralAirProGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCentralAirProGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCentralAirProGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_central_air_pro_gateway, root, attachToRoot, component);
    }

    public static ActCentralAirProGatewayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCentralAirProGatewayBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCentralAirProGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_central_air_pro_gateway, null, false, component);
    }

    public static ActCentralAirProGatewayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCentralAirProGatewayBinding bind(View view, Object component) {
        return (ActCentralAirProGatewayBinding) bind(component, view, R.layout.act_central_air_pro_gateway);
    }
}