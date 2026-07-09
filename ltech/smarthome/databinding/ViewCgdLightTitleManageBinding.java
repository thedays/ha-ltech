package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ViewCgdLightTitleManageBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvAdd;
    public final AppCompatTextView tvEdit;
    public final AppCompatTextView tvFind;
    public final AppCompatTextView tvType;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ViewCgdLightTitleManageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvAdd, AppCompatTextView tvEdit, AppCompatTextView tvFind, AppCompatTextView tvType) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvAdd = tvAdd;
        this.tvEdit = tvEdit;
        this.tvFind = tvFind;
        this.tvType = tvType;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ViewCgdLightTitleManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdLightTitleManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewCgdLightTitleManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_cgd_light_title_manage, root, attachToRoot, component);
    }

    public static ViewCgdLightTitleManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdLightTitleManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewCgdLightTitleManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_cgd_light_title_manage, null, false, component);
    }

    public static ViewCgdLightTitleManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdLightTitleManageBinding bind(View view, Object component) {
        return (ViewCgdLightTitleManageBinding) bind(component, view, R.layout.view_cgd_light_title_manage);
    }
}