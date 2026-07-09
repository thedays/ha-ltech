package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.CountDownProgressBar;

/* loaded from: classes3.dex */
public abstract class FtSensorTestStepBinding extends ViewDataBinding {
    public final CountDownProgressBar barCountdown;
    public final AppCompatTextView btNext;
    public final AppCompatTextView btPrevious;
    public final AppCompatImageView ivSensitivityGo;
    public final AppCompatImageView ivStep;
    public final ImageView ivSteps;
    public final LinearLayout layoutBottom;
    public final ConstraintLayout layoutSetSensitivity;
    public final LinearLayout layoutSuccessTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvFailTip;
    public final AppCompatTextView tvSensitivity;
    public final AppCompatTextView tvSteps;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtSensorTestStepBinding(Object _bindingComponent, View _root, int _localFieldCount, CountDownProgressBar barCountdown, AppCompatTextView btNext, AppCompatTextView btPrevious, AppCompatImageView ivSensitivityGo, AppCompatImageView ivStep, ImageView ivSteps, LinearLayout layoutBottom, ConstraintLayout layoutSetSensitivity, LinearLayout layoutSuccessTip, AppCompatTextView tvFailTip, AppCompatTextView tvSensitivity, AppCompatTextView tvSteps) {
        super(_bindingComponent, _root, _localFieldCount);
        this.barCountdown = barCountdown;
        this.btNext = btNext;
        this.btPrevious = btPrevious;
        this.ivSensitivityGo = ivSensitivityGo;
        this.ivStep = ivStep;
        this.ivSteps = ivSteps;
        this.layoutBottom = layoutBottom;
        this.layoutSetSensitivity = layoutSetSensitivity;
        this.layoutSuccessTip = layoutSuccessTip;
        this.tvFailTip = tvFailTip;
        this.tvSensitivity = tvSensitivity;
        this.tvSteps = tvSteps;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtSensorTestStepBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSensorTestStepBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSensorTestStepBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_sensor_test_step, root, attachToRoot, component);
    }

    public static FtSensorTestStepBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSensorTestStepBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSensorTestStepBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_sensor_test_step, null, false, component);
    }

    public static FtSensorTestStepBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSensorTestStepBinding bind(View view, Object component) {
        return (FtSensorTestStepBinding) bind(component, view, R.layout.ft_sensor_test_step);
    }
}