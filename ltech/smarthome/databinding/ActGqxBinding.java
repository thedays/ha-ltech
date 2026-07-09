package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.gqx.ActGqxVM;

/* loaded from: classes3.dex */
public abstract class ActGqxBinding extends ViewDataBinding {
    public final ImageView bg;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActGqxVM mViewmodel;
    public final TabLayout tabLayout;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvBtn1;
    public final TextView tvBtn2;
    public final TextView tvBtn3;
    public final TextView tvBtn4;
    public final ViewPager2 vp;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActGqxVM viewmodel);

    protected ActGqxBinding(Object _bindingComponent, View _root, int _localFieldCount, ImageView bg, TabLayout tabLayout, LayoutTitleDefaultBinding title, TextView tvBtn1, TextView tvBtn2, TextView tvBtn3, TextView tvBtn4, ViewPager2 vp) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bg = bg;
        this.tabLayout = tabLayout;
        this.title = title;
        this.tvBtn1 = tvBtn1;
        this.tvBtn2 = tvBtn2;
        this.tvBtn3 = tvBtn3;
        this.tvBtn4 = tvBtn4;
        this.vp = vp;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActGqxVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActGqxBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqxBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActGqxBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_gqx, root, attachToRoot, component);
    }

    public static ActGqxBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqxBinding inflate(LayoutInflater inflater, Object component) {
        return (ActGqxBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_gqx, null, false, component);
    }

    public static ActGqxBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqxBinding bind(View view, Object component) {
        return (ActGqxBinding) bind(component, view, R.layout.act_gqx);
    }
}