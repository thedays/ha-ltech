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
import com.ltech.smarthome.view.WaveView;

/* loaded from: classes3.dex */
public abstract class ItemMusicListBinding extends ViewDataBinding {
    public final WaveView icPlaying;
    public final AppCompatImageView ivFavorite;
    public final ConstraintLayout layoutNormal;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvNumber;
    public final AppCompatTextView tvSub;

    protected ItemMusicListBinding(Object _bindingComponent, View _root, int _localFieldCount, WaveView icPlaying, AppCompatImageView ivFavorite, ConstraintLayout layoutNormal, AppCompatTextView tvMain, AppCompatTextView tvNumber, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.icPlaying = icPlaying;
        this.ivFavorite = ivFavorite;
        this.layoutNormal = layoutNormal;
        this.tvMain = tvMain;
        this.tvNumber = tvNumber;
        this.tvSub = tvSub;
    }

    public static ItemMusicListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMusicListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_music_list, root, attachToRoot, component);
    }

    public static ItemMusicListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicListBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMusicListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_music_list, null, false, component);
    }

    public static ItemMusicListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicListBinding bind(View view, Object component) {
        return (ItemMusicListBinding) bind(component, view, R.layout.item_music_list);
    }
}