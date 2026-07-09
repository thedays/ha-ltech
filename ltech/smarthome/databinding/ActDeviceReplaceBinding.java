package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActDeviceReplaceBinding extends ViewDataBinding {
    public final Guideline guideline;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvDevice;
    public final MySpinner spinnerFloor;
    public final MySpinner spinnerRoom;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActDeviceReplaceBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline, RecyclerView rvDevice, MySpinner spinnerFloor, MySpinner spinnerRoom, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline = guideline;
        this.rvDevice = rvDevice;
        this.spinnerFloor = spinnerFloor;
        this.spinnerRoom = spinnerRoom;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActDeviceReplaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceReplaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDeviceReplaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_replace, root, attachToRoot, component);
    }

    public static ActDeviceReplaceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceReplaceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDeviceReplaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_replace, null, false, component);
    }

    public static ActDeviceReplaceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceReplaceBinding bind(View view, Object component) {
        return (ActDeviceReplaceBinding) bind(component, view, R.layout.act_device_replace);
    }
}