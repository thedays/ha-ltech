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
public abstract class LayoutLoadingBinding extends ViewDataBinding {
    public final LinearLayout loadingView;
    public final AppCompatTextView tvContent;

    protected LayoutLoadingBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout loadingView, AppCompatTextView tvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.loadingView = loadingView;
        this.tvContent = tvContent;
    }

    public static LayoutLoadingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutLoadingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutLoadingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_loading, root, attachToRoot, component);
    }

    public static LayoutLoadingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutLoadingBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutLoadingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_loading, null, false, component);
    }

    public static LayoutLoadingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutLoadingBinding bind(View view, Object component) {
        return (LayoutLoadingBinding) bind(component, view, R.layout.layout_loading);
    }
}