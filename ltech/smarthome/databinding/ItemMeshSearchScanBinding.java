package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ItemMeshSearchScanBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final RelativeLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvNum;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ItemMeshSearchScanBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, RelativeLayout layoutBg, AppCompatTextView tvNum) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.layoutBg = layoutBg;
        this.tvNum = tvNum;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemMeshSearchScanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshSearchScanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMeshSearchScanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mesh_search_scan, root, attachToRoot, component);
    }

    public static ItemMeshSearchScanBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshSearchScanBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMeshSearchScanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mesh_search_scan, null, false, component);
    }

    public static ItemMeshSearchScanBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshSearchScanBinding bind(View view, Object component) {
        return (ItemMeshSearchScanBinding) bind(component, view, R.layout.item_mesh_search_scan);
    }
}