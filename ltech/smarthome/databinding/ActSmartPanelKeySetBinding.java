package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySetVM;

/* loaded from: classes3.dex */
public abstract class ActSmartPanelKeySetBinding extends ViewDataBinding {
    public final AppCompatImageView ivBindObjectGo;
    public final AppCompatImageView ivKeyNameGo;
    public final ConstraintLayout layoutBindObject;
    public final ConstraintLayout layoutKeyName;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSmartPanelKeySetVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBindObject;
    public final AppCompatTextView tvBindTip;
    public final AppCompatTextView tvKeyName;
    public final AppCompatTextView tvNameTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSmartPanelKeySetVM viewmodel);

    protected ActSmartPanelKeySetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBindObjectGo, AppCompatImageView ivKeyNameGo, ConstraintLayout layoutBindObject, ConstraintLayout layoutKeyName, LayoutTitleDefaultBinding title, AppCompatTextView tvBindObject, AppCompatTextView tvBindTip, AppCompatTextView tvKeyName, AppCompatTextView tvNameTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBindObjectGo = ivBindObjectGo;
        this.ivKeyNameGo = ivKeyNameGo;
        this.layoutBindObject = layoutBindObject;
        this.layoutKeyName = layoutKeyName;
        this.title = title;
        this.tvBindObject = tvBindObject;
        this.tvBindTip = tvBindTip;
        this.tvKeyName = tvKeyName;
        this.tvNameTip = tvNameTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSmartPanelKeySetVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSmartPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSmartPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_panel_key_set, root, attachToRoot, component);
    }

    public static ActSmartPanelKeySetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelKeySetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSmartPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_panel_key_set, null, false, component);
    }

    public static ActSmartPanelKeySetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelKeySetBinding bind(View view, Object component) {
        return (ActSmartPanelKeySetBinding) bind(component, view, R.layout.act_smart_panel_key_set);
    }
}