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
public abstract class DialogSpQuickBinding extends ViewDataBinding {
    public final View dividerLine;
    public final AppCompatImageView ivDeviceMore;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSpQuickBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, AppCompatImageView ivDeviceMore, RecyclerView rvContent, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.ivDeviceMore = ivDeviceMore;
        this.rvContent = rvContent;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSpQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSpQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSpQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_sp_quick, root, attachToRoot, component);
    }

    public static DialogSpQuickBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSpQuickBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSpQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_sp_quick, null, false, component);
    }

    public static DialogSpQuickBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSpQuickBinding bind(View view, Object component) {
        return (DialogSpQuickBinding) bind(component, view, R.layout.dialog_sp_quick);
    }
}