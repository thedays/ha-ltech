package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.SelectItem;

/* loaded from: classes3.dex */
public abstract class ItemDefaultBinding extends ViewDataBinding {

    @Bindable
    protected SelectItem mItem;
    public final AppCompatTextView tvName;

    public abstract void setItem(SelectItem item);

    protected ItemDefaultBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvName = tvName;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public static ItemDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_default, root, attachToRoot, component);
    }

    public static ItemDefaultBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDefaultBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_default, null, false, component);
    }

    public static ItemDefaultBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDefaultBinding bind(View view, Object component) {
        return (ItemDefaultBinding) bind(component, view, R.layout.item_default);
    }
}