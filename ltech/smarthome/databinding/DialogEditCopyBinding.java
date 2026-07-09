package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

/* loaded from: classes3.dex */
public abstract class DialogEditCopyBinding extends ViewDataBinding {
    public final AppCompatEditText etContent;
    public final AppCompatImageView ivCopy;
    public final ConstraintLayout layoutBg;
    public final RelativeLayout linearLayout;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvContentTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogEditCopyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etContent, AppCompatImageView ivCopy, ConstraintLayout layoutBg, RelativeLayout linearLayout, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvContentTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etContent = etContent;
        this.ivCopy = ivCopy;
        this.layoutBg = layoutBg;
        this.linearLayout = linearLayout;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvContentTip = tvContentTip;
        this.tvTitle = tvTitle;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogEditCopyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditCopyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogEditCopyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_edit_copy, root, attachToRoot, component);
    }

    public static DialogEditCopyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditCopyBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogEditCopyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_edit_copy, null, false, component);
    }

    public static DialogEditCopyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditCopyBinding bind(View view, Object component) {
        return (DialogEditCopyBinding) bind(component, view, R.layout.dialog_edit_copy);
    }
}