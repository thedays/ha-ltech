package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.WaveView;

/* loaded from: classes3.dex */
public abstract class ItemContentNormalBinding extends ViewDataBinding {
    public final View centerLine;
    public final WaveView icPlaying;
    public final ImageView ivLogo;
    public final ImageView ivPlay;
    public final ConstraintLayout layoutNormalOrSmall;
    public final TextView tvMain;
    public final TextView tvSub;

    protected ItemContentNormalBinding(Object _bindingComponent, View _root, int _localFieldCount, View centerLine, WaveView icPlaying, ImageView ivLogo, ImageView ivPlay, ConstraintLayout layoutNormalOrSmall, TextView tvMain, TextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.centerLine = centerLine;
        this.icPlaying = icPlaying;
        this.ivLogo = ivLogo;
        this.ivPlay = ivPlay;
        this.layoutNormalOrSmall = layoutNormalOrSmall;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
    }

    public static ItemContentNormalBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemContentNormalBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemContentNormalBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_content_normal, root, attachToRoot, component);
    }

    public static ItemContentNormalBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemContentNormalBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemContentNormalBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_content_normal, null, false, component);
    }

    public static ItemContentNormalBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemContentNormalBinding bind(View view, Object component) {
        return (ItemContentNormalBinding) bind(component, view, R.layout.item_content_normal);
    }
}