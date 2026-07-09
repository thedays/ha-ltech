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
public abstract class LayoutEmptyBinding extends ViewDataBinding {
    public final AppCompatButton emptyRetryView;
    public final ConstraintLayout emptyView;
    public final AppCompatImageView ivEmpty;
    public final AppCompatTextView tvEmptyTip;

    protected LayoutEmptyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton emptyRetryView, ConstraintLayout emptyView, AppCompatImageView ivEmpty, AppCompatTextView tvEmptyTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.emptyRetryView = emptyRetryView;
        this.emptyView = emptyView;
        this.ivEmpty = ivEmpty;
        this.tvEmptyTip = tvEmptyTip;
    }

    public static LayoutEmptyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutEmptyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutEmptyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_empty, root, attachToRoot, component);
    }

    public static LayoutEmptyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutEmptyBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutEmptyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_empty, null, false, component);
    }

    public static LayoutEmptyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutEmptyBinding bind(View view, Object component) {
        return (LayoutEmptyBinding) bind(component, view, R.layout.layout_empty);
    }
}