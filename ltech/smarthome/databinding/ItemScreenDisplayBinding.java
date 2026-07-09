package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemScreenDisplayBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView8;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvName;

    protected ItemScreenDisplayBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView8, ConstraintLayout layoutItemBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView8 = appCompatImageView8;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
    }

    public static ItemScreenDisplayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenDisplayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemScreenDisplayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_display, root, attachToRoot, component);
    }

    public static ItemScreenDisplayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenDisplayBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemScreenDisplayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_display, null, false, component);
    }

    public static ItemScreenDisplayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenDisplayBinding bind(View view, Object component) {
        return (ItemScreenDisplayBinding) bind(component, view, R.layout.item_screen_display);
    }
}