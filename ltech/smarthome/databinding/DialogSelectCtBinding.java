package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogSelectCtBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RangeSeekBar sbCt;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvCt;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSelectCtBinding(Object _bindingComponent, View _root, int _localFieldCount, RangeSeekBar sbCt, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvCt, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.sbCt = sbCt;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvCt = tvCt;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectCtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectCtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectCtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_ct, root, attachToRoot, component);
    }

    public static DialogSelectCtBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectCtBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectCtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_ct, null, false, component);
    }

    public static DialogSelectCtBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectCtBinding bind(View view, Object component) {
        return (DialogSelectCtBinding) bind(component, view, R.layout.dialog_select_ct);
    }
}