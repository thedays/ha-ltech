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
public abstract class DialogPairBinding extends ViewDataBinding {
    public final View dividerLine;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvPair;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogPairBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvPair, AppCompatTextView tvTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvPair = tvPair;
        this.tvTip = tvTip;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogPairBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogPairBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogPairBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_pair, root, attachToRoot, component);
    }

    public static DialogPairBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogPairBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogPairBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_pair, null, false, component);
    }

    public static DialogPairBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogPairBinding bind(View view, Object component) {
        return (DialogPairBinding) bind(component, view, R.layout.dialog_pair);
    }
}