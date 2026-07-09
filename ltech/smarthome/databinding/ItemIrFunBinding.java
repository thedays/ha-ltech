package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemIrFunBinding extends ViewDataBinding {
    public final LinearLayout layoutBg;
    public final AppCompatTextView tvName;

    protected ItemIrFunBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBg = layoutBg;
        this.tvName = tvName;
    }

    public static ItemIrFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIrFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemIrFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_ir_fun, root, attachToRoot, component);
    }

    public static ItemIrFunBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIrFunBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemIrFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_ir_fun, null, false, component);
    }

    public static ItemIrFunBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIrFunBinding bind(View view, Object component) {
        return (ItemIrFunBinding) bind(component, view, R.layout.item_ir_fun);
    }
}