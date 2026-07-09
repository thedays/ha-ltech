package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.control.FtSceneVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class FtSceneBinding extends ViewDataBinding {
    public final LinearLayout layoutFloor;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected FtSceneVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final TabLayout tabs;
    public final AppCompatTextView tvFloorContent;
    public final ViewPager2 viewpager;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setViewmodel(FtSceneVM viewmodel);

    protected FtSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutFloor, SmartRefreshLayout refreshLayout, TabLayout tabs, AppCompatTextView tvFloorContent, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutFloor = layoutFloor;
        this.refreshLayout = refreshLayout;
        this.tabs = tabs;
        this.tvFloorContent = tvFloorContent;
        this.viewpager = viewpager;
    }

    public FtSceneVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_scene, root, attachToRoot, component);
    }

    public static FtSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_scene, null, false, component);
    }

    public static FtSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSceneBinding bind(View view, Object component) {
        return (FtSceneBinding) bind(component, view, R.layout.ft_scene);
    }
}