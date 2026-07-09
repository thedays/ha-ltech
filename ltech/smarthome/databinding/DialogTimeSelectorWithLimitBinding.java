package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
public abstract class DialogTimeSelectorWithLimitBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final AppCompatImageView ivSelect2;
    public final RelativeLayout layoutChooseOne;
    public final RelativeLayout layoutChooseTwo;
    public final ConstraintLayout layoutSelect;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mMinUnit;

    @Bindable
    protected String mSecUnit;

    @Bindable
    protected Boolean mWithUnit;
    public final RecyclerView pickViewMin;
    public final RecyclerView pickerViewSec;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvMinTip;
    public final AppCompatTextView tvSecTip;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setMinUnit(String minUnit);

    public abstract void setSecUnit(String secUnit);

    public abstract void setWithUnit(Boolean withUnit);

    protected DialogTimeSelectorWithLimitBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, AppCompatImageView ivSelect2, RelativeLayout layoutChooseOne, RelativeLayout layoutChooseTwo, ConstraintLayout layoutSelect, RecyclerView pickViewMin, RecyclerView pickerViewSec, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvMinTip, AppCompatTextView tvSecTip, AppCompatTextView tvTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.ivSelect2 = ivSelect2;
        this.layoutChooseOne = layoutChooseOne;
        this.layoutChooseTwo = layoutChooseTwo;
        this.layoutSelect = layoutSelect;
        this.pickViewMin = pickViewMin;
        this.pickerViewSec = pickerViewSec;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
        this.tvMinTip = tvMinTip;
        this.tvSecTip = tvSecTip;
        this.tvTip = tvTip;
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

    public static DialogTimeSelectorWithLimitBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimeSelectorWithLimitBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogTimeSelectorWithLimitBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_time_selector_with_limit, root, attachToRoot, component);
    }

    public static DialogTimeSelectorWithLimitBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimeSelectorWithLimitBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogTimeSelectorWithLimitBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_time_selector_with_limit, null, false, component);
    }

    public static DialogTimeSelectorWithLimitBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTimeSelectorWithLimitBinding bind(View view, Object component) {
        return (DialogTimeSelectorWithLimitBinding) bind(component, view, R.layout.dialog_time_selector_with_limit);
    }
}