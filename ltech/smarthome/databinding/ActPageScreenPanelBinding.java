package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;

/* loaded from: classes3.dex */
public abstract class ActPageScreenPanelBinding extends ViewDataBinding {
    public final AppCompatImageView ivS4;
    public final AppCompatImageView ivScreen;
    public final AppCompatImageView ivShadowLeft;
    public final AppCompatImageView ivShadowRight;
    public final AppCompatImageView ivSwitch5;
    public final ConstraintLayout layoutContent;
    public final LinearLayout layoutDot;
    public final ConstraintLayout layoutIv;
    public final RelativeLayout layoutS4;
    public final RelativeLayout llBottom;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSmartPanelVM mViewmodel;
    public final RecyclerView rvKeyInfo;
    public final RecyclerView rvScreenInfo;
    public final TabLayout tabLayout;
    public final LayoutTitleDefaultBinding title;
    public final View viewGuide;
    public final ViewPager2 vp;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSmartPanelVM viewmodel);

    protected ActPageScreenPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivS4, AppCompatImageView ivScreen, AppCompatImageView ivShadowLeft, AppCompatImageView ivShadowRight, AppCompatImageView ivSwitch5, ConstraintLayout layoutContent, LinearLayout layoutDot, ConstraintLayout layoutIv, RelativeLayout layoutS4, RelativeLayout llBottom, RecyclerView rvKeyInfo, RecyclerView rvScreenInfo, TabLayout tabLayout, LayoutTitleDefaultBinding title, View viewGuide, ViewPager2 vp) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivS4 = ivS4;
        this.ivScreen = ivScreen;
        this.ivShadowLeft = ivShadowLeft;
        this.ivShadowRight = ivShadowRight;
        this.ivSwitch5 = ivSwitch5;
        this.layoutContent = layoutContent;
        this.layoutDot = layoutDot;
        this.layoutIv = layoutIv;
        this.layoutS4 = layoutS4;
        this.llBottom = llBottom;
        this.rvKeyInfo = rvKeyInfo;
        this.rvScreenInfo = rvScreenInfo;
        this.tabLayout = tabLayout;
        this.title = title;
        this.viewGuide = viewGuide;
        this.vp = vp;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSmartPanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActPageScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPageScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActPageScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_page_screen_panel, root, attachToRoot, component);
    }

    public static ActPageScreenPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPageScreenPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActPageScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_page_screen_panel, null, false, component);
    }

    public static ActPageScreenPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPageScreenPanelBinding bind(View view, Object component) {
        return (ActPageScreenPanelBinding) bind(component, view, R.layout.act_page_screen_panel);
    }
}