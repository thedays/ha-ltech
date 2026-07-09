package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.mode.ActModeVM;

/* loaded from: classes3.dex */
public abstract class ActModeBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;

    @Bindable
    protected ActModeVM mViewmodel;
    public final TabLayout tabs;
    public final AppCompatTextView tvEdit;
    public final View vTitleBg;
    public final ViewPager2 viewpager;

    public abstract void setViewmodel(ActModeVM viewmodel);

    protected ActModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, TabLayout tabs, AppCompatTextView tvEdit, View vTitleBg, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.tabs = tabs;
        this.tvEdit = tvEdit;
        this.vTitleBg = vTitleBg;
        this.viewpager = viewpager;
    }

    public ActModeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mode, root, attachToRoot, component);
    }

    public static ActModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mode, null, false, component);
    }

    public static ActModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActModeBinding bind(View view, Object component) {
        return (ActModeBinding) bind(component, view, R.layout.act_mode);
    }
}