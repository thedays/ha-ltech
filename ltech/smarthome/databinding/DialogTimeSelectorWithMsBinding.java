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
public abstract class DialogTimeSelectorWithMsBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mMinUnit;

    @Bindable
    protected String mSecUnit;

    @Bindable
    protected Boolean mWithUnit;
    public final RecyclerView pickViewMin;
    public final RecyclerView pickViewMs;
    public final RecyclerView pickViewSec;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvMinTip;
    public final AppCompatTextView tvMsTip;
    public final AppCompatTextView tvSecTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setMinUnit(String minUnit);

    public abstract void setSecUnit(String secUnit);

    public abstract void setWithUnit(Boolean withUnit);

    protected DialogTimeSelectorWithMsBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView pickViewMin, RecyclerView pickViewMs, RecyclerView pickViewSec, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvMinTip, AppCompatTextView tvMsTip, AppCompatTextView tvSecTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.pickViewMin = pickViewMin;
        this.pickViewMs = pickViewMs;
        this.pickViewSec = pickViewSec;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
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

    public String getMinUnit() {
        return this.mMinUnit;
    }

    public String getSecUnit() {
        return this.mSecUnit;
    }

    public static DialogTimeSelectorWithMsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimeSelectorWithMsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogTimeSelectorWithMsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_time_selector_with_ms, root, attachToRoot, component);
    }

    public static DialogTimeSelectorWithMsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimeSelectorWithMsBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogTimeSelectorWithMsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_time_selector_with_ms, null, false, component);
    }

    public static DialogTimeSelectorWithMsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimeSelectorWithMsBinding bind(View view, Object component) {
        return (DialogTimeSelectorWithMsBinding) bind(component, view, R.layout.dialog_time_selector_with_ms);
    }
}