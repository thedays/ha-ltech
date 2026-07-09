package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSortLocalMusicBinding extends ViewDataBinding {
    public final View bg;
    public final FrameLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvDel;

    public abstract void setTitle(TitleDefault title);

    protected ActSortLocalMusicBinding(Object _bindingComponent, View _root, int _localFieldCount, View bg, FrameLayout layoutLoad, RecyclerView rv, LayoutTitleDefaultBinding title, TextView tvDel) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bg = bg;
        this.layoutLoad = layoutLoad;
        this.rv = rv;
        this.title = title;
        this.tvDel = tvDel;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSortLocalMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSortLocalMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSortLocalMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sort_local_music, root, attachToRoot, component);
    }

    public static ActSortLocalMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSortLocalMusicBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSortLocalMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sort_local_music, null, false, component);
    }

    public static ActSortLocalMusicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSortLocalMusicBinding bind(View view, Object component) {
        return (ActSortLocalMusicBinding) bind(component, view, R.layout.act_sort_local_music);
    }
}