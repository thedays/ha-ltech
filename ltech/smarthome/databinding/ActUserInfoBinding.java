package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.user.ActUserInfoVM;

/* loaded from: classes3.dex */
public abstract class ActUserInfoBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActUserInfoVM mViewmodel;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActUserInfoVM viewmodel);

    protected ActUserInfoBinding(Object _bindingComponent, View _root, int _localFieldCount, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActUserInfoVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActUserInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActUserInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActUserInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_user_info, root, attachToRoot, component);
    }

    public static ActUserInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActUserInfoBinding inflate(LayoutInflater inflater, Object component) {
        return (ActUserInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_user_info, null, false, component);
    }

    public static ActUserInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActUserInfoBinding bind(View view, Object component) {
        return (ActUserInfoBinding) bind(component, view, R.layout.act_user_info);
    }
}