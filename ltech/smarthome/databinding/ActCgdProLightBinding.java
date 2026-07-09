package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActCgdProLightVM;

/* loaded from: classes3.dex */
public abstract class ActCgdProLightBinding extends ViewDataBinding {
    public final FrameLayout fragmentContainer;
    public final LinearLayout layoutTab;
    public final View leftLine;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActCgdProLightVM mViewmodel;
    public final View rightLine;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBroadcast;
    public final AppCompatTextView tvDaliScene;
    public final AppCompatTextView tvGroupAndAddress;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActCgdProLightVM viewmodel);

    protected ActCgdProLightBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout fragmentContainer, LinearLayout layoutTab, View leftLine, View rightLine, LayoutTitleDefaultBinding title, AppCompatTextView tvBroadcast, AppCompatTextView tvDaliScene, AppCompatTextView tvGroupAndAddress) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
        this.layoutTab = layoutTab;
        this.leftLine = leftLine;
        this.rightLine = rightLine;
        this.title = title;
        this.tvBroadcast = tvBroadcast;
        this.tvDaliScene = tvDaliScene;
        this.tvGroupAndAddress = tvGroupAndAddress;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActCgdProLightVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActCgdProLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCgdProLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCgdProLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cgd_pro_light, root, attachToRoot, component);
    }

    public static ActCgdProLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCgdProLightBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCgdProLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cgd_pro_light, null, false, component);
    }

    public static ActCgdProLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCgdProLightBinding bind(View view, Object component) {
        return (ActCgdProLightBinding) bind(component, view, R.layout.act_cgd_pro_light);
    }
}