package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.my.ActMessageCenterVM;

/* loaded from: classes3.dex */
public abstract class ActMessageCenterBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActMessageCenterVM mViewModel;
    public final TabLayout tabs;
    public final LayoutTitleDefaultBinding title;
    public final ViewPager2 viewpager;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewModel(ActMessageCenterVM viewModel);

    protected ActMessageCenterBinding(Object _bindingComponent, View _root, int _localFieldCount, TabLayout tabs, LayoutTitleDefaultBinding title, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tabs = tabs;
        this.title = title;
        this.viewpager = viewpager;
    }

    public ActMessageCenterVM getViewModel() {
        return this.mViewModel;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActMessageCenterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMessageCenterBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMessageCenterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_message_center, root, attachToRoot, component);
    }

    public static ActMessageCenterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMessageCenterBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMessageCenterBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_message_center, null, false, component);
    }

    public static ActMessageCenterBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMessageCenterBinding bind(View view, Object component) {
        return (ActMessageCenterBinding) bind(component, view, R.layout.act_message_center);
    }
}