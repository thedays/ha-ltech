package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemMeshScanReplaceBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivIcon;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvDeviceName;

    protected ItemMeshScanReplaceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatImageView ivIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvDeviceName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.ivIcon = ivIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvDeviceName = tvDeviceName;
    }

    public static ItemMeshScanReplaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshScanReplaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMeshScanReplaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mesh_scan_replace, root, attachToRoot, component);
    }

    public static ItemMeshScanReplaceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshScanReplaceBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMeshScanReplaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mesh_scan_replace, null, false, component);
    }

    public static ItemMeshScanReplaceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshScanReplaceBinding bind(View view, Object component) {
        return (ItemMeshScanReplaceBinding) bind(component, view, R.layout.item_mesh_scan_replace);
    }
}