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
public abstract class ItemSelectWithPlaceCg485Binding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvPlaceInfo;
    public final View viewDivider;

    protected ItemSelectWithPlaceCg485Binding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivSelect, AppCompatTextView tvDeviceName, AppCompatTextView tvPlaceInfo, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.tvDeviceName = tvDeviceName;
        this.tvPlaceInfo = tvPlaceInfo;
        this.viewDivider = viewDivider;
    }

    public static ItemSelectWithPlaceCg485Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectWithPlaceCg485Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectWithPlaceCg485Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_with_place_cg485, root, attachToRoot, component);
    }

    public static ItemSelectWithPlaceCg485Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectWithPlaceCg485Binding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectWithPlaceCg485Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_with_place_cg485, null, false, component);
    }

    public static ItemSelectWithPlaceCg485Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectWithPlaceCg485Binding bind(View view, Object component) {
        return (ItemSelectWithPlaceCg485Binding) bind(component, view, R.layout.item_select_with_place_cg485);
    }
}