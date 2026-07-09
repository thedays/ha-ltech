package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActChoiceLightTypeVM;

/* loaded from: classes3.dex */
public abstract class ActChoiceLightTypeBinding extends ViewDataBinding {
    public final RelativeLayout lightTypeCt;
    public final RelativeLayout lightTypeDim;
    public final RelativeLayout lightTypeRgb;
    public final RelativeLayout lightTypeRgbW;
    public final RelativeLayout lightTypeRgbWy;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActChoiceLightTypeVM mViewmodel;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActChoiceLightTypeVM viewmodel);

    protected ActChoiceLightTypeBinding(Object _bindingComponent, View _root, int _localFieldCount, RelativeLayout lightTypeCt, RelativeLayout lightTypeDim, RelativeLayout lightTypeRgb, RelativeLayout lightTypeRgbW, RelativeLayout lightTypeRgbWy, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.lightTypeCt = lightTypeCt;
        this.lightTypeDim = lightTypeDim;
        this.lightTypeRgb = lightTypeRgb;
        this.lightTypeRgbW = lightTypeRgbW;
        this.lightTypeRgbWy = lightTypeRgbWy;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActChoiceLightTypeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActChoiceLightTypeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChoiceLightTypeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActChoiceLightTypeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_choice_light_type, root, attachToRoot, component);
    }

    public static ActChoiceLightTypeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChoiceLightTypeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActChoiceLightTypeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_choice_light_type, null, false, component);
    }

    public static ActChoiceLightTypeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChoiceLightTypeBinding bind(View view, Object component) {
        return (ActChoiceLightTypeBinding) bind(component, view, R.layout.act_choice_light_type);
    }
}