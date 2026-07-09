package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class DialogSelectKDuvBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final StepSetView seekbarDuv;
    public final StepSetView seekbarK;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogSelectKDuvBinding(Object _bindingComponent, View _root, int _localFieldCount, StepSetView seekbarDuv, StepSetView seekbarK, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.seekbarDuv = seekbarDuv;
        this.seekbarK = seekbarK;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectKDuvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectKDuvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectKDuvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_k_duv, root, attachToRoot, component);
    }

    public static DialogSelectKDuvBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectKDuvBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectKDuvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_k_duv, null, false, component);
    }

    public static DialogSelectKDuvBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectKDuvBinding bind(View view, Object component) {
        return (DialogSelectKDuvBinding) bind(component, view, R.layout.dialog_select_k_duv);
    }
}