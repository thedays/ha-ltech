package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.config.ActMeshScanVM;

/* loaded from: classes3.dex */
public abstract class ActivityActMeshAddAllDeviceBinding extends ViewDataBinding {
    public final AppCompatButton btNext;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActMeshScanVM mViewmodel;
    public final RecyclerView rvContent;
    public final TextView tvNum;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActMeshScanVM viewmodel);

    protected ActivityActMeshAddAllDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btNext, RecyclerView rvContent, TextView tvNum) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btNext = btNext;
        this.rvContent = rvContent;
        this.tvNum = tvNum;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActMeshScanVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActivityActMeshAddAllDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityActMeshAddAllDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityActMeshAddAllDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_act_mesh_add_all_device, root, attachToRoot, component);
    }

    public static ActivityActMeshAddAllDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityActMeshAddAllDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityActMeshAddAllDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_act_mesh_add_all_device, null, false, component);
    }

    public static ActivityActMeshAddAllDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityActMeshAddAllDeviceBinding bind(View view, Object component) {
        return (ActivityActMeshAddAllDeviceBinding) bind(component, view, R.layout.activity_act_mesh_add_all_device);
    }
}