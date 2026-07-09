package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemMusicSearchBinding extends ViewDataBinding {
    public final ConstraintLayout layoutNormal;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvSub;

    protected ItemMusicSearchBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutNormal, AppCompatTextView tvMain, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutNormal = layoutNormal;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
    }

    public static ItemMusicSearchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicSearchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMusicSearchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_music_search, root, attachToRoot, component);
    }

    public static ItemMusicSearchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicSearchBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMusicSearchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_music_search, null, false, component);
    }

    public static ItemMusicSearchBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicSearchBinding bind(View view, Object component) {
        return (ItemMusicSearchBinding) bind(component, view, R.layout.item_music_search);
    }
}