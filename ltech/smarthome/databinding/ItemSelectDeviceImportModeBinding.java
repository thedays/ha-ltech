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
public abstract class ItemSelectDeviceImportModeBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvImport;
    public final AppCompatTextView tvPlaceInfo;

    protected ItemSelectDeviceImportModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatTextView tvDeviceName, AppCompatTextView tvImport, AppCompatTextView tvPlaceInfo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.tvDeviceName = tvDeviceName;
        this.tvImport = tvImport;
        this.tvPlaceInfo = tvPlaceInfo;
    }

    public static ItemSelectDeviceImportModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceImportModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectDeviceImportModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_import_mode, root, attachToRoot, component);
    }

    public static ItemSelectDeviceImportModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceImportModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectDeviceImportModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_device_import_mode, null, false, component);
    }

    public static ItemSelectDeviceImportModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDeviceImportModeBinding bind(View view, Object component) {
        return (ItemSelectDeviceImportModeBinding) bind(component, view, R.layout.item_select_device_import_mode);
    }
}