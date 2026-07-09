package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSetVM;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActBleMotorModeSetBinding extends ViewDataBinding {
    public final AppCompatImageView ivKeyNameGo;
    public final AppCompatImageView ivModeIcon;
    public final ConstraintLayout layoutChangeCurtainOpenPercent;
    public final RelativeLayout layoutChangeIcon;
    public final ConstraintLayout layoutKeyName;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActBleMotorModeSetVM mViewmodel;
    public final LightBrtBar sbBrt;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvChangeCurtainOpenPercentTip;
    public final AppCompatTextView tvKeyName;
    public final AppCompatTextView tvNameTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActBleMotorModeSetVM viewmodel);

    protected ActBleMotorModeSetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivKeyNameGo, AppCompatImageView ivModeIcon, ConstraintLayout layoutChangeCurtainOpenPercent, RelativeLayout layoutChangeIcon, ConstraintLayout layoutKeyName, LightBrtBar sbBrt, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, AppCompatTextView tvChangeCurtainOpenPercentTip, AppCompatTextView tvKeyName, AppCompatTextView tvNameTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivKeyNameGo = ivKeyNameGo;
        this.ivModeIcon = ivModeIcon;
        this.layoutChangeCurtainOpenPercent = layoutChangeCurtainOpenPercent;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutKeyName = layoutKeyName;
        this.sbBrt = sbBrt;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvChangeCurtainOpenPercentTip = tvChangeCurtainOpenPercentTip;
        this.tvKeyName = tvKeyName;
        this.tvNameTip = tvNameTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActBleMotorModeSetVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActBleMotorModeSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMotorModeSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBleMotorModeSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_motor_mode_set, root, attachToRoot, component);
    }

    public static ActBleMotorModeSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMotorModeSetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBleMotorModeSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_motor_mode_set, null, false, component);
    }

    public static ActBleMotorModeSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMotorModeSetBinding bind(View view, Object component) {
        return (ActBleMotorModeSetBinding) bind(component, view, R.layout.act_ble_motor_mode_set);
    }
}