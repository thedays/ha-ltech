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
import com.ltech.smarthome.view.MyTimePickerView;

/* loaded from: classes3.dex */
public abstract class DialogTimePickerBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mMinUnit;

    @Bindable
    protected String mSecUnit;

    @Bindable
    protected Boolean mWithUnit;
    public final MyTimePickerView pickViewMin;
    public final MyTimePickerView pickerViewSec;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvMinTip;
    public final AppCompatTextView tvSecTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setMinUnit(String minUnit);

    public abstract void setSecUnit(String secUnit);

    public abstract void setWithUnit(Boolean withUnit);

    protected DialogTimePickerBinding(Object _bindingComponent, View _root, int _localFieldCount, MyTimePickerView pickViewMin, MyTimePickerView pickerViewSec, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvMinTip, AppCompatTextView tvSecTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.pickViewMin = pickViewMin;
        this.pickerViewSec = pickerViewSec;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
        this.tvMinTip = tvMinTip;
        this.tvSecTip = tvSecTip;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Boolean getWithUnit() {
        return this.mWithUnit;
    }

    public String getMinUnit() {
        return this.mMinUnit;
    }

    public String getSecUnit() {
        return this.mSecUnit;
    }

    public static DialogTimePickerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimePickerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogTimePickerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_time_picker, root, attachToRoot, component);
    }

    public static DialogTimePickerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimePickerBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogTimePickerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_time_picker, null, false, component);
    }

    public static DialogTimePickerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimePickerBinding bind(View view, Object component) {
        return (DialogTimePickerBinding) bind(component, view, R.layout.dialog_time_picker);
    }
}