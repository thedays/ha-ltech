package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.FtMicVM;
import com.ltech.smarthome.view.VoiceView;

/* loaded from: classes3.dex */
public abstract class FtMicBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivStartStop;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected FtMicVM mViewmodel;
    public final Toolbar toolbar;
    public final AppCompatTextView tvTip1;
    public final AppCompatTextView tvTitle;
    public final VoiceView voiceview;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(FtMicVM viewmodel);

    protected FtMicBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, AppCompatImageView ivEdit, AppCompatImageView ivStartStop, Toolbar toolbar, AppCompatTextView tvTip1, AppCompatTextView tvTitle, VoiceView voiceview) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.ivEdit = ivEdit;
        this.ivStartStop = ivStartStop;
        this.toolbar = toolbar;
        this.tvTip1 = tvTip1;
        this.tvTitle = tvTitle;
        this.voiceview = voiceview;
    }

    public FtMicVM getViewmodel() {
        return this.mViewmodel;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static FtMicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtMicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_mic, root, attachToRoot, component);
    }

    public static FtMicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMicBinding inflate(LayoutInflater inflater, Object component) {
        return (FtMicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_mic, null, false, component);
    }

    public static FtMicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMicBinding bind(View view, Object component) {
        return (FtMicBinding) bind(component, view, R.layout.ft_mic);
    }
}