package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogE6TipBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvKnobAction;
    public final AppCompatTextView tvInstruction;
    public final AppCompatTextView tvTipMessage;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogE6TipBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvKnobAction, AppCompatTextView tvInstruction, AppCompatTextView tvTipMessage) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvKnobAction = rvKnobAction;
        this.tvInstruction = tvInstruction;
        this.tvTipMessage = tvTipMessage;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogE6TipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogE6TipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogE6TipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_e6_tip, root, attachToRoot, component);
    }

    public static DialogE6TipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogE6TipBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogE6TipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_e6_tip, null, false, component);
    }

    public static DialogE6TipBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogE6TipBinding bind(View view, Object component) {
        return (DialogE6TipBinding) bind(component, view, R.layout.dialog_e6_tip);
    }
}