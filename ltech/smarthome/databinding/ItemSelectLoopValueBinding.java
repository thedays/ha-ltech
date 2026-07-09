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
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class ItemSelectLoopValueBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final View layoutBottom;
    public final View layoutItem;

    @Bindable
    protected SelectItem mItem;
    public final StepSetView sbBrt;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSubName;
    public final AppCompatTextView tvSubValue;

    public abstract void setItem(SelectItem item);

    protected ItemSelectLoopValueBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, View layoutBottom, View layoutItem, StepSetView sbBrt, AppCompatTextView tvName, AppCompatTextView tvSubName, AppCompatTextView tvSubValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.layoutBottom = layoutBottom;
        this.layoutItem = layoutItem;
        this.sbBrt = sbBrt;
        this.tvName = tvName;
        this.tvSubName = tvSubName;
        this.tvSubValue = tvSubValue;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public static ItemSelectLoopValueBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectLoopValueBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectLoopValueBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_loop_value, root, attachToRoot, component);
    }

    public static ItemSelectLoopValueBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectLoopValueBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectLoopValueBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_loop_value, null, false, component);
    }

    public static ItemSelectLoopValueBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectLoopValueBinding bind(View view, Object component) {
        return (ItemSelectLoopValueBinding) bind(component, view, R.layout.item_select_loop_value);
    }
}