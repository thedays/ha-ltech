package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.home.ActHomeSettingVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActHomeSettingBinding extends ViewDataBinding {
    public final RecyclerView layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActHomeSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActHomeSettingVM viewmodel);

    protected ActHomeSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView layoutLoad, SmartRefreshLayout refreshLayout, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.refreshLayout = refreshLayout;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActHomeSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActHomeSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHomeSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_setting, root, attachToRoot, component);
    }

    public static ActHomeSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHomeSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_setting, null, false, component);
    }

    public static ActHomeSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeSettingBinding bind(View view, Object component) {
        return (ActHomeSettingBinding) bind(component, view, R.layout.act_home_setting);
    }
}