package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public abstract class FtCameraTalkBinding extends ViewDataBinding {
    public final AppCompatImageView ivTalk;
    public final SpreadView spreadviewTalk;
    public final AppCompatImageView spreadviewTalkStop;
    public final RelativeLayout talkView;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvTitle;

    protected FtCameraTalkBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivTalk, SpreadView spreadviewTalk, AppCompatImageView spreadviewTalkStop, RelativeLayout talkView, AppCompatTextView tvTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivTalk = ivTalk;
        this.spreadviewTalk = spreadviewTalk;
        this.spreadviewTalkStop = spreadviewTalkStop;
        this.talkView = talkView;
        this.tvTip = tvTip;
        this.tvTitle = tvTitle;
    }

    public static FtCameraTalkBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCameraTalkBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtCameraTalkBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_camera_talk, root, attachToRoot, component);
    }

    public static FtCameraTalkBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCameraTalkBinding inflate(LayoutInflater inflater, Object component) {
        return (FtCameraTalkBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_camera_talk, null, false, component);
    }

    public static FtCameraTalkBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCameraTalkBinding bind(View view, Object component) {
        return (FtCameraTalkBinding) bind(component, view, R.layout.ft_camera_talk);
    }
}