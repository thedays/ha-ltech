package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.DaliTextSeekBarView;

/* loaded from: classes3.dex */
public abstract class DialogSelectDimRangeBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView33;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final DaliTextSeekBarView seekbarMaxBtr;
    public final DaliTextSeekBarView seekbarMinBtr;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvSave;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogSelectDimRangeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView33, ConstraintLayout layoutBg, DaliTextSeekBarView seekbarMaxBtr, DaliTextSeekBarView seekbarMinBtr, AppCompatTextView tvCancel, AppCompatTextView tvSave) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView33 = appCompatTextView33;
        this.layoutBg = layoutBg;
        this.seekbarMaxBtr = seekbarMaxBtr;
        this.seekbarMinBtr = seekbarMinBtr;
        this.tvCancel = tvCancel;
        this.tvSave = tvSave;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectDimRangeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectDimRangeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectDimRangeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_dim_range, root, attachToRoot, component);
    }

    public static DialogSelectDimRangeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectDimRangeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectDimRangeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_dim_range, null, false, component);
    }

    public static DialogSelectDimRangeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectDimRangeBinding bind(View view, Object component) {
        return (DialogSelectDimRangeBinding) bind(component, view, R.layout.dialog_select_dim_range);
    }
}