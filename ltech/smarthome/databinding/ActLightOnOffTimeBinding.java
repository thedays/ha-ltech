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
public abstract class ActLightOnOffTimeBinding extends ViewDataBinding {
    public final RelativeLayout layoutLightOff;
    public final RelativeLayout layoutLightOn;
    public final RelativeLayout layoutLightPowerOn;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvOffTime;
    public final AppCompatTextView tvOffTip;
    public final AppCompatTextView tvOnTime;
    public final AppCompatTextView tvOnTip;
    public final AppCompatTextView tvPowerOnTime;
    public final AppCompatTextView tvPowerOnTip;
    public final AppCompatTextView tvReset;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActLightOnOffTimeBinding(Object _bindingComponent, View _root, int _localFieldCount, RelativeLayout layoutLightOff, RelativeLayout layoutLightOn, RelativeLayout layoutLightPowerOn, ConstraintLayout layoutLoad, AppCompatTextView tvOffTime, AppCompatTextView tvOffTip, AppCompatTextView tvOnTime, AppCompatTextView tvOnTip, AppCompatTextView tvPowerOnTime, AppCompatTextView tvPowerOnTip, AppCompatTextView tvReset) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLightOff = layoutLightOff;
        this.layoutLightOn = layoutLightOn;
        this.layoutLightPowerOn = layoutLightPowerOn;
        this.layoutLoad = layoutLoad;
        this.tvOffTime = tvOffTime;
        this.tvOffTip = tvOffTip;
        this.tvOnTime = tvOnTime;
        this.tvOnTip = tvOnTip;
        this.tvPowerOnTime = tvPowerOnTime;
        this.tvPowerOnTip = tvPowerOnTip;
        this.tvReset = tvReset;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActLightOnOffTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightOnOffTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActLightOnOffTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_on_off_time, root, attachToRoot, component);
    }

    public static ActLightOnOffTimeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightOnOffTimeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActLightOnOffTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_on_off_time, null, false, component);
    }

    public static ActLightOnOffTimeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightOnOffTimeBinding bind(View view, Object component) {
        return (ActLightOnOffTimeBinding) bind(component, view, R.layout.act_light_on_off_time);
    }
}