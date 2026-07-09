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
public abstract class DialogIntercomTimePickerBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mMinUnit;

    @Bindable
    protected String mSecUnit;

    @Bindable
    protected Boolean mWithUnit;
    public final RecyclerView pickViewDay;
    public final RecyclerView pickViewHour;
    public final RecyclerView pickViewMin;
    public final RecyclerView pickViewMonth;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setMinUnit(String minUnit);

    public abstract void setSecUnit(String secUnit);

    public abstract void setWithUnit(Boolean withUnit);

    protected DialogIntercomTimePickerBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView pickViewDay, RecyclerView pickViewHour, RecyclerView pickViewMin, RecyclerView pickViewMonth, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.pickViewDay = pickViewDay;
        this.pickViewHour = pickViewHour;
        this.pickViewMin = pickViewMin;
        this.pickViewMonth = pickViewMonth;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
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

    public static DialogIntercomTimePickerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIntercomTimePickerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogIntercomTimePickerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_intercom_time_picker, root, attachToRoot, component);
    }

    public static DialogIntercomTimePickerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIntercomTimePickerBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogIntercomTimePickerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_intercom_time_picker, null, false, component);
    }

    public static DialogIntercomTimePickerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIntercomTimePickerBinding bind(View view, Object component) {
        return (DialogIntercomTimePickerBinding) bind(component, view, R.layout.dialog_intercom_time_picker);
    }
}