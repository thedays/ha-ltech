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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogSwitchPatternSelectorBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView33;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvSave;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogSwitchPatternSelectorBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView33, ConstraintLayout layoutBg, RecyclerView rvContent, AppCompatTextView tvCancel, AppCompatTextView tvSave) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView33 = appCompatTextView33;
        this.layoutBg = layoutBg;
        this.rvContent = rvContent;
        this.tvCancel = tvCancel;
        this.tvSave = tvSave;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSwitchPatternSelectorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSwitchPatternSelectorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSwitchPatternSelectorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_switch_pattern_selector, root, attachToRoot, component);
    }

    public static DialogSwitchPatternSelectorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSwitchPatternSelectorBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSwitchPatternSelectorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_switch_pattern_selector, null, false, component);
    }

    public static DialogSwitchPatternSelectorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSwitchPatternSelectorBinding bind(View view, Object component) {
        return (DialogSwitchPatternSelectorBinding) bind(component, view, R.layout.dialog_switch_pattern_selector);
    }
}