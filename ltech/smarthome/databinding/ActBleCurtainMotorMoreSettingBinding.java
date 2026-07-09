package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActBleCurtainMotorMoreSettingBinding extends ViewDataBinding {
    public final AppCompatButton btnAdjust;
    public final AppCompatButton btnMotorDirection;
    public final AppCompatImageView ivAdjustGo;
    public final AppCompatImageView ivMotorDirection;
    public final AppCompatImageView ivMotorDirectionNameGo;
    public final AppCompatImageView ivMotorSpeedGo;
    public final AppCompatImageView ivTvWhenToStopGo;
    public final ConstraintLayout layoutAdjust;
    public final RelativeLayout layoutMemorizeCurtainPosition;
    public final ConstraintLayout layoutMotorDirection;
    public final RelativeLayout layoutMotorOpenType;
    public final RelativeLayout layoutMotorSpeed;
    public final ConstraintLayout layoutRemoteControl;
    public final RelativeLayout layoutSetLimitPosition;
    public final RelativeLayout layoutSetManuallyPull;
    public final RelativeLayout layoutSetSoftStart;
    public final RelativeLayout layoutSetSyncManualOperation;
    public final ConstraintLayout layoutWhenToStop;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActBleCurtainMotorMoreSettingVM mViewmodel;
    public final SwitchButton sbManuallyPull;
    public final SwitchButton sbMemorizeCurtainPosition;
    public final SwitchButton sbSetLimitPosition;
    public final SwitchButton sbSoftStart;
    public final SwitchButton sbSyncManualOperation;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAdjustTip;
    public final AppCompatTextView tvMemorizeCurtainPositionTip;
    public final AppCompatTextView tvMotorDirectionTip;
    public final AppCompatTextView tvMotorSpeed;
    public final AppCompatTextView tvMotorSpeedTip;
    public final AppCompatTextView tvOpenType;
    public final AppCompatTextView tvSetCurtainMotorTip;
    public final AppCompatTextView tvSetLimitPositionTip;
    public final AppCompatTextView tvSetManuallyPullTip;
    public final AppCompatTextView tvSetSoftStartTip;
    public final AppCompatTextView tvSetSyncManualOperationTip;
    public final AppCompatTextView tvWhenToStop;
    public final AppCompatTextView tvWhenToStopTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActBleCurtainMotorMoreSettingVM viewmodel);

    protected ActBleCurtainMotorMoreSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btnAdjust, AppCompatButton btnMotorDirection, AppCompatImageView ivAdjustGo, AppCompatImageView ivMotorDirection, AppCompatImageView ivMotorDirectionNameGo, AppCompatImageView ivMotorSpeedGo, AppCompatImageView ivTvWhenToStopGo, ConstraintLayout layoutAdjust, RelativeLayout layoutMemorizeCurtainPosition, ConstraintLayout layoutMotorDirection, RelativeLayout layoutMotorOpenType, RelativeLayout layoutMotorSpeed, ConstraintLayout layoutRemoteControl, RelativeLayout layoutSetLimitPosition, RelativeLayout layoutSetManuallyPull, RelativeLayout layoutSetSoftStart, RelativeLayout layoutSetSyncManualOperation, ConstraintLayout layoutWhenToStop, SwitchButton sbManuallyPull, SwitchButton sbMemorizeCurtainPosition, SwitchButton sbSetLimitPosition, SwitchButton sbSoftStart, SwitchButton sbSyncManualOperation, LayoutTitleDefaultBinding title, AppCompatTextView tvAdjustTip, AppCompatTextView tvMemorizeCurtainPositionTip, AppCompatTextView tvMotorDirectionTip, AppCompatTextView tvMotorSpeed, AppCompatTextView tvMotorSpeedTip, AppCompatTextView tvOpenType, AppCompatTextView tvSetCurtainMotorTip, AppCompatTextView tvSetLimitPositionTip, AppCompatTextView tvSetManuallyPullTip, AppCompatTextView tvSetSoftStartTip, AppCompatTextView tvSetSyncManualOperationTip, AppCompatTextView tvWhenToStop, AppCompatTextView tvWhenToStopTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnAdjust = btnAdjust;
        this.btnMotorDirection = btnMotorDirection;
        this.ivAdjustGo = ivAdjustGo;
        this.ivMotorDirection = ivMotorDirection;
        this.ivMotorDirectionNameGo = ivMotorDirectionNameGo;
        this.ivMotorSpeedGo = ivMotorSpeedGo;
        this.ivTvWhenToStopGo = ivTvWhenToStopGo;
        this.layoutAdjust = layoutAdjust;
        this.layoutMemorizeCurtainPosition = layoutMemorizeCurtainPosition;
        this.layoutMotorDirection = layoutMotorDirection;
        this.layoutMotorOpenType = layoutMotorOpenType;
        this.layoutMotorSpeed = layoutMotorSpeed;
        this.layoutRemoteControl = layoutRemoteControl;
        this.layoutSetLimitPosition = layoutSetLimitPosition;
        this.layoutSetManuallyPull = layoutSetManuallyPull;
        this.layoutSetSoftStart = layoutSetSoftStart;
        this.layoutSetSyncManualOperation = layoutSetSyncManualOperation;
        this.layoutWhenToStop = layoutWhenToStop;
        this.sbManuallyPull = sbManuallyPull;
        this.sbMemorizeCurtainPosition = sbMemorizeCurtainPosition;
        this.sbSetLimitPosition = sbSetLimitPosition;
        this.sbSoftStart = sbSoftStart;
        this.sbSyncManualOperation = sbSyncManualOperation;
        this.title = title;
        this.tvAdjustTip = tvAdjustTip;
        this.tvMemorizeCurtainPositionTip = tvMemorizeCurtainPositionTip;
        this.tvMotorDirectionTip = tvMotorDirectionTip;
        this.tvMotorSpeed = tvMotorSpeed;
        this.tvMotorSpeedTip = tvMotorSpeedTip;
        this.tvOpenType = tvOpenType;
        this.tvSetCurtainMotorTip = tvSetCurtainMotorTip;
        this.tvSetLimitPositionTip = tvSetLimitPositionTip;
        this.tvSetManuallyPullTip = tvSetManuallyPullTip;
        this.tvSetSoftStartTip = tvSetSoftStartTip;
        this.tvSetSyncManualOperationTip = tvSetSyncManualOperationTip;
        this.tvWhenToStop = tvWhenToStop;
        this.tvWhenToStopTip = tvWhenToStopTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActBleCurtainMotorMoreSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActBleCurtainMotorMoreSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleCurtainMotorMoreSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBleCurtainMotorMoreSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_curtain_motor_more_setting, root, attachToRoot, component);
    }

    public static ActBleCurtainMotorMoreSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleCurtainMotorMoreSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBleCurtainMotorMoreSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_curtain_motor_more_setting, null, false, component);
    }

    public static ActBleCurtainMotorMoreSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleCurtainMotorMoreSettingBinding bind(View view, Object component) {
        return (ActBleCurtainMotorMoreSettingBinding) bind(component, view, R.layout.act_ble_curtain_motor_more_setting);
    }
}