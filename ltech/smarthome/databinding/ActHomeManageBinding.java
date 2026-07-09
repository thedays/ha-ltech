package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.home.ActHomeManageVM;

/* loaded from: classes3.dex */
public abstract class ActHomeManageBinding extends ViewDataBinding {
    public final RecyclerView layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActHomeManageVM mViewmodel;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActHomeManageVM viewmodel);

    protected ActHomeManageBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView layoutLoad, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActHomeManageVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActHomeManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHomeManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_manage, root, attachToRoot, component);
    }

    public static ActHomeManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHomeManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_manage, null, false, component);
    }

    public static ActHomeManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeManageBinding bind(View view, Object component) {
        return (ActHomeManageBinding) bind(component, view, R.layout.act_home_manage);
    }
}