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
public abstract class DialogEditBinding extends ViewDataBinding {
    public final AppCompatEditText etContent;
    public final AppCompatImageView ivDelete;
    public final LinearLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvOk;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogEditBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etContent, AppCompatImageView ivDelete, LinearLayout layoutBg, AppCompatTextView tvCancel, AppCompatTextView tvOk, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etContent = etContent;
        this.ivDelete = ivDelete;
        this.layoutBg = layoutBg;
        this.tvCancel = tvCancel;
        this.tvOk = tvOk;
        this.tvTitle = tvTitle;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogEditBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogEditBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_edit, root, attachToRoot, component);
    }

    public static DialogEditBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogEditBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_edit, null, false, component);
    }

    public static DialogEditBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditBinding bind(View view, Object component) {
        return (DialogEditBinding) bind(component, view, R.layout.dialog_edit);
    }
}