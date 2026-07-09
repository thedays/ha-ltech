package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSongPlaylistBinding extends ViewDataBinding {
    public final AppCompatImageView ivPlayAnim;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvSongAuthor;
    public final AppCompatTextView tvSongName;

    protected ItemSongPlaylistBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivPlayAnim, ConstraintLayout layoutItemBg, AppCompatTextView tvSongAuthor, AppCompatTextView tvSongName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivPlayAnim = ivPlayAnim;
        this.layoutItemBg = layoutItemBg;
        this.tvSongAuthor = tvSongAuthor;
        this.tvSongName = tvSongName;
    }

    public static ItemSongPlaylistBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSongPlaylistBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSongPlaylistBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_song_playlist, root, attachToRoot, component);
    }

    public static ItemSongPlaylistBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSongPlaylistBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSongPlaylistBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_song_playlist, null, false, component);
    }

    public static ItemSongPlaylistBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSongPlaylistBinding bind(View view, Object component) {
        return (ItemSongPlaylistBinding) bind(component, view, R.layout.item_song_playlist);
    }
}