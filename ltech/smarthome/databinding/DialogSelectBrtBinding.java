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
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class DialogSelectBrtBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView27;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final LightBrtBar sbBrt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSelectBrtBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView27, LightBrtBar sbBrt, AppCompatTextView tvBrt, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView27 = appCompatTextView27;
        this.sbBrt = sbBrt;
        this.tvBrt = tvBrt;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectBrtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectBrtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectBrtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_brt, root, attachToRoot, component);
    }

    public static DialogSelectBrtBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectBrtBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectBrtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_brt, null, false, component);
    }

    public static DialogSelectBrtBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectBrtBinding bind(View view, Object component) {
        return (DialogSelectBrtBinding) bind(component, view, R.layout.dialog_select_brt);
    }
}