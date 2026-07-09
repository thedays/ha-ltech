package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM;
import com.ltech.smarthome.view.CircleMusicView;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActSonosMusicDetailBinding extends ViewDataBinding {
    public final TextView cross;
    public final AppCompatImageView ivCross;
    public final CircleMusicView ivMusicCd;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivPlay;
    public final AppCompatImageView ivPrevious;
    public final AppCompatImageView ivRepeat;
    public final AppCompatImageView ivRepeatBg;
    public final AppCompatImageView ivRepeatOne;
    public final AppCompatImageView ivRepeatOneBg;
    public final AppCompatImageView ivShuffle;
    public final AppCompatImageView ivShuffleBg;
    public final RelativeLayout layoutCrossFade;
    public final ConstraintLayout layoutFunction;
    public final LinearLayout layoutMenu;
    public final ConstraintLayout layoutMusicDetail;
    public final LinearLayout layoutPlay;
    public final LinearLayout layoutVolume;
    public final View line;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSonosPlayControlVM mViewmodel;
    public final SwitchButton sbCrossFade;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvCross;
    public final TextView tvModeFull;
    public final TextView tvModeLabel;
    public final AppCompatTextView tvRepeat;
    public final AppCompatTextView tvRepeatOne;
    public final AppCompatTextView tvShuffle;
    public final AppCompatTextView tvSinger;
    public final AppCompatTextView tvSong;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSonosPlayControlVM viewmodel);

    protected ActSonosMusicDetailBinding(Object _bindingComponent, View _root, int _localFieldCount, TextView cross, AppCompatImageView ivCross, CircleMusicView ivMusicCd, AppCompatImageView ivNext, AppCompatImageView ivPlay, AppCompatImageView ivPrevious, AppCompatImageView ivRepeat, AppCompatImageView ivRepeatBg, AppCompatImageView ivRepeatOne, AppCompatImageView ivRepeatOneBg, AppCompatImageView ivShuffle, AppCompatImageView ivShuffleBg, RelativeLayout layoutCrossFade, ConstraintLayout layoutFunction, LinearLayout layoutMenu, ConstraintLayout layoutMusicDetail, LinearLayout layoutPlay, LinearLayout layoutVolume, View line, SwitchButton sbCrossFade, LayoutTitleDefaultBinding title, TextView tvCross, TextView tvModeFull, TextView tvModeLabel, AppCompatTextView tvRepeat, AppCompatTextView tvRepeatOne, AppCompatTextView tvShuffle, AppCompatTextView tvSinger, AppCompatTextView tvSong) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cross = cross;
        this.ivCross = ivCross;
        this.ivMusicCd = ivMusicCd;
        this.ivNext = ivNext;
        this.ivPlay = ivPlay;
        this.ivPrevious = ivPrevious;
        this.ivRepeat = ivRepeat;
        this.ivRepeatBg = ivRepeatBg;
        this.ivRepeatOne = ivRepeatOne;
        this.ivRepeatOneBg = ivRepeatOneBg;
        this.ivShuffle = ivShuffle;
        this.ivShuffleBg = ivShuffleBg;
        this.layoutCrossFade = layoutCrossFade;
        this.layoutFunction = layoutFunction;
        this.layoutMenu = layoutMenu;
        this.layoutMusicDetail = layoutMusicDetail;
        this.layoutPlay = layoutPlay;
        this.layoutVolume = layoutVolume;
        this.line = line;
        this.sbCrossFade = sbCrossFade;
        this.title = title;
        this.tvCross = tvCross;
        this.tvModeFull = tvModeFull;
        this.tvModeLabel = tvModeLabel;
        this.tvRepeat = tvRepeat;
        this.tvRepeatOne = tvRepeatOne;
        this.tvShuffle = tvShuffle;
        this.tvSinger = tvSinger;
        this.tvSong = tvSong;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSonosPlayControlVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSonosMusicDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSonosMusicDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSonosMusicDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sonos_music_detail, root, attachToRoot, component);
    }

    public static ActSonosMusicDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSonosMusicDetailBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSonosMusicDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sonos_music_detail, null, false, component);
    }

    public static ActSonosMusicDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSonosMusicDetailBinding bind(View view, Object component) {
        return (ActSonosMusicDetailBinding) bind(component, view, R.layout.act_sonos_music_detail);
    }
}