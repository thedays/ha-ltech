package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtSongPlaylistBinding extends ViewDataBinding {
    public final RecyclerView recyclerView;

    protected FtSongPlaylistBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView recyclerView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.recyclerView = recyclerView;
    }

    public static FtSongPlaylistBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSongPlaylistBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSongPlaylistBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_song_playlist, root, attachToRoot, component);
    }

    public static FtSongPlaylistBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSongPlaylistBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSongPlaylistBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_song_playlist, null, false, component);
    }

    public static FtSongPlaylistBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSongPlaylistBinding bind(View view, Object component) {
        return (FtSongPlaylistBinding) bind(component, view, R.layout.ft_song_playlist);
    }
}