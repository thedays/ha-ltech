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

/* loaded from: classes3.dex */
public abstract class ActSearchAutomationBinding extends ViewDataBinding {
    public final FrameLayout layoutSearch;

    @Bindable
    protected FtAutomationVM mViewmodel;
    public final RecyclerView rvContent;
    public final ItemSearchBarBinding searchBar;

    public abstract void setViewmodel(FtAutomationVM viewmodel);

    protected ActSearchAutomationBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutSearch, RecyclerView rvContent, ItemSearchBarBinding searchBar) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutSearch = layoutSearch;
        this.rvContent = rvContent;
        this.searchBar = searchBar;
    }

    public FtAutomationVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSearchAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSearchAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_automation, root, attachToRoot, component);
    }

    public static ActSearchAutomationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchAutomationBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSearchAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_automation, null, false, component);
    }

    public static ActSearchAutomationBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchAutomationBinding bind(View view, Object component) {
        return (ActSearchAutomationBinding) bind(component, view, R.layout.act_search_automation);
    }
}