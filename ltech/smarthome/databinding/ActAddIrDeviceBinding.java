package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActAddIrDeviceBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;

    public abstract void setTitle(TitleDefault title);

    protected ActAddIrDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActAddIrDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddIrDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddIrDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_ir_device, root, attachToRoot, component);
    }

    public static ActAddIrDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddIrDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddIrDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_ir_device, null, false, component);
    }

    public static ActAddIrDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddIrDeviceBinding bind(View view, Object component) {
        return (ActAddIrDeviceBinding) bind(component, view, R.layout.act_add_ir_device);
    }
}