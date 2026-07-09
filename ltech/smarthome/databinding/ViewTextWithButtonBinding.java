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
public abstract class ViewTextWithButtonBinding extends ViewDataBinding {
    public final AppCompatImageView ivPlus;
    public final AppCompatImageView ivReduce;
    public final AppCompatTextView tvValue;

    protected ViewTextWithButtonBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivPlus, AppCompatImageView ivReduce, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivPlus = ivPlus;
        this.ivReduce = ivReduce;
        this.tvValue = tvValue;
    }

    public static ViewTextWithButtonBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTextWithButtonBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewTextWithButtonBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_text_with_button, root, attachToRoot, component);
    }

    public static ViewTextWithButtonBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTextWithButtonBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewTextWithButtonBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_text_with_button, null, false, component);
    }

    public static ViewTextWithButtonBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTextWithButtonBinding bind(View view, Object component) {
        return (ViewTextWithButtonBinding) bind(component, view, R.layout.view_text_with_button);
    }
}