package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewS2ProGuideBinding extends ViewDataBinding {
    public final AppCompatImageView ivClose;
    public final AppCompatImageView ivGuide;
    public final ConstraintLayout layoutContent;

    protected ViewS2ProGuideBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivClose, AppCompatImageView ivGuide, ConstraintLayout layoutContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivClose = ivClose;
        this.ivGuide = ivGuide;
        this.layoutContent = layoutContent;
    }

    public static ViewS2ProGuideBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewS2ProGuideBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewS2ProGuideBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_s2_pro_guide, root, attachToRoot, component);
    }

    public static ViewS2ProGuideBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewS2ProGuideBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewS2ProGuideBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_s2_pro_guide, null, false, component);
    }

    public static ViewS2ProGuideBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewS2ProGuideBinding bind(View view, Object component) {
        return (ViewS2ProGuideBinding) bind(component, view, R.layout.view_s2_pro_guide);
    }
}