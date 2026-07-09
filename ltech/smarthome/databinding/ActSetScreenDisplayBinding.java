package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSetScreenDisplayBinding extends ViewDataBinding {
    public final AppCompatImageView ivS4;
    public final AppCompatImageView ivScreen;
    public final AppCompatImageView ivShadowLeft;
    public final AppCompatImageView ivShadowRight;
    public final AppCompatImageView ivSwitch5;
    public final ConstraintLayout layoutContent;
    public final LinearLayout layoutS4;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvDisplay;
    public final RecyclerView rvKeyInfo;
    public final RecyclerView rvScreenInfo;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActSetScreenDisplayBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivS4, AppCompatImageView ivScreen, AppCompatImageView ivShadowLeft, AppCompatImageView ivShadowRight, AppCompatImageView ivSwitch5, ConstraintLayout layoutContent, LinearLayout layoutS4, RecyclerView rvDisplay, RecyclerView rvKeyInfo, RecyclerView rvScreenInfo, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivS4 = ivS4;
        this.ivScreen = ivScreen;
        this.ivShadowLeft = ivShadowLeft;
        this.ivShadowRight = ivShadowRight;
        this.ivSwitch5 = ivSwitch5;
        this.layoutContent = layoutContent;
        this.layoutS4 = layoutS4;
        this.rvDisplay = rvDisplay;
        this.rvKeyInfo = rvKeyInfo;
        this.rvScreenInfo = rvScreenInfo;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSetScreenDisplayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSetScreenDisplayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSetScreenDisplayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_set_screen_display, root, attachToRoot, component);
    }

    public static ActSetScreenDisplayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSetScreenDisplayBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSetScreenDisplayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_set_screen_display, null, false, component);
    }

    public static ActSetScreenDisplayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSetScreenDisplayBinding bind(View view, Object component) {
        return (ActSetScreenDisplayBinding) bind(component, view, R.layout.act_set_screen_display);
    }
}