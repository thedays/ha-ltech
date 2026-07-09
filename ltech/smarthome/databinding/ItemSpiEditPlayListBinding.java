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
public abstract class ItemSpiEditPlayListBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final AppCompatImageView ivSort;
    public final AppCompatTextView tvName;

    protected ItemSpiEditPlayListBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, AppCompatImageView ivSort, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.ivSort = ivSort;
        this.tvName = tvName;
    }

    public static ItemSpiEditPlayListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpiEditPlayListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSpiEditPlayListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_spi_edit_play_list, root, attachToRoot, component);
    }

    public static ItemSpiEditPlayListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpiEditPlayListBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSpiEditPlayListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_spi_edit_play_list, null, false, component);
    }

    public static ItemSpiEditPlayListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpiEditPlayListBinding bind(View view, Object component) {
        return (ItemSpiEditPlayListBinding) bind(component, view, R.layout.item_spi_edit_play_list);
    }
}