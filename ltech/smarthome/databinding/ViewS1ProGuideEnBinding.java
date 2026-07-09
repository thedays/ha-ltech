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
public abstract class ViewS1ProGuideEnBinding extends ViewDataBinding {
    public final AppCompatImageView ivClose;
    public final AppCompatImageView ivGuide;
    public final ConstraintLayout layoutContent;

    protected ViewS1ProGuideEnBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivClose, AppCompatImageView ivGuide, ConstraintLayout layoutContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivClose = ivClose;
        this.ivGuide = ivGuide;
        this.layoutContent = layoutContent;
    }

    public static ViewS1ProGuideEnBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewS1ProGuideEnBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewS1ProGuideEnBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_s1_pro_guide_en, root, attachToRoot, component);
    }

    public static ViewS1ProGuideEnBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewS1ProGuideEnBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewS1ProGuideEnBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_s1_pro_guide_en, null, false, component);
    }

    public static ViewS1ProGuideEnBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewS1ProGuideEnBinding bind(View view, Object component) {
        return (ViewS1ProGuideEnBinding) bind(component, view, R.layout.view_s1_pro_guide_en);
    }
}