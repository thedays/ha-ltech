package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public abstract class ActHsdSensorBinding extends ViewDataBinding {
    public final AppCompatTextView btSmart;
    public final ConstraintLayout cardview;
    public final View guideline2;
    public final ImageView ivBattery;
    public final ImageView ivSensitivity;
    public final AppCompatImageView ivSensorClose;
    public final ConstraintLayout layoutBattery;
    public final ConstraintLayout layoutContent;
    public final RelativeLayout layoutSenseSetting;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mOffline;

    @Bindable
    protected TitleDefault mTitle;
    public final SpreadView spreadviewSensor;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBattery;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvEnvironmentLog;
    public final AppCompatTextView tvLux;
    public final AppCompatTextView tvLuxTip;
    public final AppCompatTextView tvSenseRecord;
    public final AppCompatTextView tvSensitivity;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvTime;
    public final RelativeLayout waveView;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setOffline(Boolean offline);

    public abstract void setTitle(TitleDefault title);

    protected ActHsdSensorBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView btSmart, ConstraintLayout cardview, View guideline2, ImageView ivBattery, ImageView ivSensitivity, AppCompatImageView ivSensorClose, ConstraintLayout layoutBattery, ConstraintLayout layoutContent, RelativeLayout layoutSenseSetting, SpreadView spreadviewSensor, LayoutTitleDefaultBinding title, AppCompatTextView tvBattery, AppCompatTextView tvBatteryTip, AppCompatTextView tvEnvironmentLog, AppCompatTextView tvLux, AppCompatTextView tvLuxTip, AppCompatTextView tvSenseRecord, AppCompatTextView tvSensitivity, AppCompatTextView tvState, AppCompatTextView tvTime, RelativeLayout waveView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btSmart = btSmart;
        this.cardview = cardview;
        this.guideline2 = guideline2;
        this.ivBattery = ivBattery;
        this.ivSensitivity = ivSensitivity;
        this.ivSensorClose = ivSensorClose;
        this.layoutBattery = layoutBattery;
        this.layoutContent = layoutContent;
        this.layoutSenseSetting = layoutSenseSetting;
        this.spreadviewSensor = spreadviewSensor;
        this.title = title;
        this.tvBattery = tvBattery;
        this.tvBatteryTip = tvBatteryTip;
        this.tvEnvironmentLog = tvEnvironmentLog;
        this.tvLux = tvLux;
        this.tvLuxTip = tvLuxTip;
        this.tvSenseRecord = tvSenseRecord;
        this.tvSensitivity = tvSensitivity;
        this.tvState = tvState;
        this.tvTime = tvTime;
        this.waveView = waveView;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getOffline() {
        return this.mOffline;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActHsdSensorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHsdSensorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHsdSensorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_hsd_sensor, root, attachToRoot, component);
    }

    public static ActHsdSensorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHsdSensorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHsdSensorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_hsd_sensor, null, false, component);
    }

    public static ActHsdSensorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHsdSensorBinding bind(View view, Object component) {
        return (ActHsdSensorBinding) bind(component, view, R.layout.act_hsd_sensor);
    }
}