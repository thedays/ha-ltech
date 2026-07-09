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
public abstract class ViewImageTextBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatTextView tvName;

    protected ViewImageTextBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.tvName = tvName;
    }

    public static ViewImageTextBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewImageTextBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewImageTextBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_image_text, root, attachToRoot, component);
    }

    public static ViewImageTextBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewImageTextBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewImageTextBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_image_text, null, false, component);
    }

    public static ViewImageTextBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewImageTextBinding bind(View view, Object component) {
        return (ViewImageTextBinding) bind(component, view, R.layout.view_image_text);
    }
}