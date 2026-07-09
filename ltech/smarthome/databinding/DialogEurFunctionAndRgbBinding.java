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
import com.ltech.smarthome.view.SwitchSeekBarView;

/* loaded from: classes3.dex */
public abstract class DialogEurFunctionAndRgbBinding extends ViewDataBinding {
    public final Group groupSecond;
    public final View line;
    public final View line2;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final SwitchButton sbFunction;
    public final LightBrtBar seekbar;
    public final AppCompatSeekBar seekbarSecond;
    public final SwitchSeekBarView switchSeekbarB;
    public final SwitchSeekBarView switchSeekbarG;
    public final SwitchSeekBarView switchSeekbarR;
    public final SwitchSeekBarView switchSeekbarRgbBrt;
    public final AppCompatTextView tvFirst;
    public final AppCompatTextView tvSecond;
    public final AppCompatTextView tvSecondTitle;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;
    public final AppCompatTextView tvValueSecond;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogEurFunctionAndRgbBinding(Object _bindingComponent, View _root, int _localFieldCount, Group groupSecond, View line, View line2, SwitchButton sbFunction, LightBrtBar seekbar, AppCompatSeekBar seekbarSecond, SwitchSeekBarView switchSeekbarB, SwitchSeekBarView switchSeekbarG, SwitchSeekBarView switchSeekbarR, SwitchSeekBarView switchSeekbarRgbBrt, AppCompatTextView tvFirst, AppCompatTextView tvSecond, AppCompatTextView tvSecondTitle, AppCompatTextView tvTitle, AppCompatTextView tvValue, AppCompatTextView tvValueSecond) {
        super(_bindingComponent, _root, _localFieldCount);
        this.groupSecond = groupSecond;
        this.line = line;
        this.line2 = line2;
        this.sbFunction = sbFunction;
        this.seekbar = seekbar;
        this.seekbarSecond = seekbarSecond;
        this.switchSeekbarB = switchSeekbarB;
        this.switchSeekbarG = switchSeekbarG;
        this.switchSeekbarR = switchSeekbarR;
        this.switchSeekbarRgbBrt = switchSeekbarRgbBrt;
        this.tvFirst = tvFirst;
        this.tvSecond = tvSecond;
        this.tvSecondTitle = tvSecondTitle;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
        this.tvValueSecond = tvValueSecond;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogEurFunctionAndRgbBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEurFunctionAndRgbBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogEurFunctionAndRgbBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_eur_function_and_rgb, root, attachToRoot, component);
    }

    public static DialogEurFunctionAndRgbBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEurFunctionAndRgbBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogEurFunctionAndRgbBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_eur_function_and_rgb, null, false, component);
    }

    public static DialogEurFunctionAndRgbBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEurFunctionAndRgbBinding bind(View view, Object component) {
        return (DialogEurFunctionAndRgbBinding) bind(component, view, R.layout.dialog_eur_function_and_rgb);
    }
}