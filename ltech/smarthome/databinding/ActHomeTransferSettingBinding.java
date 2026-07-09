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
import com.ltech.smarthome.ui.home.ActHomeTransferSettingVM;

/* loaded from: classes3.dex */
public abstract class ActHomeTransferSettingBinding extends ViewDataBinding {
    public final RecyclerView layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActHomeTransferSettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActHomeTransferSettingVM viewmodel);

    protected ActHomeTransferSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView layoutLoad, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActHomeTransferSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActHomeTransferSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeTransferSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHomeTransferSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_transfer_setting, root, attachToRoot, component);
    }

    public static ActHomeTransferSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeTransferSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHomeTransferSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_transfer_setting, null, false, component);
    }

    public static ActHomeTransferSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeTransferSettingBinding bind(View view, Object component) {
        return (ActHomeTransferSettingBinding) bind(component, view, R.layout.act_home_transfer_setting);
    }
}