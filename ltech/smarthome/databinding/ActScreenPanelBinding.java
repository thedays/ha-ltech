package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;

/* loaded from: classes3.dex */
public abstract class ActScreenPanelBinding extends ViewDataBinding {
    public final AppCompatImageView ivS4;
    public final AppCompatImageView ivScreen;
    public final AppCompatImageView ivShadowLeft;
    public final AppCompatImageView ivShadowRight;
    public final AppCompatImageView ivSwitch5;
    public final ConstraintLayout layoutContent;
    public final LinearLayout layoutLoad;
    public final LinearLayout layoutS4;
    public final LinearLayout llBottom;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSmartPanelVM mViewmodel;
    public final RecyclerView rvKeyInfo;
    public final RecyclerView rvScreenInfo;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvTipMessage;
    public final TextView tvTipTitle;
    public final View viewGuide;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSmartPanelVM viewmodel);

    protected ActScreenPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivS4, AppCompatImageView ivScreen, AppCompatImageView ivShadowLeft, AppCompatImageView ivShadowRight, AppCompatImageView ivSwitch5, ConstraintLayout layoutContent, LinearLayout layoutLoad, LinearLayout layoutS4, LinearLayout llBottom, RecyclerView rvKeyInfo, RecyclerView rvScreenInfo, LayoutTitleDefaultBinding title, TextView tvTipMessage, TextView tvTipTitle, View viewGuide) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivS4 = ivS4;
        this.ivScreen = ivScreen;
        this.ivShadowLeft = ivShadowLeft;
        this.ivShadowRight = ivShadowRight;
        this.ivSwitch5 = ivSwitch5;
        this.layoutContent = layoutContent;
        this.layoutLoad = layoutLoad;
        this.layoutS4 = layoutS4;
        this.llBottom = llBottom;
        this.rvKeyInfo = rvKeyInfo;
        this.rvScreenInfo = rvScreenInfo;
        this.title = title;
        this.tvTipMessage = tvTipMessage;
        this.tvTipTitle = tvTipTitle;
        this.viewGuide = viewGuide;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSmartPanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_screen_panel, root, attachToRoot, component);
    }

    public static ActScreenPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActScreenPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_screen_panel, null, false, component);
    }

    public static ActScreenPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActScreenPanelBinding bind(View view, Object component) {
        return (ActScreenPanelBinding) bind(component, view, R.layout.act_screen_panel);
    }
}