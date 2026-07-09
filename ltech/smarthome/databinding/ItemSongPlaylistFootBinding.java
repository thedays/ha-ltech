package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSongPlaylistFootBinding extends ViewDataBinding {
    public final ConstraintLayout layoutItemBg;

    protected ItemSongPlaylistFootBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutItemBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutItemBg = layoutItemBg;
    }

    public static ItemSongPlaylistFootBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSongPlaylistFootBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSongPlaylistFootBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_song_playlist_foot, root, attachToRoot, component);
    }

    public static ItemSongPlaylistFootBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSongPlaylistFootBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSongPlaylistFootBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_song_playlist_foot, null, false, component);
    }

    public static ItemSongPlaylistFootBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSongPlaylistFootBinding bind(View view, Object component) {
        return (ItemSongPlaylistFootBinding) bind(component, view, R.layout.item_song_playlist_foot);
    }
}