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
public abstract class LayoutExtBinding extends ViewDataBinding {
    public final AppCompatButton extRetryView;
    public final ConstraintLayout extView;
    public final AppCompatImageView ivExt;
    public final AppCompatTextView tvExtTip;

    protected LayoutExtBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton extRetryView, ConstraintLayout extView, AppCompatImageView ivExt, AppCompatTextView tvExtTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.extRetryView = extRetryView;
        this.extView = extView;
        this.ivExt = ivExt;
        this.tvExtTip = tvExtTip;
    }

    public static LayoutExtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutExtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutExtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_ext, root, attachToRoot, component);
    }

    public static LayoutExtBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutExtBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutExtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_ext, null, false, component);
    }

    public static LayoutExtBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutExtBinding bind(View view, Object component) {
        return (LayoutExtBinding) bind(component, view, R.layout.layout_ext);
    }
}