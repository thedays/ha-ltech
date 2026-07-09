package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogCenterDaliModifyParamBinding extends ViewDataBinding {
    public final LinearLayout layoutBottom;
    public final ConstraintLayout layoutTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvType;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogCenterDaliModifyParamBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBottom, ConstraintLayout layoutTip, RecyclerView rvContent, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle, AppCompatTextView tvType) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBottom = layoutBottom;
        this.layoutTip = layoutTip;
        this.rvContent = rvContent;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
        this.tvType = tvType;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogCenterDaliModifyParamBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCenterDaliModifyParamBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogCenterDaliModifyParamBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_center_dali_modify_param, root, attachToRoot, component);
    }

    public static DialogCenterDaliModifyParamBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCenterDaliModifyParamBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogCenterDaliModifyParamBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_center_dali_modify_param, null, false, component);
    }

    public static DialogCenterDaliModifyParamBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCenterDaliModifyParamBinding bind(View view, Object component) {
        return (DialogCenterDaliModifyParamBinding) bind(component, view, R.layout.dialog_center_dali_modify_param);
    }
}