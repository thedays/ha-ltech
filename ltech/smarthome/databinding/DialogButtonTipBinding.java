package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogButtonTipBinding extends ViewDataBinding {
    public final Guideline guideline;
    public final AppCompatImageView iv;
    public final AppCompatImageView iv2;
    public final ConstraintLayout layoutS6;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvFirst;
    public final AppCompatTextView tvSecond;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogButtonTipBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline, AppCompatImageView iv, AppCompatImageView iv2, ConstraintLayout layoutS6, AppCompatTextView tvConfirm, AppCompatTextView tvFirst, AppCompatTextView tvSecond, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline = guideline;
        this.iv = iv;
        this.iv2 = iv2;
        this.layoutS6 = layoutS6;
        this.tvConfirm = tvConfirm;
        this.tvFirst = tvFirst;
        this.tvSecond = tvSecond;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogButtonTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogButtonTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogButtonTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_button_tip, root, attachToRoot, component);
    }

    public static DialogButtonTipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogButtonTipBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogButtonTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_button_tip, null, false, component);
    }

    public static DialogButtonTipBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogButtonTipBinding bind(View view, Object component) {
        return (DialogButtonTipBinding) bind(component, view, R.layout.dialog_button_tip);
    }
}