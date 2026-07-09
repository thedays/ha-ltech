package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogSensingDistanceSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivIco;
    public final AppCompatImageView ivTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RangeSeekBar seekBar;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final TextView tvTip;
    public final AppCompatTextView tvTitle;
    public final TextView tvValue;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSensingDistanceSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIco, AppCompatImageView ivTip, RangeSeekBar seekBar, AppCompatTextView tvCancel, AppCompatTextView tvFinish, TextView tvTip, AppCompatTextView tvTitle, TextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIco = ivIco;
        this.ivTip = ivTip;
        this.seekBar = seekBar;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
        this.tvTip = tvTip;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSensingDistanceSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSensingDistanceSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSensingDistanceSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_sensing_distance_setting, root, attachToRoot, component);
    }

    public static DialogSensingDistanceSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSensingDistanceSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSensingDistanceSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_sensing_distance_setting, null, false, component);
    }

    public static DialogSensingDistanceSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSensingDistanceSettingBinding bind(View view, Object component) {
        return (DialogSensingDistanceSettingBinding) bind(component, view, R.layout.dialog_sensing_distance_setting);
    }
}