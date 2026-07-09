package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.control.FtRoomVM;

/* loaded from: classes3.dex */
public abstract class FtDevice2Binding extends ViewDataBinding {

    @Bindable
    protected Boolean mDeviceVisible;

    @Bindable
    protected FtRoomVM mViewmodel;
    public final RecyclerView rvDevice;

    public abstract void setDeviceVisible(Boolean deviceVisible);

    public abstract void setViewmodel(FtRoomVM viewmodel);

    protected FtDevice2Binding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvDevice) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvDevice = rvDevice;
    }

    public FtRoomVM getViewmodel() {
        return this.mViewmodel;
    }

    public Boolean getDeviceVisible() {
        return this.mDeviceVisible;
    }

    public static FtDevice2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDevice2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtDevice2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_device2, root, attachToRoot, component);
    }

    public static FtDevice2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDevice2Binding inflate(LayoutInflater inflater, Object component) {
        return (FtDevice2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_device2, null, false, component);
    }

    public static FtDevice2Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDevice2Binding bind(View view, Object component) {
        return (FtDevice2Binding) bind(component, view, R.layout.ft_device2);
    }
}