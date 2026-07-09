package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ViewCgdLightTitleSelectBinding extends ViewDataBinding {
    public final AppCompatImageView ivAdd;
    public final AppCompatImageView ivType;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvAdd;
    public final AppCompatTextView tvType;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ViewCgdLightTitleSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivAdd, AppCompatImageView ivType, AppCompatTextView tvAdd, AppCompatTextView tvType) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivAdd = ivAdd;
        this.ivType = ivType;
        this.tvAdd = tvAdd;
        this.tvType = tvType;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ViewCgdLightTitleSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdLightTitleSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewCgdLightTitleSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_cgd_light_title_select, root, attachToRoot, component);
    }

    public static ViewCgdLightTitleSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdLightTitleSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewCgdLightTitleSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_cgd_light_title_select, null, false, component);
    }

    public static ViewCgdLightTitleSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdLightTitleSelectBinding bind(View view, Object component) {
        return (ViewCgdLightTitleSelectBinding) bind(component, view, R.layout.view_cgd_light_title_select);
    }
}