package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemImgBinding extends ViewDataBinding {
    public final AppCompatImageView ivImage;

    protected ItemImgBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivImage) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivImage = ivImage;
    }

    public static ItemImgBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemImgBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemImgBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_img, root, attachToRoot, component);
    }

    public static ItemImgBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemImgBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemImgBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_img, null, false, component);
    }

    public static ItemImgBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemImgBinding bind(View view, Object component) {
        return (ItemImgBinding) bind(component, view, R.layout.item_img);
    }
}