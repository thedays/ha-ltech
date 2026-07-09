package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ltech.smarthome.ui.device.musicplayer.FtSongsVM;

/* loaded from: classes3.dex */
public abstract class FtSongsBinding extends ViewDataBinding {
    public final AppCompatImageView ivLast;
    public final AppCompatImageView ivMode;
    public final AppCompatImageView ivMute;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivPlay;
    public final ConstraintLayout layoutControl;
    public final LinearLayoutCompat layoutPlay;

    @Bindable
    protected FtSongsVM mViewmodel;
    public final RecyclerView recyclerView;
    public final AppCompatSeekBar sb;
    public final AppCompatTextView tvPlayAll;
    public final AppCompatTextView tvSongName;
    public final AppCompatTextView tvSync;
    public final View vTitleBg;

    public abstract void setViewmodel(FtSongsVM viewmodel);

    protected FtSongsBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivLast, AppCompatImageView ivMode, AppCompatImageView ivMute, AppCompatImageView ivNext, AppCompatImageView ivPlay, ConstraintLayout layoutControl, LinearLayoutCompat layoutPlay, RecyclerView recyclerView, AppCompatSeekBar sb, AppCompatTextView tvPlayAll, AppCompatTextView tvSongName, AppCompatTextView tvSync, View vTitleBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivLast = ivLast;
        this.ivMode = ivMode;
        this.ivMute = ivMute;
        this.ivNext = ivNext;
        this.ivPlay = ivPlay;
        this.layoutControl = layoutControl;
        this.layoutPlay = layoutPlay;
        this.recyclerView = recyclerView;
        this.sb = sb;
        this.tvPlayAll = tvPlayAll;
        this.tvSongName = tvSongName;
        this.tvSync = tvSync;
        this.vTitleBg = vTitleBg;
    }

    public FtSongsVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtSongsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSongsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSongsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_songs, root, attachToRoot, component);
    }

    public static FtSongsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSongsBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSongsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_songs, null, false, component);
    }

    public static FtSongsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSongsBinding bind(View view, Object component) {
        return (FtSongsBinding) bind(component, view, R.layout.ft_songs);
    }
}