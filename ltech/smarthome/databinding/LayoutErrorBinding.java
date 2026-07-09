package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class LayoutErrorBinding extends ViewDataBinding {
    public final AppCompatButton errorRetryView;
    public final ConstraintLayout errorView;
    public final AppCompatImageView ivError;
    public final AppCompatTextView tvErrorTip;

    protected LayoutErrorBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton errorRetryView, ConstraintLayout errorView, AppCompatImageView ivError, AppCompatTextView tvErrorTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.errorRetryView = errorRetryView;
        this.errorView = errorView;
        this.ivError = ivError;
        this.tvErrorTip = tvErrorTip;
    }

    public static LayoutErrorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutErrorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutErrorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_error, root, attachToRoot, component);
    }

    public static LayoutErrorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutErrorBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutErrorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_error, null, false, component);
    }

    public static LayoutErrorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutErrorBinding bind(View view, Object component) {
        return (LayoutErrorBinding) bind(component, view, R.layout.layout_error);
    }
}