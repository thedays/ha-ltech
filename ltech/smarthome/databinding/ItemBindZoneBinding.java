package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemBindZoneBinding extends ViewDataBinding {
    public final AppCompatImageView ivEdit;
    public final LinearLayoutCompat layoutItem;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvState;

    protected ItemBindZoneBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEdit, LinearLayoutCompat layoutItem, ConstraintLayout layoutItemBg, AppCompatTextView tvName, AppCompatTextView tvState) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEdit = ivEdit;
        this.layoutItem = layoutItem;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
        this.tvState = tvState;
    }

    public static ItemBindZoneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBindZoneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemBindZoneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_bind_zone, root, attachToRoot, component);
    }

    public static ItemBindZoneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBindZoneBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemBindZoneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_bind_zone, null, false, component);
    }

    public static ItemBindZoneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBindZoneBinding bind(View view, Object component) {
        return (ItemBindZoneBinding) bind(component, view, R.layout.item_bind_zone);
    }
}