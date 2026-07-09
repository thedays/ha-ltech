package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class DialogAddColorPointBinding extends ViewDataBinding {
    public final AppCompatEditText etName;
    public final AppCompatImageView ivClear;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final StepSetView seekbarDuv;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvColor;
    public final AppCompatTextView tvCt;
    public final AppCompatTextView tvLabel;
    public final AppCompatTextView tvSave;
    public final AppCompatTextView tvTitle;
    public final View viewDivider;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogAddColorPointBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etName, AppCompatImageView ivClear, ConstraintLayout layoutBg, StepSetView seekbarDuv, AppCompatTextView tvCancel, AppCompatTextView tvColor, AppCompatTextView tvCt, AppCompatTextView tvLabel, AppCompatTextView tvSave, AppCompatTextView tvTitle, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etName = etName;
        this.ivClear = ivClear;
        this.layoutBg = layoutBg;
        this.seekbarDuv = seekbarDuv;
        this.tvCancel = tvCancel;
        this.tvColor = tvColor;
        this.tvCt = tvCt;
        this.tvLabel = tvLabel;
        this.tvSave = tvSave;
        this.tvTitle = tvTitle;
        this.viewDivider = viewDivider;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogAddColorPointBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddColorPointBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogAddColorPointBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_add_color_point, root, attachToRoot, component);
    }

    public static DialogAddColorPointBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddColorPointBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogAddColorPointBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_add_color_point, null, false, component);
    }

    public static DialogAddColorPointBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddColorPointBinding bind(View view, Object component) {
        return (DialogAddColorPointBinding) bind(component, view, R.layout.dialog_add_color_point);
    }
}