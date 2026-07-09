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
public abstract class DialogRegModeBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvEmail;
    public final AppCompatTextView tvPhone;
    public final View vMiddle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogRegModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvEmail, AppCompatTextView tvPhone, View vMiddle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvEmail = tvEmail;
        this.tvPhone = tvPhone;
        this.vMiddle = vMiddle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogRegModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRegModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogRegModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_reg_mode, root, attachToRoot, component);
    }

    public static DialogRegModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRegModeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogRegModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_reg_mode, null, false, component);
    }

    public static DialogRegModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRegModeBinding bind(View view, Object component) {
        return (DialogRegModeBinding) bind(component, view, R.layout.dialog_reg_mode);
    }
}