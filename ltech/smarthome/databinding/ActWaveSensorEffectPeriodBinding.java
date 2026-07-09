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
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActWaveSensorEffectPeriodBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView17;
    public final AppCompatImageView appCompatImageView18;
    public final AppCompatTextView appCompatTextView24;
    public final AppCompatTextView appCompatTextView25;
    public final AppCompatTextView date;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mEndTime;

    @Bindable
    protected String mStartTime;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvEndTime;
    public final AppCompatTextView tvEndTimeTip;
    public final AppCompatTextView tvRepeatTime;
    public final AppCompatTextView tvStartTime;
    public final AppCompatTextView tvStartTimeTip;
    public final AppCompatTextView tvTriggerTime;
    public final View vSelectTime;
    public final View vSetTriggerTimes;
    public final View view22;
    public final View view3;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setEndTime(String endTime);

    public abstract void setStartTime(String startTime);

    public abstract void setTitle(TitleDefault title);

    protected ActWaveSensorEffectPeriodBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView17, AppCompatImageView appCompatImageView18, AppCompatTextView appCompatTextView24, AppCompatTextView appCompatTextView25, AppCompatTextView date, LayoutTitleDefaultBinding title, AppCompatTextView tvEndTime, AppCompatTextView tvEndTimeTip, AppCompatTextView tvRepeatTime, AppCompatTextView tvStartTime, AppCompatTextView tvStartTimeTip, AppCompatTextView tvTriggerTime, View vSelectTime, View vSetTriggerTimes, View view22, View view3) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView17 = appCompatImageView17;
        this.appCompatImageView18 = appCompatImageView18;
        this.appCompatTextView24 = appCompatTextView24;
        this.appCompatTextView25 = appCompatTextView25;
        this.date = date;
        this.title = title;
        this.tvEndTime = tvEndTime;
        this.tvEndTimeTip = tvEndTimeTip;
        this.tvRepeatTime = tvRepeatTime;
        this.tvStartTime = tvStartTime;
        this.tvStartTimeTip = tvStartTimeTip;
        this.tvTriggerTime = tvTriggerTime;
        this.vSelectTime = vSelectTime;
        this.vSetTriggerTimes = vSetTriggerTimes;
        this.view22 = view22;
        this.view3 = view3;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getStartTime() {
        return this.mStartTime;
    }

    public String getEndTime() {
        return this.mEndTime;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActWaveSensorEffectPeriodBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorEffectPeriodBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActWaveSensorEffectPeriodBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_wave_sensor_effect_period, root, attachToRoot, component);
    }

    public static ActWaveSensorEffectPeriodBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorEffectPeriodBinding inflate(LayoutInflater inflater, Object component) {
        return (ActWaveSensorEffectPeriodBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_wave_sensor_effect_period, null, false, component);
    }

    public static ActWaveSensorEffectPeriodBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorEffectPeriodBinding bind(View view, Object component) {
        return (ActWaveSensorEffectPeriodBinding) bind(component, view, R.layout.act_wave_sensor_effect_period);
    }
}