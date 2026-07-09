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
public abstract class ViewSqProGuideEnBinding extends ViewDataBinding {
    public final AppCompatImageView ivClose;
    public final AppCompatImageView ivGuide;
    public final ConstraintLayout layoutContent;

    protected ViewSqProGuideEnBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivClose, AppCompatImageView ivGuide, ConstraintLayout layoutContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivClose = ivClose;
        this.ivGuide = ivGuide;
        this.layoutContent = layoutContent;
    }

    public static ViewSqProGuideEnBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSqProGuideEnBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewSqProGuideEnBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sq_pro_guide_en, root, attachToRoot, component);
    }

    public static ViewSqProGuideEnBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSqProGuideEnBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewSqProGuideEnBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_sq_pro_guide_en, null, false, component);
    }

    public static ViewSqProGuideEnBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSqProGuideEnBinding bind(View view, Object component) {
        return (ViewSqProGuideEnBinding) bind(component, view, R.layout.view_sq_pro_guide_en);
    }
}