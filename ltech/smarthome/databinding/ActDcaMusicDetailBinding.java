package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM;
import com.ltech.smarthome.view.CircleMusicView;
import com.ltech.smarthome.view.HorizontalSeekBar;

/* loaded from: classes3.dex */
public abstract class ActDcaMusicDetailBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivMode;
    public final AppCompatImageView ivMusicCd;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivPlay;
    public final AppCompatImageView ivPlaylist;
    public final AppCompatImageView ivPrevious;
    public final AppCompatImageView ivTime;
    public final AppCompatImageView ivVolume;
    public final LinearLayout layoutFunction;
    public final ConstraintLayout layoutMusicDetail;
    public final LinearLayout layoutPlay;
    public final LinearLayout layoutProgress;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDcaMusicDetailVM mViewmodel;
    public final HorizontalSeekBar sbMusic;
    public final CircleMusicView songCirclePic;
    public final Toolbar title;
    public final AppCompatTextView tvSinger;
    public final AppCompatTextView tvTimeEnd;
    public final AppCompatTextView tvTimeStart;
    public final AppCompatTextView tvTips;
    public final AppCompatTextView tvTitle;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDcaMusicDetailVM viewmodel);

    protected ActDcaMusicDetailBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, AppCompatImageView ivEdit, AppCompatImageView ivMode, AppCompatImageView ivMusicCd, AppCompatImageView ivNext, AppCompatImageView ivPlay, AppCompatImageView ivPlaylist, AppCompatImageView ivPrevious, AppCompatImageView ivTime, AppCompatImageView ivVolume, LinearLayout layoutFunction, ConstraintLayout layoutMusicDetail, LinearLayout layoutPlay, LinearLayout layoutProgress, HorizontalSeekBar sbMusic, CircleMusicView songCirclePic, Toolbar title, AppCompatTextView tvSinger, AppCompatTextView tvTimeEnd, AppCompatTextView tvTimeStart, AppCompatTextView tvTips, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.ivEdit = ivEdit;
        this.ivMode = ivMode;
        this.ivMusicCd = ivMusicCd;
        this.ivNext = ivNext;
        this.ivPlay = ivPlay;
        this.ivPlaylist = ivPlaylist;
        this.ivPrevious = ivPrevious;
        this.ivTime = ivTime;
        this.ivVolume = ivVolume;
        this.layoutFunction = layoutFunction;
        this.layoutMusicDetail = layoutMusicDetail;
        this.layoutPlay = layoutPlay;
        this.layoutProgress = layoutProgress;
        this.sbMusic = sbMusic;
        this.songCirclePic = songCirclePic;
        this.title = title;
        this.tvSinger = tvSinger;
        this.tvTimeEnd = tvTimeEnd;
        this.tvTimeStart = tvTimeStart;
        this.tvTips = tvTips;
        this.tvTitle = tvTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDcaMusicDetailVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDcaMusicDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaMusicDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDcaMusicDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dca_music_detail, root, attachToRoot, component);
    }

    public static ActDcaMusicDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaMusicDetailBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDcaMusicDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dca_music_detail, null, false, component);
    }

    public static ActDcaMusicDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaMusicDetailBinding bind(View view, Object component) {
        return (ActDcaMusicDetailBinding) bind(component, view, R.layout.act_dca_music_detail);
    }
}