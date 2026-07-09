package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSpiModeBinding extends ViewDataBinding {
    public final AppCompatImageView ivMode;
    public final AppCompatImageView ivSelect;
    public final LinearLayout layoutMode;
    public final AppCompatTextView tvMode;

    protected ItemSpiModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMode, AppCompatImageView ivSelect, LinearLayout layoutMode, AppCompatTextView tvMode) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMode = ivMode;
        this.ivSelect = ivSelect;
        this.layoutMode = layoutMode;
        this.tvMode = tvMode;
    }

    public static ItemSpiModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpiModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSpiModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_spi_mode, root, attachToRoot, component);
    }

    public static ItemSpiModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpiModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSpiModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_spi_mode, null, false, component);
    }

    public static ItemSpiModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpiModeBinding bind(View view, Object component) {
        return (ItemSpiModeBinding) bind(component, view, R.layout.item_spi_mode);
    }
}