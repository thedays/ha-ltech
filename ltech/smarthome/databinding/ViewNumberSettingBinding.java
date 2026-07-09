package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewNumberSettingBinding extends ViewDataBinding {
    public final AppCompatEditText etAdd;
    public final AppCompatImageView ivAddMinus;
    public final AppCompatImageView ivAddPlus;

    protected ViewNumberSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etAdd, AppCompatImageView ivAddMinus, AppCompatImageView ivAddPlus) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etAdd = etAdd;
        this.ivAddMinus = ivAddMinus;
        this.ivAddPlus = ivAddPlus;
    }

    public static ViewNumberSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewNumberSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewNumberSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_number_setting, root, attachToRoot, component);
    }

    public static ViewNumberSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewNumberSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewNumberSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_number_setting, null, false, component);
    }

    public static ViewNumberSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewNumberSettingBinding bind(View view, Object component) {
        return (ViewNumberSettingBinding) bind(component, view, R.layout.view_number_setting);
    }
}