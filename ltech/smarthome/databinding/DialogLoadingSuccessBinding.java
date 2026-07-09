package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class DialogLoadingSuccessBinding extends ViewDataBinding {
    public final AppCompatTextView tvContent;

    protected DialogLoadingSuccessBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvContent = tvContent;
    }

    public static DialogLoadingSuccessBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLoadingSuccessBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogLoadingSuccessBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_loading_success, root, attachToRoot, component);
    }

    public static DialogLoadingSuccessBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLoadingSuccessBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogLoadingSuccessBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_loading_success, null, false, component);
    }

    public static DialogLoadingSuccessBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLoadingSuccessBinding bind(View view, Object component) {
        return (DialogLoadingSuccessBinding) bind(component, view, R.layout.dialog_loading_success);
    }
}