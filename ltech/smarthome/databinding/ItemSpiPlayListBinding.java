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
public abstract class ItemSpiPlayListBinding extends ViewDataBinding {
    public final AppCompatImageView ivMore;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutMode;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvSub;

    protected ItemSpiPlayListBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMore, AppCompatImageView ivSelect, ConstraintLayout layoutMode, AppCompatTextView tvMain, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMore = ivMore;
        this.ivSelect = ivSelect;
        this.layoutMode = layoutMode;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
    }

    public static ItemSpiPlayListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpiPlayListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSpiPlayListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_spi_play_list, root, attachToRoot, component);
    }

    public static ItemSpiPlayListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpiPlayListBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSpiPlayListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_spi_play_list, null, false, component);
    }

    public static ItemSpiPlayListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpiPlayListBinding bind(View view, Object component) {
        return (ItemSpiPlayListBinding) bind(component, view, R.layout.item_spi_play_list);
    }
}