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

/* loaded from: classes3.dex */
public abstract class DialogDelFailBinding extends ViewDataBinding {
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvContent;
    public final AppCompatTextView tvContent2;
    public final AppCompatTextView tvTitle;
    public final View vLine;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogDelFailBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutBg, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvContent, AppCompatTextView tvContent2, AppCompatTextView tvTitle, View vLine) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBg = layoutBg;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvContent = tvContent;
        this.tvContent2 = tvContent2;
        this.tvTitle = tvTitle;
        this.vLine = vLine;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogDelFailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDelFailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogDelFailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_del_fail, root, attachToRoot, component);
    }

    public static DialogDelFailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDelFailBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogDelFailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_del_fail, null, false, component);
    }

    public static DialogDelFailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDelFailBinding bind(View view, Object component) {
        return (DialogDelFailBinding) bind(component, view, R.layout.dialog_del_fail);
    }
}