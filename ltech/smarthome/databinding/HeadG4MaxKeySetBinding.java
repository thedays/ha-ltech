package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class HeadG4MaxKeySetBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView16;

    protected HeadG4MaxKeySetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView16) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView16 = appCompatImageView16;
    }

    public static HeadG4MaxKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HeadG4MaxKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (HeadG4MaxKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.head_g4_max_key_set, root, attachToRoot, component);
    }

    public static HeadG4MaxKeySetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HeadG4MaxKeySetBinding inflate(LayoutInflater inflater, Object component) {
        return (HeadG4MaxKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.head_g4_max_key_set, null, false, component);
    }

    public static HeadG4MaxKeySetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HeadG4MaxKeySetBinding bind(View view, Object component) {
        return (HeadG4MaxKeySetBinding) bind(component, view, R.layout.head_g4_max_key_set);
    }
}