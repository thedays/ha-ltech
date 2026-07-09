package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.my.FtMessageHomeVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class FtMessageHomeBinding extends ViewDataBinding {
    public final ItemMessageDataFooterBinding footerView;

    @Bindable
    protected FtMessageHomeVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvContent;

    public abstract void setViewmodel(FtMessageHomeVM viewmodel);

    protected FtMessageHomeBinding(Object _bindingComponent, View _root, int _localFieldCount, ItemMessageDataFooterBinding footerView, SmartRefreshLayout refreshLayout, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.footerView = footerView;
        this.refreshLayout = refreshLayout;
        this.rvContent = rvContent;
    }

    public FtMessageHomeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtMessageHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMessageHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtMessageHomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_message_home, root, attachToRoot, component);
    }

    public static FtMessageHomeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMessageHomeBinding inflate(LayoutInflater inflater, Object component) {
        return (FtMessageHomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_message_home, null, false, component);
    }

    public static FtMessageHomeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMessageHomeBinding bind(View view, Object component) {
        return (FtMessageHomeBinding) bind(component, view, R.layout.ft_message_home);
    }
}