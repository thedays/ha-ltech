package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogSelectDimCurveBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView33;
    public final AppCompatImageView ivLinear;
    public final AppCompatImageView ivLinearSelect;
    public final AppCompatImageView ivLog;
    public final AppCompatImageView ivLogSelect;
    public final ConstraintLayout layoutBg;
    public final ConstraintLayout layoutLinear;
    public final ConstraintLayout layoutLog;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvLinear;
    public final AppCompatTextView tvLog;
    public final AppCompatTextView tvSave;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogSelectDimCurveBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView33, AppCompatImageView ivLinear, AppCompatImageView ivLinearSelect, AppCompatImageView ivLog, AppCompatImageView ivLogSelect, ConstraintLayout layoutBg, ConstraintLayout layoutLinear, ConstraintLayout layoutLog, AppCompatTextView tvCancel, AppCompatTextView tvLinear, AppCompatTextView tvLog, AppCompatTextView tvSave) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView33 = appCompatTextView33;
        this.ivLinear = ivLinear;
        this.ivLinearSelect = ivLinearSelect;
        this.ivLog = ivLog;
        this.ivLogSelect = ivLogSelect;
        this.layoutBg = layoutBg;
        this.layoutLinear = layoutLinear;
        this.layoutLog = layoutLog;
        this.tvCancel = tvCancel;
        this.tvLinear = tvLinear;
        this.tvLog = tvLog;
        this.tvSave = tvSave;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectDimCurveBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectDimCurveBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectDimCurveBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_dim_curve, root, attachToRoot, component);
    }

    public static DialogSelectDimCurveBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectDimCurveBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectDimCurveBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_dim_curve, null, false, component);
    }

    public static DialogSelectDimCurveBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectDimCurveBinding bind(View view, Object component) {
        return (DialogSelectDimCurveBinding) bind(component, view, R.layout.dialog_select_dim_curve);
    }
}