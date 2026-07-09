package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSensorSubDeviceBinding extends ViewDataBinding {
    public final AppCompatImageView ivNameShow;
    public final LinearLayout layoutContent;
    public final RelativeLayout layoutEnvironmentLog;
    public final RelativeLayout layoutName;
    public final RelativeLayout layoutSenseRecord;
    public final AppCompatTextView tvCt;
    public final AppCompatTextView tvLux;
    public final AppCompatTextView tvName;

    protected ItemSensorSubDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivNameShow, LinearLayout layoutContent, RelativeLayout layoutEnvironmentLog, RelativeLayout layoutName, RelativeLayout layoutSenseRecord, AppCompatTextView tvCt, AppCompatTextView tvLux, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivNameShow = ivNameShow;
        this.layoutContent = layoutContent;
        this.layoutEnvironmentLog = layoutEnvironmentLog;
        this.layoutName = layoutName;
        this.layoutSenseRecord = layoutSenseRecord;
        this.tvCt = tvCt;
        this.tvLux = tvLux;
        this.tvName = tvName;
    }

    public static ItemSensorSubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSensorSubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSensorSubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sensor_sub_device, root, attachToRoot, component);
    }

    public static ItemSensorSubDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSensorSubDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSensorSubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sensor_sub_device, null, false, component);
    }

    public static ItemSensorSubDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSensorSubDeviceBinding bind(View view, Object component) {
        return (ItemSensorSubDeviceBinding) bind(component, view, R.layout.item_sensor_sub_device);
    }
}