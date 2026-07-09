package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class DialogEurFunctionBinding extends ViewDataBinding {
    public final Group groupSecond;
    public final View line;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final SwitchButton sbRgbOn;
    public final LightBrtBar seekbar;
    public final AppCompatSeekBar seekbarSecond;
    public final AppCompatTextView tvFirst;
    public final AppCompatTextView tvSecond;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;
    public final AppCompatTextView tvValueSecond;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogEurFunctionBinding(Object _bindingComponent, View _root, int _localFieldCount, Group groupSecond, View line, SwitchButton sbRgbOn, LightBrtBar seekbar, AppCompatSeekBar seekbarSecond, AppCompatTextView tvFirst, AppCompatTextView tvSecond, AppCompatTextView tvTitle, AppCompatTextView tvValue, AppCompatTextView tvValueSecond) {
        super(_bindingComponent, _root, _localFieldCount);
        this.groupSecond = groupSecond;
        this.line = line;
        this.sbRgbOn = sbRgbOn;
        this.seekbar = seekbar;
        this.seekbarSecond = seekbarSecond;
        this.tvFirst = tvFirst;
        this.tvSecond = tvSecond;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
        this.tvValueSecond = tvValueSecond;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogEurFunctionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEurFunctionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogEurFunctionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_eur_function, root, attachToRoot, component);
    }

    public static DialogEurFunctionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEurFunctionBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogEurFunctionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_eur_function, null, false, component);
    }

    public static DialogEurFunctionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEurFunctionBinding bind(View view, Object component) {
        return (DialogEurFunctionBinding) bind(component, view, R.layout.dialog_eur_function);
    }
}