package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.SelectItem;

/* loaded from: classes3.dex */
public abstract class ItemSelect2Binding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;

    @Bindable
    protected SelectItem mItem;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSub;

    public abstract void setItem(SelectItem item);

    protected ItemSelect2Binding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, AppCompatTextView tvName, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.tvName = tvName;
        this.tvSub = tvSub;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public static ItemSelect2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelect2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelect2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select2, root, attachToRoot, component);
    }

    public static ItemSelect2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelect2Binding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelect2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select2, null, false, component);
    }

    public static ItemSelect2Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelect2Binding bind(View view, Object component) {
        return (ItemSelect2Binding) bind(component, view, R.layout.item_select2);
    }
}