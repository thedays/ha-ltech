package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ItemAddInstructTitleBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvCopy;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvName;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ItemAddInstructTitleBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvCopy, AppCompatTextView tvDelete, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvCopy = tvCopy;
        this.tvDelete = tvDelete;
        this.tvName = tvName;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemAddInstructTitleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAddInstructTitleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemAddInstructTitleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_add_instruct_title, root, attachToRoot, component);
    }

    public static ItemAddInstructTitleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAddInstructTitleBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemAddInstructTitleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_add_instruct_title, null, false, component);
    }

    public static ItemAddInstructTitleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAddInstructTitleBinding bind(View view, Object component) {
        return (ItemAddInstructTitleBinding) bind(component, view, R.layout.item_add_instruct_title);
    }
}