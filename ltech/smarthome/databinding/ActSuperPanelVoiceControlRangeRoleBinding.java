package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeVM;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelVoiceControlRangeRoleBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivEdit;
    public final ConstraintLayout layoutTab;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSuperPanelVoiceControlRangeVM mViewmodel;
    public final TabLayout tabTitle;
    public final View vTitleBg;
    public final ViewPager2 viewpager;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSuperPanelVoiceControlRangeVM viewmodel);

    protected ActSuperPanelVoiceControlRangeRoleBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, AppCompatImageView ivEdit, ConstraintLayout layoutTab, TabLayout tabTitle, View vTitleBg, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.ivEdit = ivEdit;
        this.layoutTab = layoutTab;
        this.tabTitle = tabTitle;
        this.vTitleBg = vTitleBg;
        this.viewpager = viewpager;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSuperPanelVoiceControlRangeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSuperPanelVoiceControlRangeRoleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelVoiceControlRangeRoleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelVoiceControlRangeRoleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_voice_control_range_role, root, attachToRoot, component);
    }

    public static ActSuperPanelVoiceControlRangeRoleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelVoiceControlRangeRoleBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelVoiceControlRangeRoleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_voice_control_range_role, null, false, component);
    }

    public static ActSuperPanelVoiceControlRangeRoleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelVoiceControlRangeRoleBinding bind(View view, Object component) {
        return (ActSuperPanelVoiceControlRangeRoleBinding) bind(component, view, R.layout.act_super_panel_voice_control_range_role);
    }
}