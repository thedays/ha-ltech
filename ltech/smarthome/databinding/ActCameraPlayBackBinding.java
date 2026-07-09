package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.videogo.widget.HandleView;

/* loaded from: classes3.dex */
public abstract class ActCameraPlayBackBinding extends ViewDataBinding {
    public final Guideline guideline3;
    public final Guideline guideline4;
    public final HandleView handleView;
    public final HandleView handleViewFull;
    public final ImageView ivFull;
    public final ImageView ivFullRecord;
    public final ImageView ivFullShot;
    public final ImageView ivFullSound;
    public final ImageView ivFullSpeak;
    public final ImageView ivRecord;
    public final ImageView ivShot;
    public final ImageView ivSmall;
    public final ImageView ivSound;
    public final ImageView ivSpeak;
    public final ImageView ivVideo;
    public final ConstraintLayout layoutController;
    public final ConstraintLayout layoutController2;
    public final ConstraintLayout layoutController3;
    public final LinearLayout linearLayout2;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final SurfaceView surfaceView;
    public final TextView tvFullRecordTime;
    public final TextView tvLoading;
    public final TextView tvQuality;
    public final TextView tvRecordTime;
    public final TextView tvSpeed;
    public final TextView tvTalkTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ActCameraPlayBackBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline3, Guideline guideline4, HandleView handleView, HandleView handleViewFull, ImageView ivFull, ImageView ivFullRecord, ImageView ivFullShot, ImageView ivFullSound, ImageView ivFullSpeak, ImageView ivRecord, ImageView ivShot, ImageView ivSmall, ImageView ivSound, ImageView ivSpeak, ImageView ivVideo, ConstraintLayout layoutController, ConstraintLayout layoutController2, ConstraintLayout layoutController3, LinearLayout linearLayout2, SurfaceView surfaceView, TextView tvFullRecordTime, TextView tvLoading, TextView tvQuality, TextView tvRecordTime, TextView tvSpeed, TextView tvTalkTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline3 = guideline3;
        this.guideline4 = guideline4;
        this.handleView = handleView;
        this.handleViewFull = handleViewFull;
        this.ivFull = ivFull;
        this.ivFullRecord = ivFullRecord;
        this.ivFullShot = ivFullShot;
        this.ivFullSound = ivFullSound;
        this.ivFullSpeak = ivFullSpeak;
        this.ivRecord = ivRecord;
        this.ivShot = ivShot;
        this.ivSmall = ivSmall;
        this.ivSound = ivSound;
        this.ivSpeak = ivSpeak;
        this.ivVideo = ivVideo;
        this.layoutController = layoutController;
        this.layoutController2 = layoutController2;
        this.layoutController3 = layoutController3;
        this.linearLayout2 = linearLayout2;
        this.surfaceView = surfaceView;
        this.tvFullRecordTime = tvFullRecordTime;
        this.tvLoading = tvLoading;
        this.tvQuality = tvQuality;
        this.tvRecordTime = tvRecordTime;
        this.tvSpeed = tvSpeed;
        this.tvTalkTip = tvTalkTip;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActCameraPlayBackBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCameraPlayBackBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCameraPlayBackBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_camera_play_back, root, attachToRoot, component);
    }

    public static ActCameraPlayBackBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCameraPlayBackBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCameraPlayBackBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_camera_play_back, null, false, component);
    }

    public static ActCameraPlayBackBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCameraPlayBackBinding bind(View view, Object component) {
        return (ActCameraPlayBackBinding) bind(component, view, R.layout.act_camera_play_back);
    }
}