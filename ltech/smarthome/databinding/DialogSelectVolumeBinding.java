package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class DialogSelectVolumeBinding extends ViewDataBinding {
    public final AppCompatImageView iv;
    public final AppCompatImageView iv1;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final LightBrtBar sbBrt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSelectVolumeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView iv, AppCompatImageView iv1, LightBrtBar sbBrt, AppCompatTextView tvBrt, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.iv = iv;
        this.iv1 = iv1;
        this.sbBrt = sbBrt;
        this.tvBrt = tvBrt;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectVolumeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectVolumeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectVolumeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_volume, root, attachToRoot, component);
    }

    public static DialogSelectVolumeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectVolumeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectVolumeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_volume, null, false, component);
    }

    public static DialogSelectVolumeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectVolumeBinding bind(View view, Object component) {
        return (DialogSelectVolumeBinding) bind(component, view, R.layout.dialog_select_volume);
    }
}