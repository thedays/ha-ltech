package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.FtMusicVM;
import com.ltech.smarthome.view.HorizontalSeekBar;

/* loaded from: classes3.dex */
public abstract class FtMusicBinding extends ViewDataBinding {
    public final ConstraintLayout constraintlayout;
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivBg;
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivMusic;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivPlay;
    public final AppCompatImageView ivPlayList;
    public final AppCompatImageView ivPlayMode;
    public final AppCompatImageView ivPrevious;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected FtMusicVM mViewmodel;
    public final HorizontalSeekBar sbMusic;
    public final Toolbar toolbar;
    public final AppCompatTextView tvMusicArtist;
    public final AppCompatTextView tvMusicCurrent;
    public final AppCompatTextView tvMusicName;
    public final AppCompatTextView tvMusicTotal;
    public final AppCompatTextView tvTitle;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(FtMusicVM viewmodel);

    protected FtMusicBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout constraintlayout, AppCompatImageView ivBack, AppCompatImageView ivBg, AppCompatImageView ivEdit, AppCompatImageView ivMusic, AppCompatImageView ivNext, AppCompatImageView ivPlay, AppCompatImageView ivPlayList, AppCompatImageView ivPlayMode, AppCompatImageView ivPrevious, HorizontalSeekBar sbMusic, Toolbar toolbar, AppCompatTextView tvMusicArtist, AppCompatTextView tvMusicCurrent, AppCompatTextView tvMusicName, AppCompatTextView tvMusicTotal, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.constraintlayout = constraintlayout;
        this.ivBack = ivBack;
        this.ivBg = ivBg;
        this.ivEdit = ivEdit;
        this.ivMusic = ivMusic;
        this.ivNext = ivNext;
        this.ivPlay = ivPlay;
        this.ivPlayList = ivPlayList;
        this.ivPlayMode = ivPlayMode;
        this.ivPrevious = ivPrevious;
        this.sbMusic = sbMusic;
        this.toolbar = toolbar;
        this.tvMusicArtist = tvMusicArtist;
        this.tvMusicCurrent = tvMusicCurrent;
        this.tvMusicName = tvMusicName;
        this.tvMusicTotal = tvMusicTotal;
        this.tvTitle = tvTitle;
    }

    public FtMusicVM getViewmodel() {
        return this.mViewmodel;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static FtMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_music, root, attachToRoot, component);
    }

    public static FtMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMusicBinding inflate(LayoutInflater inflater, Object component) {
        return (FtMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_music, null, false, component);
    }

    public static FtMusicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMusicBinding bind(View view, Object component) {
        return (FtMusicBinding) bind(component, view, R.layout.ft_music);
    }
}