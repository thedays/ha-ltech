package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.control.FtIntelligenceVM;

/* loaded from: classes3.dex */
public abstract class ActIntelligenceBinding extends ViewDataBinding {
    public final AppCompatImageView ivAdd;
    public final AppCompatImageView ivBack;

    @Bindable
    protected FtIntelligenceVM mViewmodel;
    public final TabLayout tabs;
    public final View vTitleBg;
    public final ViewPager2 viewpager;

    public abstract void setViewmodel(FtIntelligenceVM viewmodel);

    protected ActIntelligenceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivAdd, AppCompatImageView ivBack, TabLayout tabs, View vTitleBg, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivAdd = ivAdd;
        this.ivBack = ivBack;
        this.tabs = tabs;
        this.vTitleBg = vTitleBg;
        this.viewpager = viewpager;
    }

    public FtIntelligenceVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntelligenceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntelligenceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntelligenceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intelligence, root, attachToRoot, component);
    }

    public static ActIntelligenceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntelligenceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntelligenceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intelligence, null, false, component);
    }

    public static ActIntelligenceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntelligenceBinding bind(View view, Object component) {
        return (ActIntelligenceBinding) bind(component, view, R.layout.act_intelligence);
    }
}