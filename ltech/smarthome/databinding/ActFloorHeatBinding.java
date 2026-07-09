package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.central.air.ActAcCentralVM;
import com.ltech.smarthome.view.CircleBar;

/* loaded from: classes3.dex */
public abstract class ActFloorHeatBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView19;
    public final AppCompatTextView appCompatTextView23;
    public final CardView cardviewAdd;
    public final CircleBar circleBar;
    public final AppCompatImageView ivMinus;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivPlus;
    public final AppCompatTextView ivUnit;
    public final AppCompatImageView ivUpper;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAcCentralVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleIrBinding title;
    public final AppCompatTextView tvIndex;
    public final AppCompatTextView tvResponse;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvTemp;
    public final View viewOnOff;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAcCentralVM viewmodel);

    protected ActFloorHeatBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView19, AppCompatTextView appCompatTextView23, CardView cardviewAdd, CircleBar circleBar, AppCompatImageView ivMinus, AppCompatImageView ivNext, AppCompatImageView ivPlus, AppCompatTextView ivUnit, AppCompatImageView ivUpper, ConstraintLayout layoutLoad, RecyclerView rvContent, LayoutTitleIrBinding title, AppCompatTextView tvIndex, AppCompatTextView tvResponse, AppCompatTextView tvState, AppCompatTextView tvTemp, View viewOnOff) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView19 = appCompatTextView19;
        this.appCompatTextView23 = appCompatTextView23;
        this.cardviewAdd = cardviewAdd;
        this.circleBar = circleBar;
        this.ivMinus = ivMinus;
        this.ivNext = ivNext;
        this.ivPlus = ivPlus;
        this.ivUnit = ivUnit;
        this.ivUpper = ivUpper;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.title = title;
        this.tvIndex = tvIndex;
        this.tvResponse = tvResponse;
        this.tvState = tvState;
        this.tvTemp = tvTemp;
        this.viewOnOff = viewOnOff;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAcCentralVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActFloorHeatBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFloorHeatBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActFloorHeatBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_floor_heat, root, attachToRoot, component);
    }

    public static ActFloorHeatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFloorHeatBinding inflate(LayoutInflater inflater, Object component) {
        return (ActFloorHeatBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_floor_heat, null, false, component);
    }

    public static ActFloorHeatBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFloorHeatBinding bind(View view, Object component) {
        return (ActFloorHeatBinding) bind(component, view, R.layout.act_floor_heat);
    }
}