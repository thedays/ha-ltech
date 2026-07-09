package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogDaliDetectBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final ContentLoadingProgressBar progress;
    public final AppCompatTextView tvCount;
    public final AppCompatTextView tvTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogDaliDetectBinding(Object _bindingComponent, View _root, int _localFieldCount, ContentLoadingProgressBar progress, AppCompatTextView tvCount, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.progress = progress;
        this.tvCount = tvCount;
        this.tvTip = tvTip;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogDaliDetectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDaliDetectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogDaliDetectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_dali_detect, root, attachToRoot, component);
    }

    public static DialogDaliDetectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDaliDetectBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogDaliDetectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_dali_detect, null, false, component);
    }

    public static DialogDaliDetectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDaliDetectBinding bind(View view, Object component) {
        return (DialogDaliDetectBinding) bind(component, view, R.layout.dialog_dali_detect);
    }
}