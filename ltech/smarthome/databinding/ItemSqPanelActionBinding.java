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
public abstract class ItemSqPanelActionBinding extends ViewDataBinding {
    public final AppCompatImageView ivAction;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvAction;
    public final AppCompatTextView tvAction1;
    public final AppCompatTextView tvActionTip;

    protected ItemSqPanelActionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivAction, ConstraintLayout layoutItemBg, AppCompatTextView tvAction, AppCompatTextView tvAction1, AppCompatTextView tvActionTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivAction = ivAction;
        this.layoutItemBg = layoutItemBg;
        this.tvAction = tvAction;
        this.tvAction1 = tvAction1;
        this.tvActionTip = tvActionTip;
    }

    public static ItemSqPanelActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSqPanelActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSqPanelActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sq_panel_action, root, attachToRoot, component);
    }

    public static ItemSqPanelActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSqPanelActionBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSqPanelActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sq_panel_action, null, false, component);
    }

    public static ItemSqPanelActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSqPanelActionBinding bind(View view, Object component) {
        return (ItemSqPanelActionBinding) bind(component, view, R.layout.item_sq_panel_action);
    }
}