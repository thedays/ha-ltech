package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseManageVM;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActDeviceGroupManageBinding extends ViewDataBinding {
    public final Guideline guideline;
    public final FrameLayout layoutSearch;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected BaseManageVM mViewmodel;
    public final RecyclerView rvContent;
    public final ItemSearchBarBinding searchBar;
    public final MySpinner spinnerFloor;
    public final MySpinner spinnerRoom;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(BaseManageVM viewmodel);

    protected ActDeviceGroupManageBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline, FrameLayout layoutSearch, RecyclerView rvContent, ItemSearchBarBinding searchBar, MySpinner spinnerFloor, MySpinner spinnerRoom, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline = guideline;
        this.layoutSearch = layoutSearch;
        this.rvContent = rvContent;
        this.searchBar = searchBar;
        this.spinnerFloor = spinnerFloor;
        this.spinnerRoom = spinnerRoom;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BaseManageVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDeviceGroupManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceGroupManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDeviceGroupManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_group_manage, root, attachToRoot, component);
    }

    public static ActDeviceGroupManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceGroupManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDeviceGroupManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_group_manage, null, false, component);
    }

    public static ActDeviceGroupManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceGroupManageBinding bind(View view, Object component) {
        return (ActDeviceGroupManageBinding) bind(component, view, R.layout.act_device_group_manage);
    }
}