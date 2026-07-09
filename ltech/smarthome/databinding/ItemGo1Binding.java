package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.GoItem;

/* loaded from: classes3.dex */
public abstract class ItemGo1Binding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivSub;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected GoItem mItem;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvSub;

    public abstract void setItem(GoItem item);

    protected ItemGo1Binding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatImageView ivSub, ConstraintLayout layoutItemBg, AppCompatTextView tvMain, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.ivSub = ivSub;
        this.layoutItemBg = layoutItemBg;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
    }

    public GoItem getItem() {
        return this.mItem;
    }

    public static ItemGo1Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGo1Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemGo1Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_go_1, root, attachToRoot, component);
    }

    public static ItemGo1Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGo1Binding inflate(LayoutInflater inflater, Object component) {
        return (ItemGo1Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_go_1, null, false, component);
    }

    public static ItemGo1Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGo1Binding bind(View view, Object component) {
        return (ItemGo1Binding) bind(component, view, R.layout.item_go_1);
    }
}