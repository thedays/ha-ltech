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
public abstract class ViewCgdLightTitleDaliBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ViewCgdLightTitleDaliBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvTip = tvTip;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ViewCgdLightTitleDaliBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdLightTitleDaliBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewCgdLightTitleDaliBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_cgd_light_title_dali, root, attachToRoot, component);
    }

    public static ViewCgdLightTitleDaliBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdLightTitleDaliBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewCgdLightTitleDaliBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_cgd_light_title_dali, null, false, component);
    }

    public static ViewCgdLightTitleDaliBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdLightTitleDaliBinding bind(View view, Object component) {
        return (ViewCgdLightTitleDaliBinding) bind(component, view, R.layout.view_cgd_light_title_dali);
    }
}