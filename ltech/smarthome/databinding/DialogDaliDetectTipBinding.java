package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogDaliDetectTipBinding extends ViewDataBinding {
    public final AppCompatImageView ivDoubt;
    public final ConstraintLayout layoutMain;
    public final LinearLayout layoutTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final ProgressBar progress;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogDaliDetectTipBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDoubt, ConstraintLayout layoutMain, LinearLayout layoutTip, ProgressBar progress, AppCompatTextView tvTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDoubt = ivDoubt;
        this.layoutMain = layoutMain;
        this.layoutTip = layoutTip;
        this.progress = progress;
        this.tvTip = tvTip;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogDaliDetectTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDaliDetectTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogDaliDetectTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_dali_detect_tip, root, attachToRoot, component);
    }

    public static DialogDaliDetectTipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDaliDetectTipBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogDaliDetectTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_dali_detect_tip, null, false, component);
    }

    public static DialogDaliDetectTipBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDaliDetectTipBinding bind(View view, Object component) {
        return (DialogDaliDetectTipBinding) bind(component, view, R.layout.dialog_dali_detect_tip);
    }
}