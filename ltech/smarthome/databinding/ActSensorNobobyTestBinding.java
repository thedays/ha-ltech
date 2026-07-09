package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public abstract class ActSensorNobobyTestBinding extends ViewDataBinding {
    public final AppCompatImageView ivSensorClose;
    public final LinearLayout layoutContent;
    public final FrameLayout layoutProgress;

    @Bindable
    protected Boolean mStateOn;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatSeekBar seekbar;
    public final SpreadView spreadviewSensor;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvTestTip;
    public final AppCompatTextView tvTestTipTitle;
    public final RelativeLayout waveView;

    public abstract void setStateOn(Boolean stateOn);

    public abstract void setTitle(TitleDefault title);

    protected ActSensorNobobyTestBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSensorClose, LinearLayout layoutContent, FrameLayout layoutProgress, AppCompatSeekBar seekbar, SpreadView spreadviewSensor, LayoutTitleDefaultBinding title, AppCompatTextView tvFinish, AppCompatTextView tvState, AppCompatTextView tvTestTip, AppCompatTextView tvTestTipTitle, RelativeLayout waveView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSensorClose = ivSensorClose;
        this.layoutContent = layoutContent;
        this.layoutProgress = layoutProgress;
        this.seekbar = seekbar;
        this.spreadviewSensor = spreadviewSensor;
        this.title = title;
        this.tvFinish = tvFinish;
        this.tvState = tvState;
        this.tvTestTip = tvTestTip;
        this.tvTestTipTitle = tvTestTipTitle;
        this.waveView = waveView;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getStateOn() {
        return this.mStateOn;
    }

    public static ActSensorNobobyTestBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSensorNobobyTestBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSensorNobobyTestBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sensor_noboby_test, root, attachToRoot, component);
    }

    public static ActSensorNobobyTestBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSensorNobobyTestBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSensorNobobyTestBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sensor_noboby_test, null, false, component);
    }

    public static ActSensorNobobyTestBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSensorNobobyTestBinding bind(View view, Object component) {
        return (ActSensorNobobyTestBinding) bind(component, view, R.layout.act_sensor_noboby_test);
    }
}