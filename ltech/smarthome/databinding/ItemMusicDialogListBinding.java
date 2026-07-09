package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.WaveView;

/* loaded from: classes3.dex */
public abstract class ItemMusicDialogListBinding extends ViewDataBinding {
    public final WaveView icPlaying;
    public final AppCompatImageView ivFavorite;
    public final LinearLayout layoutIcon;
    public final LinearLayout layoutMain;
    public final ConstraintLayout layoutNormal;
    public final LinearLayout layoutSortIcon;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvNumber;
    public final AppCompatTextView tvSub;

    protected ItemMusicDialogListBinding(Object _bindingComponent, View _root, int _localFieldCount, WaveView icPlaying, AppCompatImageView ivFavorite, LinearLayout layoutIcon, LinearLayout layoutMain, ConstraintLayout layoutNormal, LinearLayout layoutSortIcon, AppCompatTextView tvMain, AppCompatTextView tvNumber, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.icPlaying = icPlaying;
        this.ivFavorite = ivFavorite;
        this.layoutIcon = layoutIcon;
        this.layoutMain = layoutMain;
        this.layoutNormal = layoutNormal;
        this.layoutSortIcon = layoutSortIcon;
        this.tvMain = tvMain;
        this.tvNumber = tvNumber;
        this.tvSub = tvSub;
    }

    public static ItemMusicDialogListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicDialogListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMusicDialogListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_music_dialog_list, root, attachToRoot, component);
    }

    public static ItemMusicDialogListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicDialogListBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMusicDialogListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_music_dialog_list, null, false, component);
    }

    public static ItemMusicDialogListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicDialogListBinding bind(View view, Object component) {
        return (ItemMusicDialogListBinding) bind(component, view, R.layout.item_music_dialog_list);
    }
}