package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemDoubleTextBinding extends ViewDataBinding {
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvSub;

    protected ItemDoubleTextBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutItemBg, AppCompatTextView tvMain, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutItemBg = layoutItemBg;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
    }

    public static ItemDoubleTextBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDoubleTextBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDoubleTextBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_double_text, root, attachToRoot, component);
    }

    public static ItemDoubleTextBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDoubleTextBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDoubleTextBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_double_text, null, false, component);
    }

    public static ItemDoubleTextBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDoubleTextBinding bind(View view, Object component) {
        return (ItemDoubleTextBinding) bind(component, view, R.layout.item_double_text);
    }
}