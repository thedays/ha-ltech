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
public abstract class ItemPlaylistManagerBinding extends ViewDataBinding {
    public final AppCompatImageView ivDel;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvSongAuthor;
    public final AppCompatTextView tvSongName;

    protected ItemPlaylistManagerBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDel, ConstraintLayout layoutItemBg, AppCompatTextView tvSongAuthor, AppCompatTextView tvSongName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDel = ivDel;
        this.layoutItemBg = layoutItemBg;
        this.tvSongAuthor = tvSongAuthor;
        this.tvSongName = tvSongName;
    }

    public static ItemPlaylistManagerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPlaylistManagerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemPlaylistManagerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_playlist_manager, root, attachToRoot, component);
    }

    public static ItemPlaylistManagerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPlaylistManagerBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemPlaylistManagerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_playlist_manager, null, false, component);
    }

    public static ItemPlaylistManagerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPlaylistManagerBinding bind(View view, Object component) {
        return (ItemPlaylistManagerBinding) bind(component, view, R.layout.item_playlist_manager);
    }
}