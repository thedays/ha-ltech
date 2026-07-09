package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemDaliLightManageBinding extends ViewDataBinding {
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivLocation;
    public final AppCompatImageView ivSelect;
    public final AppCompatTextView tvAdd;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvPlaceInfo;
    public final AppCompatTextView tvType;

    protected ItemDaliLightManageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEdit, AppCompatImageView ivIcon, AppCompatImageView ivLocation, AppCompatImageView ivSelect, AppCompatTextView tvAdd, AppCompatTextView tvDeviceName, AppCompatTextView tvPlaceInfo, AppCompatTextView tvType) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEdit = ivEdit;
        this.ivIcon = ivIcon;
        this.ivLocation = ivLocation;
        this.ivSelect = ivSelect;
        this.tvAdd = tvAdd;
        this.tvDeviceName = tvDeviceName;
        this.tvPlaceInfo = tvPlaceInfo;
        this.tvType = tvType;
    }

    public static ItemDaliLightManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDaliLightManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDaliLightManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_dali_light_manage, root, attachToRoot, component);
    }

    public static ItemDaliLightManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDaliLightManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDaliLightManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_dali_light_manage, null, false, component);
    }

    public static ItemDaliLightManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDaliLightManageBinding bind(View view, Object component) {
        return (ItemDaliLightManageBinding) bind(component, view, R.layout.item_dali_light_manage);
    }
}