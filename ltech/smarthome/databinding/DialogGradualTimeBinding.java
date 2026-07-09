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
public abstract class DialogGradualTimeBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mHourUnit;

    @Bindable
    protected String mMinUnit;

    @Bindable
    protected String mMsUnit;

    @Bindable
    protected String mSecUnit;

    @Bindable
    protected Boolean mWithUnit;
    public final MyTimePickerView pickViewHour;
    public final MyTimePickerView pickViewMin;
    public final MyTimePickerView pickerViewMs;
    public final MyTimePickerView pickerViewSec;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvMinHour;
    public final AppCompatTextView tvMinTip;
    public final AppCompatTextView tvMsTip;
    public final AppCompatTextView tvSecTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setHourUnit(String hourUnit);

    public abstract void setMinUnit(String minUnit);

    public abstract void setMsUnit(String msUnit);

    public abstract void setSecUnit(String secUnit);

    public abstract void setWithUnit(Boolean withUnit);

    protected DialogGradualTimeBinding(Object _bindingComponent, View _root, int _localFieldCount, MyTimePickerView pickViewHour, MyTimePickerView pickViewMin, MyTimePickerView pickerViewMs, MyTimePickerView pickerViewSec, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvMinHour, AppCompatTextView tvMinTip, AppCompatTextView tvMsTip, AppCompatTextView tvSecTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.pickViewHour = pickViewHour;
        this.pickViewMin = pickViewMin;
        this.pickerViewMs = pickerViewMs;
        this.pickerViewSec = pickerViewSec;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
        this.tvMinHour = tvMinHour;
        this.tvMinTip = tvMinTip;
        this.tvMsTip = tvMsTip;
        this.tvSecTip = tvSecTip;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Boolean getWithUnit() {
        return this.mWithUnit;
    }

    public String getHourUnit() {
        return this.mHourUnit;
    }

    public String getMinUnit() {
        return this.mMinUnit;
    }

    public String getSecUnit() {
        return this.mSecUnit;
    }

    public String getMsUnit() {
        return this.mMsUnit;
    }

    public static DialogGradualTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogGradualTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogGradualTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_gradual_time, root, attachToRoot, component);
    }

    public static DialogGradualTimeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogGradualTimeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogGradualTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_gradual_time, null, false, component);
    }

    public static DialogGradualTimeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogGradualTimeBinding bind(View view, Object component) {
        return (DialogGradualTimeBinding) bind(component, view, R.layout.dialog_gradual_time);
    }
}