package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogWheelSelectListBinding extends ViewDataBinding {
    public final View line2;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvSpecify;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogWheelSelectListBinding(Object _bindingComponent, View _root, int _localFieldCount, View line2, RecyclerView rvContent, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvSpecify, AppCompatTextView tvTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.line2 = line2;
        this.rvContent = rvContent;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvSpecify = tvSpecify;
        this.tvTip = tvTip;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogWheelSelectListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogWheelSelectListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogWheelSelectListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_wheel_select_list, root, attachToRoot, component);
    }

    public static DialogWheelSelectListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogWheelSelectListBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogWheelSelectListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_wheel_select_list, null, false, component);
    }

    public static DialogWheelSelectListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogWheelSelectListBinding bind(View view, Object component) {
        return (DialogWheelSelectListBinding) bind(component, view, R.layout.dialog_wheel_select_list);
    }
}