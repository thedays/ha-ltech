package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM;
import com.ltech.smarthome.view.CircleMusicView;
import com.ltech.smarthome.view.StickyScrollView;

/* loaded from: classes3.dex */
public abstract class ActDcaMusicListBinding extends ViewDataBinding {
    public final StickyScrollView actAddDeviceScroll;
    public final ImageView iv;
    public final ImageView ivBgMusicList;
    public final AppCompatImageView ivPlay;
    public final AppCompatImageView ivPlaylist;
    public final ConstraintLayout layoutBottom;
    public final FrameLayout layoutLoad;
    public final LinearLayout layoutPlayAll;
    public final RelativeLayout layoutSort;
    public final View line;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDcaMusicListVM mViewmodel;
    public final TextView musicNumber;
    public final RecyclerView rv;
    public final CircleMusicView songCirclePic;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSinger;
    public final AppCompatTextView tvSongName;
    public final TextView tvWifi;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDcaMusicListVM viewmodel);

    protected ActDcaMusicListBinding(Object _bindingComponent, View _root, int _localFieldCount, StickyScrollView actAddDeviceScroll, ImageView iv, ImageView ivBgMusicList, AppCompatImageView ivPlay, AppCompatImageView ivPlaylist, ConstraintLayout layoutBottom, FrameLayout layoutLoad, LinearLayout layoutPlayAll, RelativeLayout layoutSort, View line, TextView musicNumber, RecyclerView rv, CircleMusicView songCirclePic, LayoutTitleDefaultBinding title, AppCompatTextView tvSinger, AppCompatTextView tvSongName, TextView tvWifi) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actAddDeviceScroll = actAddDeviceScroll;
        this.iv = iv;
        this.ivBgMusicList = ivBgMusicList;
        this.ivPlay = ivPlay;
        this.ivPlaylist = ivPlaylist;
        this.layoutBottom = layoutBottom;
        this.layoutLoad = layoutLoad;
        this.layoutPlayAll = layoutPlayAll;
        this.layoutSort = layoutSort;
        this.line = line;
        this.musicNumber = musicNumber;
        this.rv = rv;
        this.songCirclePic = songCirclePic;
        this.title = title;
        this.tvSinger = tvSinger;
        this.tvSongName = tvSongName;
        this.tvWifi = tvWifi;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDcaMusicListVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDcaMusicListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaMusicListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDcaMusicListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dca_music_list, root, attachToRoot, component);
    }

    public static ActDcaMusicListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaMusicListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDcaMusicListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dca_music_list, null, false, component);
    }

    public static ActDcaMusicListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaMusicListBinding bind(View view, Object component) {
        return (ActDcaMusicListBinding) bind(component, view, R.layout.act_dca_music_list);
    }
}