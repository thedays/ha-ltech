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
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.SelectItem;

/* loaded from: classes3.dex */
public abstract class ItemSelectKValueBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutBrt;
    public final ConstraintLayout layoutItem;

    @Bindable
    protected SelectItem mItem;
    public final RangeSeekBar sbBrt;
    public final AppCompatTextView tvKLeft;
    public final AppCompatTextView tvKRight;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSubName;

    public abstract void setItem(SelectItem item);

    protected ItemSelectKValueBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, ConstraintLayout layoutBrt, ConstraintLayout layoutItem, RangeSeekBar sbBrt, AppCompatTextView tvKLeft, AppCompatTextView tvKRight, AppCompatTextView tvName, AppCompatTextView tvSubName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.layoutBrt = layoutBrt;
        this.layoutItem = layoutItem;
        this.sbBrt = sbBrt;
        this.tvKLeft = tvKLeft;
        this.tvKRight = tvKRight;
        this.tvName = tvName;
        this.tvSubName = tvSubName;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public static ItemSelectKValueBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectKValueBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectKValueBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_k_value, root, attachToRoot, component);
    }

    public static ItemSelectKValueBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectKValueBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectKValueBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_k_value, null, false, component);
    }

    public static ItemSelectKValueBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectKValueBinding bind(View view, Object component) {
        return (ItemSelectKValueBinding) bind(component, view, R.layout.item_select_k_value);
    }
}