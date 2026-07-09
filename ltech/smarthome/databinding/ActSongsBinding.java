package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.musicplayer.FtSongsVM;

/* loaded from: classes3.dex */
public abstract class ActSongsBinding extends ViewDataBinding {
    public final AppCompatImageView ivLast;
    public final AppCompatImageView ivMode;
    public final AppCompatImageView ivMute;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivPlay;
    public final ConstraintLayout layoutControl;
    public final FrameLayout layoutLoad;
    public final LinearLayoutCompat layoutPlay;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected FtSongsVM mViewmodel;
    public final RecyclerView recyclerView;
    public final AppCompatSeekBar sb;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvPlayAll;
    public final AppCompatTextView tvSongName;
    public final View vTitleBg;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(FtSongsVM viewmodel);

    protected ActSongsBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivLast, AppCompatImageView ivMode, AppCompatImageView ivMute, AppCompatImageView ivNext, AppCompatImageView ivPlay, ConstraintLayout layoutControl, FrameLayout layoutLoad, LinearLayoutCompat layoutPlay, RecyclerView recyclerView, AppCompatSeekBar sb, LayoutTitleDefaultBinding title, AppCompatTextView tvPlayAll, AppCompatTextView tvSongName, View vTitleBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivLast = ivLast;
        this.ivMode = ivMode;
        this.ivMute = ivMute;
        this.ivNext = ivNext;
        this.ivPlay = ivPlay;
        this.layoutControl = layoutControl;
        this.layoutLoad = layoutLoad;
        this.layoutPlay = layoutPlay;
        this.recyclerView = recyclerView;
        this.sb = sb;
        this.title = title;
        this.tvPlayAll = tvPlayAll;
        this.tvSongName = tvSongName;
        this.vTitleBg = vTitleBg;
    }

    public FtSongsVM getViewmodel() {
        return this.mViewmodel;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSongsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSongsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSongsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_songs, root, attachToRoot, component);
    }

    public static ActSongsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSongsBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSongsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_songs, null, false, component);
    }

    public static ActSongsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSongsBinding bind(View view, Object component) {
        return (ActSongsBinding) bind(component, view, R.layout.act_songs);
    }
}