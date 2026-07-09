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

/* loaded from: classes3.dex */
public abstract class ItemSelectDeviceInGroupBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final ContentLoadingProgressBar sendLoading;
    public final AppCompatTextView syncTv;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvPlaceInfo;

    protected ItemSelectDeviceInGroupBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivSelect, ContentLoadingProgressBar sendLoading, AppCompatTextView syncTv, AppCompatTextView tvDeviceName, AppCompatTextView tvPlaceInfo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.sendLoading = sendLoading;
        this.syncTv = syncTv;
        this.tvDeviceName = tvDeviceName;
        this.tvPlaceInfo = tvPlaceInfo;
    }

    public static ItemSelectDeviceInGroupBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceInGroupBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectDeviceInGroupBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_in_group, root, attachToRoot, component);
    }

    public static ItemSelectDeviceInGroupBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceInGroupBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectDeviceInGroupBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_in_group, null, false, component);
    }

    public static ItemSelectDeviceInGroupBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceInGroupBinding bind(View view, Object component) {
        return (ItemSelectDeviceInGroupBinding) bind(component, view, R.layout.item_select_device_in_group);
    }
}