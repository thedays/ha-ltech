package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemLightGroupControlSubDeviceBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView27;
    public final CircleImageView civColor;
    public final ColorSeekBar csbColorBar;
    public final Group groupColor;
    public final LightBrtBar sbBrt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvColorTip;
    public final AppCompatTextView tvCtPercent;

    protected ItemLightGroupControlSubDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView27, CircleImageView civColor, ColorSeekBar csbColorBar, Group groupColor, LightBrtBar sbBrt, AppCompatTextView tvBrt, AppCompatTextView tvColorTip, AppCompatTextView tvCtPercent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView27 = appCompatTextView27;
        this.civColor = civColor;
        this.csbColorBar = csbColorBar;
        this.groupColor = groupColor;
        this.sbBrt = sbBrt;
        this.tvBrt = tvBrt;
        this.tvColorTip = tvColorTip;
        this.tvCtPercent = tvCtPercent;
    }

    public static ItemLightGroupControlSubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightGroupControlSubDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemLightGroupControlSubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_light_group_control_sub_device, root, attachToRoot, component);
    }

    public static ItemLightGroupControlSubDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightGroupControlSubDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemLightGroupControlSubDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_light_group_control_sub_device, null, false, component);
    }

    public static ItemLightGroupControlSubDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightGroupControlSubDeviceBinding bind(View view, Object component) {
        return (ItemLightGroupControlSubDeviceBinding) bind(component, view, R.layout.item_light_group_control_sub_device);
    }
}