package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ItemLightGroupControlBinding extends ViewDataBinding {
    public final AppCompatImageView ivDeviceMore;
    public final SwitchButton sb;
    public final AppCompatTextView tvFloor;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvVirtual;
    public final View view16;

    protected ItemLightGroupControlBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDeviceMore, SwitchButton sb, AppCompatTextView tvFloor, AppCompatTextView tvName, AppCompatTextView tvVirtual, View view16) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDeviceMore = ivDeviceMore;
        this.sb = sb;
        this.tvFloor = tvFloor;
        this.tvName = tvName;
        this.tvVirtual = tvVirtual;
        this.view16 = view16;
    }

    public static ItemLightGroupControlBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightGroupControlBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemLightGroupControlBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_light_group_control, root, attachToRoot, component);
    }

    public static ItemLightGroupControlBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightGroupControlBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemLightGroupControlBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_light_group_control, null, false, component);
    }

    public static ItemLightGroupControlBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemLightGroupControlBinding bind(View view, Object component) {
        return (ItemLightGroupControlBinding) bind(component, view, R.layout.item_light_group_control);
    }
}