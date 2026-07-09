package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class FtRoomBinding extends ViewDataBinding {
    public final AppCompatImageView addDevice;
    public final AppCompatImageView appCompatImageView2;
    public final AppCompatTextView appCompatTextView4;
    public final AppCompatTextView appCompatTextView5;
    public final AppCompatTextView centerSelect;
    public final LinearLayout layoutLoad;

    @Bindable
    protected FtRoomVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final AppCompatImageView searchDevice;
    public final AppCompatTextView selectAll;
    public final TabLayout tabs;
    public final AppCompatTextView tvSelectDone;
    public final View view8;
    public final ViewPager2 viewpager;

    public abstract void setViewmodel(FtRoomVM viewmodel);

    protected FtRoomBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView addDevice, AppCompatImageView appCompatImageView2, AppCompatTextView appCompatTextView4, AppCompatTextView appCompatTextView5, AppCompatTextView centerSelect, LinearLayout layoutLoad, SmartRefreshLayout refreshLayout, AppCompatImageView searchDevice, AppCompatTextView selectAll, TabLayout tabs, AppCompatTextView tvSelectDone, View view8, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.addDevice = addDevice;
        this.appCompatImageView2 = appCompatImageView2;
        this.appCompatTextView4 = appCompatTextView4;
        this.appCompatTextView5 = appCompatTextView5;
        this.centerSelect = centerSelect;
        this.layoutLoad = layoutLoad;
        this.refreshLayout = refreshLayout;
        this.searchDevice = searchDevice;
        this.selectAll = selectAll;
        this.tabs = tabs;
        this.tvSelectDone = tvSelectDone;
        this.view8 = view8;
        this.viewpager = viewpager;
    }

    public FtRoomVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtRoomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtRoomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtRoomBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_room, root, attachToRoot, component);
    }

    public static FtRoomBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtRoomBinding inflate(LayoutInflater inflater, Object component) {
        return (FtRoomBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_room, null, false, component);
    }

    public static FtRoomBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtRoomBinding bind(View view, Object component) {
        return (FtRoomBinding) bind(component, view, R.layout.ft_room);
    }
}