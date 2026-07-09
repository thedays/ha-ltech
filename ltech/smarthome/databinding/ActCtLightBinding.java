package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.jaygoo.widget.VerticalRangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActCtLightVM;
import com.ltech.smarthome.view.RectProgressBar2;

/* loaded from: classes3.dex */
public abstract class ActCtLightBinding extends ViewDataBinding {
    public final View ctView;
    public final VerticalRangeSeekBar ctsb;
    public final AppCompatImageView ivOpen;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActCtLightVM mViewmodel;
    public final RecyclerView rvColor;
    public final RecyclerView rvMode;
    public final RectProgressBar2 sbBrt;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvWy;
    public final View view15;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActCtLightVM viewmodel);

    protected ActCtLightBinding(Object _bindingComponent, View _root, int _localFieldCount, View ctView, VerticalRangeSeekBar ctsb, AppCompatImageView ivOpen, RecyclerView rvColor, RecyclerView rvMode, RectProgressBar2 sbBrt, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, AppCompatTextView tvWy, View view15) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ctView = ctView;
        this.ctsb = ctsb;
        this.ivOpen = ivOpen;
        this.rvColor = rvColor;
        this.rvMode = rvMode;
        this.sbBrt = sbBrt;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvWy = tvWy;
        this.view15 = view15;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActCtLightVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActCtLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCtLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCtLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ct_light, root, attachToRoot, component);
    }

    public static ActCtLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCtLightBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCtLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ct_light, null, false, component);
    }

    public static ActCtLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCtLightBinding bind(View view, Object component) {
        return (ActCtLightBinding) bind(component, view, R.layout.act_ct_light);
    }
}