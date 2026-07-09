package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ActCurrentSetBinding extends ViewDataBinding {
    public final AppCompatImageView ivMinus;
    public final AppCompatImageView ivPlus;
    public final LinearLayout layoutCurrentSet;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mCurrent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatEditText tvCurrent;
    public final AppCompatTextView tvOutputParam;
    public final AppCompatTextView tvSetTitle;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvUnit;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setCurrent(String current);

    protected ActCurrentSetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMinus, AppCompatImageView ivPlus, LinearLayout layoutCurrentSet, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatEditText tvCurrent, AppCompatTextView tvOutputParam, AppCompatTextView tvSetTitle, AppCompatTextView tvTitle, AppCompatTextView tvUnit) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMinus = ivMinus;
        this.ivPlus = ivPlus;
        this.layoutCurrentSet = layoutCurrentSet;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvCurrent = tvCurrent;
        this.tvOutputParam = tvOutputParam;
        this.tvSetTitle = tvSetTitle;
        this.tvTitle = tvTitle;
        this.tvUnit = tvUnit;
    }

    public String getCurrent() {
        return this.mCurrent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActCurrentSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCurrentSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCurrentSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_current_set, root, attachToRoot, component);
    }

    public static ActCurrentSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCurrentSetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCurrentSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_current_set, null, false, component);
    }

    public static ActCurrentSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCurrentSetBinding bind(View view, Object component) {
        return (ActCurrentSetBinding) bind(component, view, R.layout.act_current_set);
    }
}