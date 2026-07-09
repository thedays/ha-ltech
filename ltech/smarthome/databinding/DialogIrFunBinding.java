package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogIrFunBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogIrFunBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogIrFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIrFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogIrFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_ir_fun, root, attachToRoot, component);
    }

    public static DialogIrFunBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIrFunBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogIrFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_ir_fun, null, false, component);
    }

    public static DialogIrFunBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIrFunBinding bind(View view, Object component) {
        return (DialogIrFunBinding) bind(component, view, R.layout.dialog_ir_fun);
    }
}