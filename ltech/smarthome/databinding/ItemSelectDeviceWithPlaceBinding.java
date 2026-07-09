package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.CircularProgressView;

/* loaded from: classes3.dex */
public abstract class ItemSelectDeviceWithPlaceBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final CircularProgressView ivUpgradeWaiting;
    public final ContentLoadingProgressBar sendLoading;
    public final AppCompatTextView tvDali;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvPlaceInfo;
    public final AppCompatTextView tvVirtual;

    protected ItemSelectDeviceWithPlaceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivSelect, CircularProgressView ivUpgradeWaiting, ContentLoadingProgressBar sendLoading, AppCompatTextView tvDali, AppCompatTextView tvDeviceName, AppCompatTextView tvPlaceInfo, AppCompatTextView tvVirtual) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.ivUpgradeWaiting = ivUpgradeWaiting;
        this.sendLoading = sendLoading;
        this.tvDali = tvDali;
        this.tvDeviceName = tvDeviceName;
        this.tvPlaceInfo = tvPlaceInfo;
        this.tvVirtual = tvVirtual;
    }

    public static ItemSelectDeviceWithPlaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceWithPlaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectDeviceWithPlaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_with_place, root, attachToRoot, component);
    }

    public static ItemSelectDeviceWithPlaceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceWithPlaceBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectDeviceWithPlaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_with_place, null, false, component);
    }

    public static ItemSelectDeviceWithPlaceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceWithPlaceBinding bind(View view, Object component) {
        return (ItemSelectDeviceWithPlaceBinding) bind(component, view, R.layout.item_select_device_with_place);
    }
}