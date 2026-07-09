package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewEurTipFunctionBinding extends ViewDataBinding {
    public final AppCompatTextView tv;

    protected ViewEurTipFunctionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tv) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tv = tv;
    }

    public static ViewEurTipFunctionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEurTipFunctionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewEurTipFunctionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_eur_tip_function, root, attachToRoot, component);
    }

    public static ViewEurTipFunctionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEurTipFunctionBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewEurTipFunctionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_eur_tip_function, null, false, component);
    }

    public static ViewEurTipFunctionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEurTipFunctionBinding bind(View view, Object component) {
        return (ViewEurTipFunctionBinding) bind(component, view, R.layout.view_eur_tip_function);
    }
}