package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.videogo.widget.HandleView;

/* loaded from: classes3.dex */
public abstract class FtCameraPtzBinding extends ViewDataBinding {
    public final HandleView handleView;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvTitle;

    protected FtCameraPtzBinding(Object _bindingComponent, View _root, int _localFieldCount, HandleView handleView, AppCompatTextView tvTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.handleView = handleView;
        this.tvTip = tvTip;
        this.tvTitle = tvTitle;
    }

    public static FtCameraPtzBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCameraPtzBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtCameraPtzBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_camera_ptz, root, attachToRoot, component);
    }

    public static FtCameraPtzBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCameraPtzBinding inflate(LayoutInflater inflater, Object component) {
        return (FtCameraPtzBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_camera_ptz, null, false, component);
    }

    public static FtCameraPtzBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCameraPtzBinding bind(View view, Object component) {
        return (FtCameraPtzBinding) bind(component, view, R.layout.ft_camera_ptz);
    }
}