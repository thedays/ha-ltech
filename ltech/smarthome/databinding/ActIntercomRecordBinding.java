package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomRecordVM;

/* loaded from: classes3.dex */
public abstract class ActIntercomRecordBinding extends ViewDataBinding {
    public final FrameLayout fragmentContainer;
    public final AppCompatImageView ivBack;
    public final ConstraintLayout layoutTab;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIntercomRecordVM mViewmodel;
    public final TabLayout tabTitle;
    public final AppCompatTextView tvEdit;
    public final View vTitleBg;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIntercomRecordVM viewmodel);

    protected ActIntercomRecordBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout fragmentContainer, AppCompatImageView ivBack, ConstraintLayout layoutTab, TabLayout tabTitle, AppCompatTextView tvEdit, View vTitleBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
        this.ivBack = ivBack;
        this.layoutTab = layoutTab;
        this.tabTitle = tabTitle;
        this.tvEdit = tvEdit;
        this.vTitleBg = vTitleBg;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public ActIntercomRecordVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntercomRecordBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomRecordBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntercomRecordBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_record, root, attachToRoot, component);
    }

    public static ActIntercomRecordBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomRecordBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntercomRecordBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_record, null, false, component);
    }

    public static ActIntercomRecordBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomRecordBinding bind(View view, Object component) {
        return (ActIntercomRecordBinding) bind(component, view, R.layout.act_intercom_record);
    }
}