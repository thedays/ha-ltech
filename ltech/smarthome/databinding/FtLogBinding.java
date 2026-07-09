package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.intercom.FtLogVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class FtLogBinding extends ViewDataBinding {

    @Bindable
    protected FtLogVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rv;

    public abstract void setViewmodel(FtLogVM viewmodel);

    protected FtLogBinding(Object _bindingComponent, View _root, int _localFieldCount, SmartRefreshLayout refreshLayout, RecyclerView rv) {
        super(_bindingComponent, _root, _localFieldCount);
        this.refreshLayout = refreshLayout;
        this.rv = rv;
    }

    public FtLogVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_log, root, attachToRoot, component);
    }

    public static FtLogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtLogBinding inflate(LayoutInflater inflater, Object component) {
        return (FtLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_log, null, false, component);
    }

    public static FtLogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtLogBinding bind(View view, Object component) {
        return (FtLogBinding) bind(component, view, R.layout.ft_log);
    }
}