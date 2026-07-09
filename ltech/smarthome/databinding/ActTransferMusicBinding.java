package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM;

/* loaded from: classes3.dex */
public abstract class ActTransferMusicBinding extends ViewDataBinding {
    public final View bg1;
    public final View bg2;
    public final View bg3;
    public final AppCompatImageView ivCopy;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDcaMusicListVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvAddress;
    public final TextView tvSize;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDcaMusicListVM viewmodel);

    protected ActTransferMusicBinding(Object _bindingComponent, View _root, int _localFieldCount, View bg1, View bg2, View bg3, AppCompatImageView ivCopy, LayoutTitleDefaultBinding title, TextView tvAddress, TextView tvSize) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bg1 = bg1;
        this.bg2 = bg2;
        this.bg3 = bg3;
        this.ivCopy = ivCopy;
        this.title = title;
        this.tvAddress = tvAddress;
        this.tvSize = tvSize;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDcaMusicListVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActTransferMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTransferMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTransferMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_transfer_music, root, attachToRoot, component);
    }

    public static ActTransferMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTransferMusicBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTransferMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_transfer_music, null, false, component);
    }

    public static ActTransferMusicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTransferMusicBinding bind(View view, Object component) {
        return (ActTransferMusicBinding) bind(component, view, R.layout.act_transfer_music);
    }
}