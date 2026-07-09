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
import com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public abstract class DialogSelectCgdActionBinding extends ViewDataBinding {
    public final CgdActionSelectView cgdActionView;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RadioImageTextView radioOff;
    public final RadioImageTextView radioOn;
    public final AppCompatTextView tvAction;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSelectCgdActionBinding(Object _bindingComponent, View _root, int _localFieldCount, CgdActionSelectView cgdActionView, RadioImageTextView radioOff, RadioImageTextView radioOn, AppCompatTextView tvAction, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cgdActionView = cgdActionView;
        this.radioOff = radioOff;
        this.radioOn = radioOn;
        this.tvAction = tvAction;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectCgdActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectCgdActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectCgdActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_cgd_action, root, attachToRoot, component);
    }

    public static DialogSelectCgdActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectCgdActionBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectCgdActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_cgd_action, null, false, component);
    }

    public static DialogSelectCgdActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectCgdActionBinding bind(View view, Object component) {
        return (DialogSelectCgdActionBinding) bind(component, view, R.layout.dialog_select_cgd_action);
    }
}