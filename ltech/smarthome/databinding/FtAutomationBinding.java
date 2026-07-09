package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.control.FtAutomationVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class FtAutomationBinding extends ViewDataBinding {
    public final FrameLayout layoutSearch;

    @Bindable
    protected FtAutomationVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvContent;
    public final ItemSearchBarNoEditBinding searchBar;

    public abstract void setViewmodel(FtAutomationVM viewmodel);

    protected FtAutomationBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutSearch, SmartRefreshLayout refreshLayout, RecyclerView rvContent, ItemSearchBarNoEditBinding searchBar) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutSearch = layoutSearch;
        this.refreshLayout = refreshLayout;
        this.rvContent = rvContent;
        this.searchBar = searchBar;
    }

    public FtAutomationVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_automation, root, attachToRoot, component);
    }

    public static FtAutomationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAutomationBinding inflate(LayoutInflater inflater, Object component) {
        return (FtAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_automation, null, false, component);
    }

    public static FtAutomationBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAutomationBinding bind(View view, Object component) {
        return (FtAutomationBinding) bind(component, view, R.layout.ft_automation);
    }
}