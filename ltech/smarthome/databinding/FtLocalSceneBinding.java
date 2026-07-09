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
import com.ltech.smarthome.ui.control.FtSceneVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class FtLocalSceneBinding extends ViewDataBinding {
    public final FrameLayout layoutSearch;

    @Bindable
    protected FtSceneVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rv;
    public final ItemSearchBarNoEditBinding searchBar;

    public abstract void setViewmodel(FtSceneVM viewmodel);

    protected FtLocalSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutSearch, SmartRefreshLayout refreshLayout, RecyclerView rv, ItemSearchBarNoEditBinding searchBar) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutSearch = layoutSearch;
        this.refreshLayout = refreshLayout;
        this.rv = rv;
        this.searchBar = searchBar;
    }

    public FtSceneVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtLocalSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtLocalSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtLocalSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_local_scene, root, attachToRoot, component);
    }

    public static FtLocalSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtLocalSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (FtLocalSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_local_scene, null, false, component);
    }

    public static FtLocalSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtLocalSceneBinding bind(View view, Object component) {
        return (FtLocalSceneBinding) bind(component, view, R.layout.ft_local_scene);
    }
}