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
public abstract class ItemAsPanelRelatedInfoBinding extends ViewDataBinding {
    public final AppCompatImageView ivMore;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvInfo;
    public final AppCompatTextView tvOnOff;

    protected ItemAsPanelRelatedInfoBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMore, ConstraintLayout layoutItemBg, AppCompatTextView tvInfo, AppCompatTextView tvOnOff) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMore = ivMore;
        this.layoutItemBg = layoutItemBg;
        this.tvInfo = tvInfo;
        this.tvOnOff = tvOnOff;
    }

    public static ItemAsPanelRelatedInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAsPanelRelatedInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemAsPanelRelatedInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_as_panel_related_info, root, attachToRoot, component);
    }

    public static ItemAsPanelRelatedInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAsPanelRelatedInfoBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemAsPanelRelatedInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_as_panel_related_info, null, false, component);
    }

    public static ItemAsPanelRelatedInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAsPanelRelatedInfoBinding bind(View view, Object component) {
        return (ItemAsPanelRelatedInfoBinding) bind(component, view, R.layout.item_as_panel_related_info);
    }
}