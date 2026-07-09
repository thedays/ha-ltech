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
public abstract class ItemContentSmallBinding extends ViewDataBinding {
    public final WaveView icPlaying;
    public final ImageView ivLogo;
    public final ImageView ivPlay;
    public final ConstraintLayout layoutNormalOrSmall;
    public final TextView tvMain;

    protected ItemContentSmallBinding(Object _bindingComponent, View _root, int _localFieldCount, WaveView icPlaying, ImageView ivLogo, ImageView ivPlay, ConstraintLayout layoutNormalOrSmall, TextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.icPlaying = icPlaying;
        this.ivLogo = ivLogo;
        this.ivPlay = ivPlay;
        this.layoutNormalOrSmall = layoutNormalOrSmall;
        this.tvMain = tvMain;
    }

    public static ItemContentSmallBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemContentSmallBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemContentSmallBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_content_small, root, attachToRoot, component);
    }

    public static ItemContentSmallBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemContentSmallBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemContentSmallBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_content_small, null, false, component);
    }

    public static ItemContentSmallBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemContentSmallBinding bind(View view, Object component) {
        return (ItemContentSmallBinding) bind(component, view, R.layout.item_content_small);
    }
}