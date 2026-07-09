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
public abstract class DialogSelectItemBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSelectItemBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectItemBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_item, root, attachToRoot, component);
    }

    public static DialogSelectItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectItemBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectItemBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_item, null, false, component);
    }

    public static DialogSelectItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectItemBinding bind(View view, Object component) {
        return (DialogSelectItemBinding) bind(component, view, R.layout.dialog_select_item);
    }
}