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
public abstract class DialogIrLearnBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView42;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final AppCompatTextView tvTime;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogIrLearnBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView42, ConstraintLayout layoutBg, AppCompatTextView tvTime) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView42 = appCompatTextView42;
        this.layoutBg = layoutBg;
        this.tvTime = tvTime;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogIrLearnBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIrLearnBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogIrLearnBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_ir_learn, root, attachToRoot, component);
    }

    public static DialogIrLearnBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIrLearnBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogIrLearnBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_ir_learn, null, false, component);
    }

    public static DialogIrLearnBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogIrLearnBinding bind(View view, Object component) {
        return (DialogIrLearnBinding) bind(component, view, R.layout.dialog_ir_learn);
    }
}