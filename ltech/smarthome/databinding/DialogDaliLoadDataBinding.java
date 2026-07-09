package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogDaliLoadDataBinding extends ViewDataBinding {
    public final LinearLayout layoutTime;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final ContentLoadingProgressBar progress;
    public final AppCompatTextView tvDetection;
    public final AppCompatTextView tvTime;
    public final AppCompatTextView tvTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogDaliLoadDataBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutTime, ContentLoadingProgressBar progress, AppCompatTextView tvDetection, AppCompatTextView tvTime, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutTime = layoutTime;
        this.progress = progress;
        this.tvDetection = tvDetection;
        this.tvTime = tvTime;
        this.tvTip = tvTip;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogDaliLoadDataBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDaliLoadDataBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogDaliLoadDataBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_dali_load_data, root, attachToRoot, component);
    }

    public static DialogDaliLoadDataBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDaliLoadDataBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogDaliLoadDataBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_dali_load_data, null, false, component);
    }

    public static DialogDaliLoadDataBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDaliLoadDataBinding bind(View view, Object component) {
        return (DialogDaliLoadDataBinding) bind(component, view, R.layout.dialog_dali_load_data);
    }
}