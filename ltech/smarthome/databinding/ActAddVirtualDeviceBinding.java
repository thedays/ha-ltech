package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.config.ActAddDeviceVM;

/* loaded from: classes3.dex */
public abstract class ActAddVirtualDeviceBinding extends ViewDataBinding {
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddDeviceVM mViewmodel;
    public final RecyclerView rvCategory;
    public final RecyclerView rvDevice;
    public final LayoutTitleDefaultBinding title;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddDeviceVM viewmodel);

    protected ActAddVirtualDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutLoad, RecyclerView rvCategory, RecyclerView rvDevice, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.rvCategory = rvCategory;
        this.rvDevice = rvDevice;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddDeviceVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActAddVirtualDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddVirtualDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddVirtualDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_virtual_device, root, attachToRoot, component);
    }

    public static ActAddVirtualDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddVirtualDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddVirtualDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_virtual_device, null, false, component);
    }

    public static ActAddVirtualDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddVirtualDeviceBinding bind(View view, Object component) {
        return (ActAddVirtualDeviceBinding) bind(component, view, R.layout.act_add_virtual_device);
    }
}