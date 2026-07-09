package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.rs8.ActRs8VM;

/* loaded from: classes3.dex */
public abstract class ActRs8AddSubDeviceBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActRs8VM mViewmodel;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final TextView tv;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActRs8VM viewmodel);

    protected ActRs8AddSubDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rv, LayoutTitleDefaultBinding title, TextView tv) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rv = rv;
        this.title = title;
        this.tv = tv;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActRs8VM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActRs8AddSubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8AddSubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRs8AddSubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rs8_add_sub_device, root, attachToRoot, component);
    }

    public static ActRs8AddSubDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8AddSubDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRs8AddSubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rs8_add_sub_device, null, false, component);
    }

    public static ActRs8AddSubDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8AddSubDeviceBinding bind(View view, Object component) {
        return (ActRs8AddSubDeviceBinding) bind(component, view, R.layout.act_rs8_add_sub_device);
    }
}