package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemRs8SubDeviceBinding extends ViewDataBinding {
    public final RelativeLayout layoutOpenDir;
    public final AppCompatTextView tvSub;
    public final AppCompatTextView tvTitle;

    protected ItemRs8SubDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, RelativeLayout layoutOpenDir, AppCompatTextView tvSub, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutOpenDir = layoutOpenDir;
        this.tvSub = tvSub;
        this.tvTitle = tvTitle;
    }

    public static ItemRs8SubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRs8SubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemRs8SubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_rs8_sub_device, root, attachToRoot, component);
    }

    public static ItemRs8SubDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRs8SubDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemRs8SubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_rs8_sub_device, null, false, component);
    }

    public static ItemRs8SubDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRs8SubDeviceBinding bind(View view, Object component) {
        return (ItemRs8SubDeviceBinding) bind(component, view, R.layout.item_rs8_sub_device);
    }
}