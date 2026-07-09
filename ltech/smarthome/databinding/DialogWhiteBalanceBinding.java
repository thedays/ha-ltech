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
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class DialogWhiteBalanceBinding extends ViewDataBinding {
    public final StepSetView layoutB;
    public final StepSetView layoutG;
    public final StepSetView layoutR;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogWhiteBalanceBinding(Object _bindingComponent, View _root, int _localFieldCount, StepSetView layoutB, StepSetView layoutG, StepSetView layoutR, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutB = layoutB;
        this.layoutG = layoutG;
        this.layoutR = layoutR;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogWhiteBalanceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogWhiteBalanceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogWhiteBalanceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_white_balance, root, attachToRoot, component);
    }

    public static DialogWhiteBalanceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogWhiteBalanceBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogWhiteBalanceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_white_balance, null, false, component);
    }

    public static DialogWhiteBalanceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogWhiteBalanceBinding bind(View view, Object component) {
        return (DialogWhiteBalanceBinding) bind(component, view, R.layout.dialog_white_balance);
    }
}