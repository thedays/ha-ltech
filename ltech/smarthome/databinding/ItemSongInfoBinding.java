package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.VoisePlayingIcon;

/* loaded from: classes3.dex */
public abstract class ItemSongInfoBinding extends ViewDataBinding {
    public final VoisePlayingIcon ivPlayAnim;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvSongAuthor;
    public final AppCompatTextView tvSongName;

    protected ItemSongInfoBinding(Object _bindingComponent, View _root, int _localFieldCount, VoisePlayingIcon ivPlayAnim, ConstraintLayout layoutItemBg, AppCompatTextView tvSongAuthor, AppCompatTextView tvSongName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivPlayAnim = ivPlayAnim;
        this.layoutItemBg = layoutItemBg;
        this.tvSongAuthor = tvSongAuthor;
        this.tvSongName = tvSongName;
    }

    public static ItemSongInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSongInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSongInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_song_info, root, attachToRoot, component);
    }

    public static ItemSongInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSongInfoBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSongInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_song_info, null, false, component);
    }

    public static ItemSongInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSongInfoBinding bind(View view, Object component) {
        return (ItemSongInfoBinding) bind(component, view, R.layout.item_song_info);
    }
}