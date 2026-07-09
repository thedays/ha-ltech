package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogCenterTipBinding extends ViewDataBinding {
    public final LinearLayout layoutBottom;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogCenterTipBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBottom, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBottom = layoutBottom;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTip = tvTip;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogCenterTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCenterTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogCenterTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_center_tip, root, attachToRoot, component);
    }

    public static DialogCenterTipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCenterTipBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogCenterTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_center_tip, null, false, component);
    }

    public static DialogCenterTipBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCenterTipBinding bind(View view, Object component) {
        return (DialogCenterTipBinding) bind(component, view, R.layout.dialog_center_tip);
    }
}