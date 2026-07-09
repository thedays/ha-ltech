package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogTimeIntervalSelectorBinding extends ViewDataBinding {
    public final TextView colon1;
    public final TextView colon2;
    public final Group groupTime;
    public final AppCompatImageView ivSel1;
    public final AppCompatImageView ivSel2;
    public final AppCompatTextView lable1;
    public final AppCompatTextView lable2;
    public final View line1;
    public final View line2;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mMinUnit;

    @Bindable
    protected String mSecUnit;

    @Bindable
    protected Boolean mWithUnit;
    public final RecyclerView pickViewMin;
    public final RecyclerView pickViewMin2;
    public final RecyclerView pickerViewSec;
    public final RecyclerView pickerViewSec2;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvMinTip;
    public final AppCompatTextView tvSecTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setMinUnit(String minUnit);

    public abstract void setSecUnit(String secUnit);

    public abstract void setWithUnit(Boolean withUnit);

    protected DialogTimeIntervalSelectorBinding(Object _bindingComponent, View _root, int _localFieldCount, TextView colon1, TextView colon2, Group groupTime, AppCompatImageView ivSel1, AppCompatImageView ivSel2, AppCompatTextView lable1, AppCompatTextView lable2, View line1, View line2, RecyclerView pickViewMin, RecyclerView pickViewMin2, RecyclerView pickerViewSec, RecyclerView pickerViewSec2, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvMinTip, AppCompatTextView tvSecTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.colon1 = colon1;
        this.colon2 = colon2;
        this.groupTime = groupTime;
        this.ivSel1 = ivSel1;
        this.ivSel2 = ivSel2;
        this.lable1 = lable1;
        this.lable2 = lable2;
        this.line1 = line1;
        this.line2 = line2;
        this.pickViewMin = pickViewMin;
        this.pickViewMin2 = pickViewMin2;
        this.pickerViewSec = pickerViewSec;
        this.pickerViewSec2 = pickerViewSec2;
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

    public static DialogTimeIntervalSelectorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimeIntervalSelectorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogTimeIntervalSelectorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_time_interval_selector, root, attachToRoot, component);
    }

    public static DialogTimeIntervalSelectorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimeIntervalSelectorBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogTimeIntervalSelectorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_time_interval_selector, null, false, component);
    }

    public static DialogTimeIntervalSelectorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimeIntervalSelectorBinding bind(View view, Object component) {
        return (DialogTimeIntervalSelectorBinding) bind(component, view, R.layout.dialog_time_interval_selector);
    }
}