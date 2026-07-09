package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.user.AccountAndSecurityVM;

/* loaded from: classes3.dex */
public abstract class ActAccountAndSecurityBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected AccountAndSecurityVM mViewmodel;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(AccountAndSecurityVM viewmodel);

    protected ActAccountAndSecurityBinding(Object _bindingComponent, View _root, int _localFieldCount, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public AccountAndSecurityVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAccountAndSecurityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAccountAndSecurityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAccountAndSecurityBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_account_and_security, root, attachToRoot, component);
    }

    public static ActAccountAndSecurityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAccountAndSecurityBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAccountAndSecurityBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_account_and_security, null, false, component);
    }

    public static ActAccountAndSecurityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAccountAndSecurityBinding bind(View view, Object component) {
        return (ActAccountAndSecurityBinding) bind(component, view, R.layout.act_account_and_security);
    }
}