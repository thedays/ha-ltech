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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVM;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelBinding extends ViewDataBinding {
    public final Guideline guideline2;
    public final AppCompatImageView ivSuperPanel;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSuperPanelVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSuperPanelTip;
    public final AppCompatTextView tvSuperPanelTip2;
    public final View vGuide;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSuperPanelVM viewmodel);

    protected ActSuperPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline2, AppCompatImageView ivSuperPanel, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvSuperPanelTip, AppCompatTextView tvSuperPanelTip2, View vGuide) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline2 = guideline2;
        this.ivSuperPanel = ivSuperPanel;
        this.rvContent = rvContent;
        this.title = title;
        this.tvSuperPanelTip = tvSuperPanelTip;
        this.tvSuperPanelTip2 = tvSuperPanelTip2;
        this.vGuide = vGuide;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSuperPanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSuperPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel, root, attachToRoot, component);
    }

    public static ActSuperPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel, null, false, component);
    }

    public static ActSuperPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelBinding bind(View view, Object component) {
        return (ActSuperPanelBinding) bind(component, view, R.layout.act_super_panel);
    }
}