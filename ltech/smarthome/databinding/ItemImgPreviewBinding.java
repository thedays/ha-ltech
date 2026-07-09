package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.imageclip.ClipViewLayout;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemImgPreviewBinding extends ViewDataBinding {
    public final ClipViewLayout clipViewLayout;

    protected ItemImgPreviewBinding(Object _bindingComponent, View _root, int _localFieldCount, ClipViewLayout clipViewLayout) {
        super(_bindingComponent, _root, _localFieldCount);
        this.clipViewLayout = clipViewLayout;
    }

    public static ItemImgPreviewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemImgPreviewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemImgPreviewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_img_preview, root, attachToRoot, component);
    }

    public static ItemImgPreviewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemImgPreviewBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemImgPreviewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_img_preview, null, false, component);
    }

    public static ItemImgPreviewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemImgPreviewBinding bind(View view, Object component) {
        return (ItemImgPreviewBinding) bind(component, view, R.layout.item_img_preview);
    }
}