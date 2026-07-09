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
public abstract class DialogRgbFunctionTipBinding extends ViewDataBinding {
    public final AppCompatImageView ivTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogRgbFunctionTipBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivTip, AppCompatTextView tvConfirm, AppCompatTextView tvTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivTip = ivTip;
        this.tvConfirm = tvConfirm;
        this.tvTip = tvTip;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogRgbFunctionTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRgbFunctionTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogRgbFunctionTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_rgb_function_tip, root, attachToRoot, component);
    }

    public static DialogRgbFunctionTipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRgbFunctionTipBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogRgbFunctionTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_rgb_function_tip, null, false, component);
    }

    public static DialogRgbFunctionTipBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRgbFunctionTipBinding bind(View view, Object component) {
        return (DialogRgbFunctionTipBinding) bind(component, view, R.layout.dialog_rgb_function_tip);
    }
}