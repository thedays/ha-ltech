package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class DialogSelectLuxBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final AppCompatImageView ivSelectTwo;
    public final RelativeLayout layoutChooseOne;
    public final RelativeLayout layoutChooseTwo;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final StepSetView seekbarLux;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSelectLuxBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, AppCompatImageView ivSelectTwo, RelativeLayout layoutChooseOne, RelativeLayout layoutChooseTwo, StepSetView seekbarLux, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.ivSelectTwo = ivSelectTwo;
        this.layoutChooseOne = layoutChooseOne;
        this.layoutChooseTwo = layoutChooseTwo;
        this.seekbarLux = seekbarLux;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectLuxBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectLuxBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectLuxBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_lux, root, attachToRoot, component);
    }

    public static DialogSelectLuxBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectLuxBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectLuxBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_lux, null, false, component);
    }

    public static DialogSelectLuxBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectLuxBinding bind(View view, Object component) {
        return (DialogSelectLuxBinding) bind(component, view, R.layout.dialog_select_lux);
    }
}