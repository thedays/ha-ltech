package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearchVM;

/* loaded from: classes3.dex */
public abstract class ActSearchMusicBinding extends ViewDataBinding {
    public final LinearLayout layoutHistory;
    public final LinearLayout layoutLoad;
    public final LinearLayout layoutPlayAll;
    public final FrameLayout layoutSearch;

    @Bindable
    protected ActDcaMusicSearchVM mViewmodel;
    public final RecyclerView rvHistory;
    public final RecyclerView rvResult;
    public final ItemSearchBarBinding searchBar;
    public final ImageView searchDel;
    public final AppCompatTextView tvFindTips;
    public final AppCompatTextView tvMusicNumber;

    public abstract void setViewmodel(ActDcaMusicSearchVM viewmodel);

    protected ActSearchMusicBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutHistory, LinearLayout layoutLoad, LinearLayout layoutPlayAll, FrameLayout layoutSearch, RecyclerView rvHistory, RecyclerView rvResult, ItemSearchBarBinding searchBar, ImageView searchDel, AppCompatTextView tvFindTips, AppCompatTextView tvMusicNumber) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutHistory = layoutHistory;
        this.layoutLoad = layoutLoad;
        this.layoutPlayAll = layoutPlayAll;
        this.layoutSearch = layoutSearch;
        this.rvHistory = rvHistory;
        this.rvResult = rvResult;
        this.searchBar = searchBar;
        this.searchDel = searchDel;
        this.tvFindTips = tvFindTips;
        this.tvMusicNumber = tvMusicNumber;
    }

    public ActDcaMusicSearchVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSearchMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSearchMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_music, root, attachToRoot, component);
    }

    public static ActSearchMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchMusicBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSearchMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_music, null, false, component);
    }

    public static ActSearchMusicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchMusicBinding bind(View view, Object component) {
        return (ActSearchMusicBinding) bind(component, view, R.layout.act_search_music);
    }
}