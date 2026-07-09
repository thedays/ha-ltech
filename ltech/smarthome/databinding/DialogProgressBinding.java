package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogProgressBinding extends ViewDataBinding {
    public final RelativeLayout bkg;
    public final LinearLayout boxButton;
    public final RelativeLayout boxRoot;
    public final TextView btnSelectNegative;
    public final TextView btnSelectOther;
    public final TextView btnSelectPositive;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final ProgressBar progressBar;
    public final TextView tvProgress;
    public final TextView txtDialogContentTip;
    public final TextView txtDialogTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogProgressBinding(Object _bindingComponent, View _root, int _localFieldCount, RelativeLayout bkg, LinearLayout boxButton, RelativeLayout boxRoot, TextView btnSelectNegative, TextView btnSelectOther, TextView btnSelectPositive, ProgressBar progressBar, TextView tvProgress, TextView txtDialogContentTip, TextView txtDialogTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bkg = bkg;
        this.boxButton = boxButton;
        this.boxRoot = boxRoot;
        this.btnSelectNegative = btnSelectNegative;
        this.btnSelectOther = btnSelectOther;
        this.btnSelectPositive = btnSelectPositive;
        this.progressBar = progressBar;
        this.tvProgress = tvProgress;
        this.txtDialogContentTip = txtDialogContentTip;
        this.txtDialogTitle = txtDialogTitle;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogProgressBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogProgressBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogProgressBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_progress, root, attachToRoot, component);
    }

    public static DialogProgressBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogProgressBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogProgressBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_progress, null, false, component);
    }

    public static DialogProgressBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogProgressBinding bind(View view, Object component) {
        return (DialogProgressBinding) bind(component, view, R.layout.dialog_progress);
    }
}