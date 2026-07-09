package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomLoginVM;

/* loaded from: classes3.dex */
public abstract class ActIntercomTipsBinding extends ViewDataBinding {
    public final AppCompatImageView ivIntercomTips;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIntercomLoginVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvLogin;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIntercomLoginVM viewmodel);

    protected ActIntercomTipsBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIntercomTips, LayoutTitleDefaultBinding title, AppCompatTextView tvLogin) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIntercomTips = ivIntercomTips;
        this.title = title;
        this.tvLogin = tvLogin;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActIntercomLoginVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntercomTipsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomTipsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntercomTipsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_tips, root, attachToRoot, component);
    }

    public static ActIntercomTipsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomTipsBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntercomTipsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_tips, null, false, component);
    }

    public static ActIntercomTipsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomTipsBinding bind(View view, Object component) {
        return (ActIntercomTipsBinding) bind(component, view, R.layout.act_intercom_tips);
    }
}