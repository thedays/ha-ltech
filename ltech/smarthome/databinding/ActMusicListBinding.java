package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActMusicListBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView10;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvMusic;
    public final AppCompatTextView tvTotal;
    public final View vPlayAll;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActMusicListBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView10, ConstraintLayout layoutLoad, RecyclerView rvMusic, AppCompatTextView tvTotal, View vPlayAll) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView10 = appCompatImageView10;
        this.layoutLoad = layoutLoad;
        this.rvMusic = rvMusic;
        this.tvTotal = tvTotal;
        this.vPlayAll = vPlayAll;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActMusicListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMusicListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMusicListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_music_list, root, attachToRoot, component);
    }

    public static ActMusicListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMusicListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMusicListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_music_list, null, false, component);
    }

    public static ActMusicListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMusicListBinding bind(View view, Object component) {
        return (ActMusicListBinding) bind(component, view, R.layout.act_music_list);
    }
}