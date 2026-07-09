package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemTailBinding extends ViewDataBinding {
    public final AppCompatTextView tvMain;

    protected ItemTailBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvMain = tvMain;
    }

    public static ItemTailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_tail, root, attachToRoot, component);
    }

    public static ItemTailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTailBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_tail, null, false, component);
    }

    public static ItemTailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTailBinding bind(View view, Object component) {
        return (ItemTailBinding) bind(component, view, R.layout.item_tail);
    }
}