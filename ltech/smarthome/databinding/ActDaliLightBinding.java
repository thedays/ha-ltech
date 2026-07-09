package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroupVM;

/* loaded from: classes3.dex */
public abstract class ActDaliLightBinding extends ViewDataBinding {
    public final TabLayout bottomTabLayout;
    public final FrameLayout fragmentContainer;
    public final LinearLayout layoutOpenClose;
    public final LinearLayout layoutTab;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDaliLightOrGroupVM mViewmodel;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDaliLightOrGroupVM viewmodel);

    protected ActDaliLightBinding(Object _bindingComponent, View _root, int _localFieldCount, TabLayout bottomTabLayout, FrameLayout fragmentContainer, LinearLayout layoutOpenClose, LinearLayout layoutTab, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bottomTabLayout = bottomTabLayout;
        this.fragmentContainer = fragmentContainer;
        this.layoutOpenClose = layoutOpenClose;
        this.layoutTab = layoutTab;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDaliLightOrGroupVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDaliLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDaliLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_light, root, attachToRoot, component);
    }

    public static ActDaliLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliLightBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDaliLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_light, null, false, component);
    }

    public static ActDaliLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliLightBinding bind(View view, Object component) {
        return (ActDaliLightBinding) bind(component, view, R.layout.act_dali_light);
    }
}