package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.my.FtMessageNoticeVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class FtMessageNoticeBinding extends ViewDataBinding {
    public final ItemMessageDataFooterBinding footerView;

    @Bindable
    protected FtMessageNoticeVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvContent;

    public abstract void setViewmodel(FtMessageNoticeVM viewmodel);

    protected FtMessageNoticeBinding(Object _bindingComponent, View _root, int _localFieldCount, ItemMessageDataFooterBinding footerView, SmartRefreshLayout refreshLayout, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.footerView = footerView;
        this.refreshLayout = refreshLayout;
        this.rvContent = rvContent;
    }

    public FtMessageNoticeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtMessageNoticeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMessageNoticeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtMessageNoticeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_message_notice, root, attachToRoot, component);
    }

    public static FtMessageNoticeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMessageNoticeBinding inflate(LayoutInflater inflater, Object component) {
        return (FtMessageNoticeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_message_notice, null, false, component);
    }

    public static FtMessageNoticeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMessageNoticeBinding bind(View view, Object component) {
        return (FtMessageNoticeBinding) bind(component, view, R.layout.ft_message_notice);
    }
}