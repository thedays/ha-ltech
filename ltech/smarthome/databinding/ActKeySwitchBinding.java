package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.remote_controller.ActKeySwitchVM;

/* loaded from: classes3.dex */
public abstract class ActKeySwitchBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView22;
    public final AppCompatImageView ivDevicePic;
    public final ConstraintLayout layoutContent;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActKeySwitchVM mViewmodel;
    public final RecyclerView rvRelatedInfo;
    public final LayoutTitleDefaultBinding title;
    public final View view6;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActKeySwitchVM viewmodel);

    protected ActKeySwitchBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView22, AppCompatImageView ivDevicePic, ConstraintLayout layoutContent, RecyclerView rvRelatedInfo, LayoutTitleDefaultBinding title, View view6) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView22 = appCompatTextView22;
        this.ivDevicePic = ivDevicePic;
        this.layoutContent = layoutContent;
        this.rvRelatedInfo = rvRelatedInfo;
        this.title = title;
        this.view6 = view6;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActKeySwitchVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActKeySwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKeySwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActKeySwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_key_switch, root, attachToRoot, component);
    }

    public static ActKeySwitchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKeySwitchBinding inflate(LayoutInflater inflater, Object component) {
        return (ActKeySwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_key_switch, null, false, component);
    }

    public static ActKeySwitchBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKeySwitchBinding bind(View view, Object component) {
        return (ActKeySwitchBinding) bind(component, view, R.layout.act_key_switch);
    }
}