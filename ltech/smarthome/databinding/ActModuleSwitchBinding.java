package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActModuleSwitchVM;

/* loaded from: classes3.dex */
public abstract class ActModuleSwitchBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivEdit;
    public final AppCompatTextView ivSwitchStatus;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActModuleSwitchVM mViewmodel;
    public final Toolbar title;
    public final AppCompatTextView tvSwitchStatus;
    public final AppCompatTextView tvTitle;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActModuleSwitchVM viewmodel);

    protected ActModuleSwitchBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, AppCompatImageView ivEdit, AppCompatTextView ivSwitchStatus, Toolbar title, AppCompatTextView tvSwitchStatus, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.ivEdit = ivEdit;
        this.ivSwitchStatus = ivSwitchStatus;
        this.title = title;
        this.tvSwitchStatus = tvSwitchStatus;
        this.tvTitle = tvTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActModuleSwitchVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActModuleSwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActModuleSwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActModuleSwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_module_switch, root, attachToRoot, component);
    }

    public static ActModuleSwitchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActModuleSwitchBinding inflate(LayoutInflater inflater, Object component) {
        return (ActModuleSwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_module_switch, null, false, component);
    }

    public static ActModuleSwitchBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActModuleSwitchBinding bind(View view, Object component) {
        return (ActModuleSwitchBinding) bind(component, view, R.layout.act_module_switch);
    }
}