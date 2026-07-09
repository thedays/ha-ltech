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
public abstract class DialogLeftTitleSelectListBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvSub;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogLeftTitleSelectListBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent, AppCompatTextView tvSub, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
        this.tvSub = tvSub;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogLeftTitleSelectListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLeftTitleSelectListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogLeftTitleSelectListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_left_title_select_list, root, attachToRoot, component);
    }

    public static DialogLeftTitleSelectListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLeftTitleSelectListBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogLeftTitleSelectListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_left_title_select_list, null, false, component);
    }

    public static DialogLeftTitleSelectListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLeftTitleSelectListBinding bind(View view, Object component) {
        return (DialogLeftTitleSelectListBinding) bind(component, view, R.layout.dialog_left_title_select_list);
    }
}