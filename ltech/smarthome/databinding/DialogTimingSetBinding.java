package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogTimingSetBinding extends ViewDataBinding {
    public final RadioGroup layoutTab;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView pickerView;
    public final RadioButton radioClose;
    public final RadioButton radioOpen;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogTimingSetBinding(Object _bindingComponent, View _root, int _localFieldCount, RadioGroup layoutTab, RecyclerView pickerView, RadioButton radioClose, RadioButton radioOpen, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutTab = layoutTab;
        this.pickerView = pickerView;
        this.radioClose = radioClose;
        this.radioOpen = radioOpen;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogTimingSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimingSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogTimingSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_timing_set, root, attachToRoot, component);
    }

    public static DialogTimingSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimingSetBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogTimingSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_timing_set, null, false, component);
    }

    public static DialogTimingSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimingSetBinding bind(View view, Object component) {
        return (DialogTimingSetBinding) bind(component, view, R.layout.dialog_timing_set);
    }
}