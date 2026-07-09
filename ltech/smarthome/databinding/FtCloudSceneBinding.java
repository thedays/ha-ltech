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
import com.ltech.smarthome.ui.control.FtCloudSceneVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class FtCloudSceneBinding extends ViewDataBinding {
    public final FrameLayout layoutSearch;

    @Bindable
    protected FtCloudSceneVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvContent;
    public final ItemSearchBarNoEditBinding searchBar;

    public abstract void setViewmodel(FtCloudSceneVM viewmodel);

    protected FtCloudSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutSearch, SmartRefreshLayout refreshLayout, RecyclerView rvContent, ItemSearchBarNoEditBinding searchBar) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutSearch = layoutSearch;
        this.refreshLayout = refreshLayout;
        this.rvContent = rvContent;
        this.searchBar = searchBar;
    }

    public FtCloudSceneVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtCloudSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCloudSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtCloudSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_cloud_scene, root, attachToRoot, component);
    }

    public static FtCloudSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCloudSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (FtCloudSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_cloud_scene, null, false, component);
    }

    public static FtCloudSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCloudSceneBinding bind(View view, Object component) {
        return (FtCloudSceneBinding) bind(component, view, R.layout.ft_cloud_scene);
    }
}