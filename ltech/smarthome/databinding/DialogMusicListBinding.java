package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogMusicListBinding extends ViewDataBinding {
    public final AppCompatImageView ivMode;
    public final LinearLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvMusic;
    public final AppCompatTextView tvMode;
    public final AppCompatTextView tvNumber;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogMusicListBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMode, LinearLayout layoutBg, RecyclerView rvMusic, AppCompatTextView tvMode, AppCompatTextView tvNumber) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMode = ivMode;
        this.layoutBg = layoutBg;
        this.rvMusic = rvMusic;
        this.tvMode = tvMode;
        this.tvNumber = tvNumber;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogMusicListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMusicListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogMusicListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_music_list, root, attachToRoot, component);
    }

    public static DialogMusicListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMusicListBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogMusicListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_music_list, null, false, component);
    }

    public static DialogMusicListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMusicListBinding bind(View view, Object component) {
        return (DialogMusicListBinding) bind(component, view, R.layout.dialog_music_list);
    }
}