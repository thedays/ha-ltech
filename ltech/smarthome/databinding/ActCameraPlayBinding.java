package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SpreadView;
import com.videogo.widget.HandleView;

/* loaded from: classes3.dex */
public abstract class ActCameraPlayBinding extends ViewDataBinding {
    public final TextView beginTimeTv;
    public final TextView endTimeTv;
    public final Guideline guideline3;
    public final HandleView handleViewFull;
    public final AppCompatImageView ivFull;
    public final AppCompatImageView ivFullBack;
    public final AppCompatImageView ivFullPlay;
    public final AppCompatImageView ivFullPlayback;
    public final AppCompatImageView ivFullPtz;
    public final AppCompatImageView ivFullRecord;
    public final AppCompatImageView ivFullShot;
    public final AppCompatImageView ivFullSound;
    public final AppCompatImageView ivFullTalk;
    public final AppCompatImageView ivOffline;
    public final AppCompatImageView ivPlay;
    public final AppCompatImageView ivRecordTime;
    public final ImageView ivSmall;
    public final AppCompatImageView ivSound;
    public final AppCompatImageView ivTalk;
    public final ConstraintLayout layoutController;
    public final ConstraintLayout layoutController3;
    public final LinearLayout layoutTab;
    public final LinearLayout linearLayout2;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mShowTitle;

    @Bindable
    protected TitleDefault mTitle;
    public final LinearLayout progressArea;
    public final SeekBar progressSeekbar;
    public final SpreadView spreadviewTalk;
    public final AppCompatImageView spreadviewTalkStop;
    public final SurfaceView surfaceView;
    public final SurfaceView surfaceView2;
    public final TabLayout tabs;
    public final RelativeLayout talkViewFull;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvFullQuality;
    public final TextView tvLoading;
    public final AppCompatTextView tvQuality;
    public final AppCompatTextView tvRecordTime;
    public final TextView tvSpeed;
    public final AppCompatTextView tvVideo1;
    public final AppCompatTextView tvVideo2;
    public final ViewPager2 viewpager;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setShowTitle(Boolean showTitle);

    public abstract void setTitle(TitleDefault title);

    protected ActCameraPlayBinding(Object _bindingComponent, View _root, int _localFieldCount, TextView beginTimeTv, TextView endTimeTv, Guideline guideline3, HandleView handleViewFull, AppCompatImageView ivFull, AppCompatImageView ivFullBack, AppCompatImageView ivFullPlay, AppCompatImageView ivFullPlayback, AppCompatImageView ivFullPtz, AppCompatImageView ivFullRecord, AppCompatImageView ivFullShot, AppCompatImageView ivFullSound, AppCompatImageView ivFullTalk, AppCompatImageView ivOffline, AppCompatImageView ivPlay, AppCompatImageView ivRecordTime, ImageView ivSmall, AppCompatImageView ivSound, AppCompatImageView ivTalk, ConstraintLayout layoutController, ConstraintLayout layoutController3, LinearLayout layoutTab, LinearLayout linearLayout2, LinearLayout progressArea, SeekBar progressSeekbar, SpreadView spreadviewTalk, AppCompatImageView spreadviewTalkStop, SurfaceView surfaceView, SurfaceView surfaceView2, TabLayout tabs, RelativeLayout talkViewFull, LayoutTitleDefaultBinding title, AppCompatTextView tvFullQuality, TextView tvLoading, AppCompatTextView tvQuality, AppCompatTextView tvRecordTime, TextView tvSpeed, AppCompatTextView tvVideo1, AppCompatTextView tvVideo2, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.beginTimeTv = beginTimeTv;
        this.endTimeTv = endTimeTv;
        this.guideline3 = guideline3;
        this.handleViewFull = handleViewFull;
        this.ivFull = ivFull;
        this.ivFullBack = ivFullBack;
        this.ivFullPlay = ivFullPlay;
        this.ivFullPlayback = ivFullPlayback;
        this.ivFullPtz = ivFullPtz;
        this.ivFullRecord = ivFullRecord;
        this.ivFullShot = ivFullShot;
        this.ivFullSound = ivFullSound;
        this.ivFullTalk = ivFullTalk;
        this.ivOffline = ivOffline;
        this.ivPlay = ivPlay;
        this.ivRecordTime = ivRecordTime;
        this.ivSmall = ivSmall;
        this.ivSound = ivSound;
        this.ivTalk = ivTalk;
        this.layoutController = layoutController;
        this.layoutController3 = layoutController3;
        this.layoutTab = layoutTab;
        this.linearLayout2 = linearLayout2;
        this.progressArea = progressArea;
        this.progressSeekbar = progressSeekbar;
        this.spreadviewTalk = spreadviewTalk;
        this.spreadviewTalkStop = spreadviewTalkStop;
        this.surfaceView = surfaceView;
        this.surfaceView2 = surfaceView2;
        this.tabs = tabs;
        this.talkViewFull = talkViewFull;
        this.title = title;
        this.tvFullQuality = tvFullQuality;
        this.tvLoading = tvLoading;
        this.tvQuality = tvQuality;
        this.tvRecordTime = tvRecordTime;
        this.tvSpeed = tvSpeed;
        this.tvVideo1 = tvVideo1;
        this.tvVideo2 = tvVideo2;
        this.viewpager = viewpager;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getShowTitle() {
        return this.mShowTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActCameraPlayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCameraPlayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCameraPlayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_camera_play, root, attachToRoot, component);
    }

    public static ActCameraPlayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCameraPlayBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCameraPlayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_camera_play, null, false, component);
    }

    public static ActCameraPlayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCameraPlayBinding bind(View view, Object component) {
        return (ActCameraPlayBinding) bind(component, view, R.layout.act_camera_play);
    }
}