package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectSpiForActionBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final LinearLayout tabLayout;
    public final TabLayout tabs;
    public final LayoutTitleDefaultBinding title;
    public final ViewPager2 viewpager;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectSpiForActionBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout tabLayout, TabLayout tabs, LayoutTitleDefaultBinding title, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tabLayout = tabLayout;
        this.tabs = tabs;
        this.title = title;
        this.viewpager = viewpager;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectSpiForActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSpiForActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectSpiForActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_spi_for_action, root, attachToRoot, component);
    }

    public static ActSelectSpiForActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSpiForActionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectSpiForActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_spi_for_action, null, false, component);
    }

    public static ActSelectSpiForActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSpiForActionBinding bind(View view, Object component) {
        return (ActSelectSpiForActionBinding) bind(component, view, R.layout.act_select_spi_for_action);
    }
}