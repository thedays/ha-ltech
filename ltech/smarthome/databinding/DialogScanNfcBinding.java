package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogScanNfcBinding extends ViewDataBinding {
    public final AppCompatImageView ivPhoneNfc;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvHint;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogScanNfcBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivPhoneNfc, ConstraintLayout layoutBg, AppCompatTextView tvCancel, AppCompatTextView tvHint, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivPhoneNfc = ivPhoneNfc;
        this.layoutBg = layoutBg;
        this.tvCancel = tvCancel;
        this.tvHint = tvHint;
        this.tvTitle = tvTitle;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogScanNfcBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogScanNfcBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogScanNfcBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_scan_nfc, root, attachToRoot, component);
    }

    public static DialogScanNfcBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogScanNfcBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogScanNfcBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_scan_nfc, null, false, component);
    }

    public static DialogScanNfcBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogScanNfcBinding bind(View view, Object component) {
        return (DialogScanNfcBinding) bind(component, view, R.layout.dialog_scan_nfc);
    }
}