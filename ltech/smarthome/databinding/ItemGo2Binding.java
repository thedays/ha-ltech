package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.GoItem;

/* loaded from: classes3.dex */
public abstract class ItemGo2Binding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;

    @Bindable
    protected GoItem mItem;

    public abstract void setItem(GoItem item);

    protected ItemGo2Binding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
    }

    public GoItem getItem() {
        return this.mItem;
    }

    public static ItemGo2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGo2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemGo2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_go_2, root, attachToRoot, component);
    }

    public static ItemGo2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGo2Binding inflate(LayoutInflater inflater, Object component) {
        return (ItemGo2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_go_2, null, false, component);
    }

    public static ItemGo2Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGo2Binding bind(View view, Object component) {
        return (ItemGo2Binding) bind(component, view, R.layout.item_go_2);
    }
}