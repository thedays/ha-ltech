package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.device.musicplayer.ActBleMusicPlayerVM;

/* loaded from: classes3.dex */
public abstract class ActBleMusicPlayerBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivSetting;

    @Bindable
    protected ActBleMusicPlayerVM mViewmodel;
    public final TabLayout tabs;
    public final View vTitleBg;
    public final ViewPager2 viewpager;

    public abstract void setViewmodel(ActBleMusicPlayerVM viewmodel);

    protected ActBleMusicPlayerBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, AppCompatImageView ivSetting, TabLayout tabs, View vTitleBg, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.ivSetting = ivSetting;
        this.tabs = tabs;
        this.vTitleBg = vTitleBg;
        this.viewpager = viewpager;
    }

    public ActBleMusicPlayerVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActBleMusicPlayerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMusicPlayerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBleMusicPlayerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_music_player, root, attachToRoot, component);
    }

    public static ActBleMusicPlayerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMusicPlayerBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBleMusicPlayerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_music_player, null, false, component);
    }

    public static ActBleMusicPlayerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleMusicPlayerBinding bind(View view, Object component) {
        return (ActBleMusicPlayerBinding) bind(component, view, R.layout.act_ble_music_player);
    }
}