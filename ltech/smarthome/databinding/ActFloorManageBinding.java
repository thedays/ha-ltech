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
import com.ltech.smarthome.ui.home.ActFloorManageVM;

/* loaded from: classes3.dex */
public abstract class ActFloorManageBinding extends ViewDataBinding {
    public final RecyclerView layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActFloorManageVM mViewmodel;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActFloorManageVM viewmodel);

    protected ActFloorManageBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView layoutLoad, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActFloorManageVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActFloorManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFloorManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActFloorManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_floor_manage, root, attachToRoot, component);
    }

    public static ActFloorManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFloorManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ActFloorManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_floor_manage, null, false, component);
    }

    public static ActFloorManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFloorManageBinding bind(View view, Object component) {
        return (ActFloorManageBinding) bind(component, view, R.layout.act_floor_manage);
    }
}