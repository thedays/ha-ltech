package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorVM;
import com.ltech.smarthome.view.SpreadView;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActWaveSensorProBinding extends ViewDataBinding {
    public final AppCompatTextView btSmart;
    public final ConstraintLayout cardview;
    public final ConstraintLayout cardview2;
    public final Group groupSingle;
    public final View guideline;
    public final View guideline2;
    public final AppCompatImageView ivArrow1;
    public final AppCompatImageView ivArrow2;
    public final AppCompatImageView ivArrow3;
    public final ImageView ivIllumincance;
    public final ImageView ivSensitivity;
    public final AppCompatImageView ivSensorClose;
    public final ConstraintLayout layoutContent;
    public final RelativeLayout layoutGroupDevice;
    public final RelativeLayout layoutIllumilance;
    public final RelativeLayout layoutSensitivity;
    public final LinearLayout layoutSensorParam1;
    public final LinearLayout layoutSensorParam2;
    public final LinearLayout layoutSensorParam3;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mIlluminance;

    @Bindable
    protected String mSensitivity;

    @Bindable
    protected Boolean mStateOn;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActWaveSensorVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvSensorParams1;
    public final RecyclerView rvSensorParams2;
    public final RecyclerView rvSensorParams3;
    public final SwitchButton sbSensor2;
    public final SwitchButton sbSensor3;
    public final SpreadView spreadviewSensor;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvCt;
    public final AppCompatTextView tvCtTip;
    public final AppCompatTextView tvEnvironmentLog;
    public final AppCompatTextView tvIlluminance;
    public final AppCompatTextView tvLux;
    public final AppCompatTextView tvLuxTip;
    public final LinearLayout tvOfflineTip;
    public final AppCompatTextView tvSenseRecord;
    public final AppCompatTextView tvSensitivity;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvTime1;
    public final AppCompatTextView tvTime2;
    public final AppCompatTextView tvTime3;
    public final RelativeLayout waveView;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setIlluminance(String illuminance);

    public abstract void setSensitivity(String sensitivity);

    public abstract void setStateOn(Boolean stateOn);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActWaveSensorVM viewmodel);

    protected ActWaveSensorProBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView btSmart, ConstraintLayout cardview, ConstraintLayout cardview2, Group groupSingle, View guideline, View guideline2, AppCompatImageView ivArrow1, AppCompatImageView ivArrow2, AppCompatImageView ivArrow3, ImageView ivIllumincance, ImageView ivSensitivity, AppCompatImageView ivSensorClose, ConstraintLayout layoutContent, RelativeLayout layoutGroupDevice, RelativeLayout layoutIllumilance, RelativeLayout layoutSensitivity, LinearLayout layoutSensorParam1, LinearLayout layoutSensorParam2, LinearLayout layoutSensorParam3, SmartRefreshLayout refreshLayout, RecyclerView rvSensorParams1, RecyclerView rvSensorParams2, RecyclerView rvSensorParams3, SwitchButton sbSensor2, SwitchButton sbSensor3, SpreadView spreadviewSensor, LayoutTitleDefaultBinding title, AppCompatTextView tvCt, AppCompatTextView tvCtTip, AppCompatTextView tvEnvironmentLog, AppCompatTextView tvIlluminance, AppCompatTextView tvLux, AppCompatTextView tvLuxTip, LinearLayout tvOfflineTip, AppCompatTextView tvSenseRecord, AppCompatTextView tvSensitivity, AppCompatTextView tvState, AppCompatTextView tvTime1, AppCompatTextView tvTime2, AppCompatTextView tvTime3, RelativeLayout waveView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btSmart = btSmart;
        this.cardview = cardview;
        this.cardview2 = cardview2;
        this.groupSingle = groupSingle;
        this.guideline = guideline;
        this.guideline2 = guideline2;
        this.ivArrow1 = ivArrow1;
        this.ivArrow2 = ivArrow2;
        this.ivArrow3 = ivArrow3;
        this.ivIllumincance = ivIllumincance;
        this.ivSensitivity = ivSensitivity;
        this.ivSensorClose = ivSensorClose;
        this.layoutContent = layoutContent;
        this.layoutGroupDevice = layoutGroupDevice;
        this.layoutIllumilance = layoutIllumilance;
        this.layoutSensitivity = layoutSensitivity;
        this.layoutSensorParam1 = layoutSensorParam1;
        this.layoutSensorParam2 = layoutSensorParam2;
        this.layoutSensorParam3 = layoutSensorParam3;
        this.refreshLayout = refreshLayout;
        this.rvSensorParams1 = rvSensorParams1;
        this.rvSensorParams2 = rvSensorParams2;
        this.rvSensorParams3 = rvSensorParams3;
        this.sbSensor2 = sbSensor2;
        this.sbSensor3 = sbSensor3;
        this.spreadviewSensor = spreadviewSensor;
        this.title = title;
        this.tvCt = tvCt;
        this.tvCtTip = tvCtTip;
        this.tvEnvironmentLog = tvEnvironmentLog;
        this.tvIlluminance = tvIlluminance;
        this.tvLux = tvLux;
        this.tvLuxTip = tvLuxTip;
        this.tvOfflineTip = tvOfflineTip;
        this.tvSenseRecord = tvSenseRecord;
        this.tvSensitivity = tvSensitivity;
        this.tvState = tvState;
        this.tvTime1 = tvTime1;
        this.tvTime2 = tvTime2;
        this.tvTime3 = tvTime3;
        this.waveView = waveView;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getStateOn() {
        return this.mStateOn;
    }

    public String getIlluminance() {
        return this.mIlluminance;
    }

    public String getSensitivity() {
        return this.mSensitivity;
    }

    public ActWaveSensorVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActWaveSensorProBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorProBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActWaveSensorProBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_wave_sensor_pro, root, attachToRoot, component);
    }

    public static ActWaveSensorProBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorProBinding inflate(LayoutInflater inflater, Object component) {
        return (ActWaveSensorProBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_wave_sensor_pro, null, false, component);
    }

    public static ActWaveSensorProBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorProBinding bind(View view, Object component) {
        return (ActWaveSensorProBinding) bind(component, view, R.layout.act_wave_sensor_pro);
    }
}