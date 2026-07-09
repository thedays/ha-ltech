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
public abstract class DialogSinglePickerBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView pickerView;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSinglePickerBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView pickerView, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.pickerView = pickerView;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSinglePickerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSinglePickerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSinglePickerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_single_picker, root, attachToRoot, component);
    }

    public static DialogSinglePickerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSinglePickerBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSinglePickerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_single_picker, null, false, component);
    }

    public static DialogSinglePickerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSinglePickerBinding bind(View view, Object component) {
        return (DialogSinglePickerBinding) bind(component, view, R.layout.dialog_single_picker);
    }
}