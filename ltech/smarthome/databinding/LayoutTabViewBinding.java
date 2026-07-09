package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class LayoutTabViewBinding extends ViewDataBinding {
    public final AppCompatImageView ivTab;
    public final AppCompatTextView tvTab;

    protected LayoutTabViewBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivTab, AppCompatTextView tvTab) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivTab = ivTab;
        this.tvTab = tvTab;
    }

    public static LayoutTabViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTabViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutTabViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_tab_view, root, attachToRoot, component);
    }

    public static LayoutTabViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTabViewBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutTabViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_tab_view, null, false, component);
    }

    public static LayoutTabViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTabViewBinding bind(View view, Object component) {
        return (LayoutTabViewBinding) bind(component, view, R.layout.layout_tab_view);
    }
}