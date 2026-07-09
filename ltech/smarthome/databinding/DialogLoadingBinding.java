package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class DialogLoadingBinding extends ViewDataBinding {
    public final AppCompatTextView tvContent;

    protected DialogLoadingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvContent = tvContent;
    }

    public static DialogLoadingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLoadingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogLoadingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_loading, root, attachToRoot, component);
    }

    public static DialogLoadingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLoadingBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogLoadingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_loading, null, false, component);
    }

    public static DialogLoadingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLoadingBinding bind(View view, Object component) {
        return (DialogLoadingBinding) bind(component, view, R.layout.dialog_loading);
    }
}