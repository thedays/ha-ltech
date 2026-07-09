package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActEngineeringModeOnOffBinding extends ViewDataBinding {
    public final RelativeLayout layoutLightOff;
    public final RelativeLayout layoutLightOn;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvLightOnTip;
    public final AppCompatTextView tvLightOnTipContent;
    public final AppCompatTextView tvOffTime;
    public final AppCompatTextView tvOffTip;
    public final AppCompatTextView tvOffTipContent;
    public final AppCompatTextView tvOnTime;
    public final AppCompatTextView tvTips;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActEngineeringModeOnOffBinding(Object _bindingComponent, View _root, int _localFieldCount, RelativeLayout layoutLightOff, RelativeLayout layoutLightOn, ConstraintLayout layoutLoad, AppCompatTextView tvLightOnTip, AppCompatTextView tvLightOnTipContent, AppCompatTextView tvOffTime, AppCompatTextView tvOffTip, AppCompatTextView tvOffTipContent, AppCompatTextView tvOnTime, AppCompatTextView tvTips) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLightOff = layoutLightOff;
        this.layoutLightOn = layoutLightOn;
        this.layoutLoad = layoutLoad;
        this.tvLightOnTip = tvLightOnTip;
        this.tvLightOnTipContent = tvLightOnTipContent;
        this.tvOffTime = tvOffTime;
        this.tvOffTip = tvOffTip;
        this.tvOffTipContent = tvOffTipContent;
        this.tvOnTime = tvOnTime;
        this.tvTips = tvTips;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActEngineeringModeOnOffBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEngineeringModeOnOffBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEngineeringModeOnOffBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_engineering_mode_on_off, root, attachToRoot, component);
    }

    public static ActEngineeringModeOnOffBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEngineeringModeOnOffBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEngineeringModeOnOffBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_engineering_mode_on_off, null, false, component);
    }

    public static ActEngineeringModeOnOffBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEngineeringModeOnOffBinding bind(View view, Object component) {
        return (ActEngineeringModeOnOffBinding) bind(component, view, R.layout.act_engineering_mode_on_off);
    }
}