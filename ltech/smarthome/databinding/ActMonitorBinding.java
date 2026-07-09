package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActMonitorVM;
import com.ltech.smarthome.view.ImageTextView;

/* loaded from: classes3.dex */
public abstract class ActMonitorBinding extends ViewDataBinding {
    public final Guideline guideline;
    public final ImageTextView itvAnswer;
    public final ImageTextView itvHangup;
    public final ImageTextView itvSpeak;
    public final ImageTextView itvUnlock;
    public final ImageTextView itvVoiceMicrophone;
    public final ImageTextView itvVoiceSpeak;
    public final ConstraintLayout layoutFunction;
    public final ConstraintLayout layoutVoice;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActMonitorVM mViewmodel;
    public final RelativeLayout rlRemoteVideoView;
    public final AppCompatTextView tvTime;
    public final AppCompatTextView tvTopName;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActMonitorVM viewmodel);

    protected ActMonitorBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline, ImageTextView itvAnswer, ImageTextView itvHangup, ImageTextView itvSpeak, ImageTextView itvUnlock, ImageTextView itvVoiceMicrophone, ImageTextView itvVoiceSpeak, ConstraintLayout layoutFunction, ConstraintLayout layoutVoice, RelativeLayout rlRemoteVideoView, AppCompatTextView tvTime, AppCompatTextView tvTopName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline = guideline;
        this.itvAnswer = itvAnswer;
        this.itvHangup = itvHangup;
        this.itvSpeak = itvSpeak;
        this.itvUnlock = itvUnlock;
        this.itvVoiceMicrophone = itvVoiceMicrophone;
        this.itvVoiceSpeak = itvVoiceSpeak;
        this.layoutFunction = layoutFunction;
        this.layoutVoice = layoutVoice;
        this.rlRemoteVideoView = rlRemoteVideoView;
        this.tvTime = tvTime;
        this.tvTopName = tvTopName;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActMonitorVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActMonitorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMonitorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMonitorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_monitor, root, attachToRoot, component);
    }

    public static ActMonitorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMonitorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMonitorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_monitor, null, false, component);
    }

    public static ActMonitorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMonitorBinding bind(View view, Object component) {
        return (ActMonitorBinding) bind(component, view, R.layout.act_monitor);
    }
}