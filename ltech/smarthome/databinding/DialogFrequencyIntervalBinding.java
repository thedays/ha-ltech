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
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogFrequencyIntervalBinding extends ViewDataBinding {
    public final AppCompatEditText etContent;
    public final AppCompatEditText etContent2;
    public final AppCompatImageView ivDelete;
    public final AppCompatImageView ivDelete2;
    public final LinearLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;

    @Bindable
    protected ObservableField<String> mContent2;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvOk;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    public abstract void setContent2(ObservableField<String> content2);

    protected DialogFrequencyIntervalBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etContent, AppCompatEditText etContent2, AppCompatImageView ivDelete, AppCompatImageView ivDelete2, LinearLayout layoutBg, AppCompatTextView tvCancel, AppCompatTextView tvOk, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etContent = etContent;
        this.etContent2 = etContent2;
        this.ivDelete = ivDelete;
        this.ivDelete2 = ivDelete2;
        this.layoutBg = layoutBg;
        this.tvCancel = tvCancel;
        this.tvOk = tvOk;
        this.tvTitle = tvTitle;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public ObservableField<String> getContent2() {
        return this.mContent2;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogFrequencyIntervalBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFrequencyIntervalBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogFrequencyIntervalBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_frequency_interval, root, attachToRoot, component);
    }

    public static DialogFrequencyIntervalBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFrequencyIntervalBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogFrequencyIntervalBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_frequency_interval, null, false, component);
    }

    public static DialogFrequencyIntervalBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFrequencyIntervalBinding bind(View view, Object component) {
        return (DialogFrequencyIntervalBinding) bind(component, view, R.layout.dialog_frequency_interval);
    }
}