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
import com.ltech.smarthome.ui.control.FtRoomVM;

/* loaded from: classes3.dex */
public abstract class ActSearchDeviceBinding extends ViewDataBinding {
    public final FrameLayout layoutSearch;

    @Bindable
    protected FtRoomVM mViewmodel;
    public final RecyclerView rvDevice;
    public final ItemSearchBarBinding searchBar;

    public abstract void setViewmodel(FtRoomVM viewmodel);

    protected ActSearchDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutSearch, RecyclerView rvDevice, ItemSearchBarBinding searchBar) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutSearch = layoutSearch;
        this.rvDevice = rvDevice;
        this.searchBar = searchBar;
    }

    public FtRoomVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSearchDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSearchDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_device, root, attachToRoot, component);
    }

    public static ActSearchDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSearchDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_device, null, false, component);
    }

    public static ActSearchDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchDeviceBinding bind(View view, Object component) {
        return (ActSearchDeviceBinding) bind(component, view, R.layout.act_search_device);
    }
}