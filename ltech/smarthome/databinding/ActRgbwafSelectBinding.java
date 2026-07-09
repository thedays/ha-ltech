package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class ActRgbwafSelectBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final StepSetView seekbarA;
    public final StepSetView seekbarB;
    public final StepSetView seekbarBrt;
    public final StepSetView seekbarF;
    public final StepSetView seekbarG;
    public final StepSetView seekbarR;
    public final StepSetView seekbarW;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActRgbwafSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, StepSetView seekbarA, StepSetView seekbarB, StepSetView seekbarBrt, StepSetView seekbarF, StepSetView seekbarG, StepSetView seekbarR, StepSetView seekbarW, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.seekbarA = seekbarA;
        this.seekbarB = seekbarB;
        this.seekbarBrt = seekbarBrt;
        this.seekbarF = seekbarF;
        this.seekbarG = seekbarG;
        this.seekbarR = seekbarR;
        this.seekbarW = seekbarW;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActRgbwafSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRgbwafSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRgbwafSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rgbwaf_select, root, attachToRoot, component);
    }

    public static ActRgbwafSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRgbwafSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRgbwafSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rgbwaf_select, null, false, component);
    }

    public static ActRgbwafSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRgbwafSelectBinding bind(View view, Object component) {
        return (ActRgbwafSelectBinding) bind(component, view, R.layout.act_rgbwaf_select);
    }
}