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
public abstract class FtIntelligenceBinding extends ViewDataBinding {
    public final AppCompatImageView ivAdd;
    public final AppCompatImageView ivSearch;
    public final AppCompatImageView ivSort;

    @Bindable
    protected FtIntelligenceVM mViewmodel;
    public final TabLayout tabs;
    public final View vTitleBg;
    public final ViewPager2 viewpager;

    public abstract void setViewmodel(FtIntelligenceVM viewmodel);

    protected FtIntelligenceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivAdd, AppCompatImageView ivSearch, AppCompatImageView ivSort, TabLayout tabs, View vTitleBg, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivAdd = ivAdd;
        this.ivSearch = ivSearch;
        this.ivSort = ivSort;
        this.tabs = tabs;
        this.vTitleBg = vTitleBg;
        this.viewpager = viewpager;
    }

    public FtIntelligenceVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtIntelligenceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtIntelligenceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtIntelligenceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_intelligence, root, attachToRoot, component);
    }

    public static FtIntelligenceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtIntelligenceBinding inflate(LayoutInflater inflater, Object component) {
        return (FtIntelligenceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_intelligence, null, false, component);
    }

    public static FtIntelligenceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtIntelligenceBinding bind(View view, Object component) {
        return (FtIntelligenceBinding) bind(component, view, R.layout.ft_intelligence);
    }
}