package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogExecuteDelayTimeSelectorBinding extends ViewDataBinding {
    public final AppCompatImageView ivSel1;
    public final AppCompatImageView ivSel2;
    public final AppCompatTextView lable1;
    public final AppCompatTextView lable2;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mMinUnit;

    @Bindable
    protected String mSecUnit;

    @Bindable
    protected Boolean mWithUnit;
    public final ConstraintLayout parent;
    public final RecyclerView pickViewMin;
    public final RecyclerView pickerViewSec;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvMinTip;
    public final AppCompatTextView tvSecTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setMinUnit(String minUnit);

    public abstract void setSecUnit(String secUnit);

    public abstract void setWithUnit(Boolean withUnit);

    protected DialogExecuteDelayTimeSelectorBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSel1, AppCompatImageView ivSel2, AppCompatTextView lable1, AppCompatTextView lable2, ConstraintLayout parent, RecyclerView pickViewMin, RecyclerView pickerViewSec, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvMinTip, AppCompatTextView tvSecTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSel1 = ivSel1;
        this.ivSel2 = ivSel2;
        this.lable1 = lable1;
        this.lable2 = lable2;
        this.parent = parent;
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

    public static DialogExecuteDelayTimeSelectorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogExecuteDelayTimeSelectorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogExecuteDelayTimeSelectorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_execute_delay_time_selector, root, attachToRoot, component);
    }

    public static DialogExecuteDelayTimeSelectorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogExecuteDelayTimeSelectorBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogExecuteDelayTimeSelectorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_execute_delay_time_selector, null, false, component);
    }

    public static DialogExecuteDelayTimeSelectorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogExecuteDelayTimeSelectorBinding bind(View view, Object component) {
        return (DialogExecuteDelayTimeSelectorBinding) bind(component, view, R.layout.dialog_execute_delay_time_selector);
    }
}