package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.imageclip.ClipViewLayout;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class FtClipPhotoBinding extends ViewDataBinding {
    public final ClipViewLayout clipViewLayout;

    protected FtClipPhotoBinding(Object _bindingComponent, View _root, int _localFieldCount, ClipViewLayout clipViewLayout) {
        super(_bindingComponent, _root, _localFieldCount);
        this.clipViewLayout = clipViewLayout;
    }

    public static FtClipPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtClipPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtClipPhotoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_clip_photo, root, attachToRoot, component);
    }

    public static FtClipPhotoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtClipPhotoBinding inflate(LayoutInflater inflater, Object component) {
        return (FtClipPhotoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_clip_photo, null, false, component);
    }

    public static FtClipPhotoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtClipPhotoBinding bind(View view, Object component) {
        return (FtClipPhotoBinding) bind(component, view, R.layout.ft_clip_photo);
    }
}