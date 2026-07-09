package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHomeVM;
import com.ltech.smarthome.view.CircleMusicView;

/* loaded from: classes3.dex */
public abstract class ActDcaMusicHomeBinding extends ViewDataBinding {
    public final AppCompatImageView ivPlay;
    public final AppCompatImageView ivPlaylist;
    public final ConstraintLayout layoutBottom;
    public final FrameLayout layoutSearch;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDcaMusicHomeVM mViewmodel;
    public final RecyclerView rv;
    public final ItemSearchBarNoEditMusicBinding searchBar;
    public final CircleMusicView songCirclePic;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSinger;
    public final AppCompatTextView tvSongName;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDcaMusicHomeVM viewmodel);

    protected ActDcaMusicHomeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivPlay, AppCompatImageView ivPlaylist, ConstraintLayout layoutBottom, FrameLayout layoutSearch, RecyclerView rv, ItemSearchBarNoEditMusicBinding searchBar, CircleMusicView songCirclePic, LayoutTitleDefaultBinding title, AppCompatTextView tvSinger, AppCompatTextView tvSongName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivPlay = ivPlay;
        this.ivPlaylist = ivPlaylist;
        this.layoutBottom = layoutBottom;
        this.layoutSearch = layoutSearch;
        this.rv = rv;
        this.searchBar = searchBar;
        this.songCirclePic = songCirclePic;
        this.title = title;
        this.tvSinger = tvSinger;
        this.tvSongName = tvSongName;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDcaMusicHomeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDcaMusicHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaMusicHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDcaMusicHomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dca_music_home, root, attachToRoot, component);
    }

    public static ActDcaMusicHomeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaMusicHomeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDcaMusicHomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dca_music_home, null, false, component);
    }

    public static ActDcaMusicHomeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaMusicHomeBinding bind(View view, Object component) {
        return (ActDcaMusicHomeBinding) bind(component, view, R.layout.act_dca_music_home);
    }
}