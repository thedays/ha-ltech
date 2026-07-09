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
public abstract class ItemSelectOnOffTimeNormalBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;

    @Bindable
    protected SelectItem mItem;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSubName;

    public abstract void setItem(SelectItem item);

    protected ItemSelectOnOffTimeNormalBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, AppCompatTextView tvName, AppCompatTextView tvSubName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.tvName = tvName;
        this.tvSubName = tvSubName;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public static ItemSelectOnOffTimeNormalBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectOnOffTimeNormalBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectOnOffTimeNormalBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_on_off_time_normal, root, attachToRoot, component);
    }

    public static ItemSelectOnOffTimeNormalBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectOnOffTimeNormalBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectOnOffTimeNormalBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_on_off_time_normal, null, false, component);
    }

    public static ItemSelectOnOffTimeNormalBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectOnOffTimeNormalBinding bind(View view, Object component) {
        return (ItemSelectOnOffTimeNormalBinding) bind(component, view, R.layout.item_select_on_off_time_normal);
    }
}