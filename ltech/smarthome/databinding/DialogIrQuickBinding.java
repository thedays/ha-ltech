package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogIrQuickBinding extends ViewDataBinding {
    public final View dividerLine;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivSendTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogIrQuickBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, AppCompatImageView ivDeviceMore, AppCompatImageView ivSendTip, RecyclerView rvContent, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.ivDeviceMore = ivDeviceMore;
        this.ivSendTip = ivSendTip;
        this.rvContent = rvContent;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogIrQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIrQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogIrQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_ir_quick, root, attachToRoot, component);
    }

    public static DialogIrQuickBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIrQuickBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogIrQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_ir_quick, null, false, component);
    }

    public static DialogIrQuickBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIrQuickBinding bind(View view, Object component) {
        return (DialogIrQuickBinding) bind(component, view, R.layout.dialog_ir_quick);
    }
}